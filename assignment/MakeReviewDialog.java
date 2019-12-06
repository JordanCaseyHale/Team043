package assignment;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

public class MakeReviewDialog extends JDialog {	
	protected JLabel labelSummary = new JLabel ("Summary");
	protected JLabel labelTypoErrors = new JLabel ("Typographical Errors");
	protected JLabel labelComboBoxVerdicts = new JLabel ("Verdict: ");
	protected JLabel labelCriticisms = new JLabel ("Criticisms");

	protected JTextArea textAreaSummary = new JTextArea(5,10);
	protected JTextArea textAreaTypoErrors = new JTextArea(5,10);
	protected ArrayList<JTextArea> textAreaCriticisms = new ArrayList<JTextArea>();
	
	protected JPanel panelCriticisms = new JPanel();
	
	protected Submission sub;
	protected int ReviewerID;
	
	private String[] possibleVerdicts = new String[] {"Strong Accept","Weak Accept","Weak Reject","Strong Reject"};
	protected JComboBox<String> comboBoxVerdicts = new JComboBox<String>(possibleVerdicts);
	
	protected JButton btnAddCriticbox = new JButton("Add Criticism");
	protected JButton btnRemoveCriticbox = new JButton("Remove Criticism");
	protected JButton btnMakeReview = new JButton("Submit");
	protected JButton btnCancel = new JButton("Cancel");
	public MakeReviewDialog(Submission sub,int RevID) {
		this.sub = sub;
		this.ReviewerID = RevID;
		this.setTitle("Review the Article");
		JPanel panel = new JPanel(new GridBagLayout());
		panel.setPreferredSize(new Dimension (500,700));
		GridBagConstraints constraints = new GridBagConstraints();

		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(10, 10, 10, 10);
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.weighty = 0.3;
		constraints.weightx = 0.2;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		panel.add(labelSummary, constraints);
		constraints.weightx = 1;
		constraints.gridwidth = 3;
		constraints.gridy = 1;
		panel.add(textAreaSummary,constraints);
		constraints.weightx = 0.3;
		constraints.gridy = 2;
		constraints.gridwidth = 1;
		panel.add(labelTypoErrors, constraints);
		constraints.weightx = 1;
		constraints.gridwidth = 3;
		constraints.gridy = 3;
		panel.add(textAreaTypoErrors,constraints);
		constraints.weightx = 0.3;
		constraints.gridy = 4;
		constraints.gridwidth = 1;
		panel.add(labelComboBoxVerdicts, constraints);
		constraints.weightx = 0.6;
		constraints.gridx = 1;
		constraints.gridwidth = 2;
		panel.add(comboBoxVerdicts,constraints);
		constraints.weightx = 0.3;
		constraints.gridx = 0;
		constraints.gridy = 5;
		constraints.gridwidth = 1;
		panel.add(labelCriticisms, constraints);
		constraints.weightx = 0.3;
		constraints.gridx = 1;
		panel.add(btnAddCriticbox, constraints);
		constraints.weightx = 0.3;
		constraints.gridx = 2;
		panel.add(btnRemoveCriticbox, constraints);
		constraints.weightx = 1;
		constraints.gridx = 0;
		constraints.gridy = 6;
		constraints.gridwidth = 3;
		
		panelCriticisms.setLayout(new BoxLayout(panelCriticisms,BoxLayout.Y_AXIS));
		JScrollPane criticismsScrollPane = new JScrollPane(panelCriticisms);
		criticismsScrollPane.setPreferredSize(new Dimension(300,300));
		panel.add(criticismsScrollPane,constraints);
		
        JPanel bp = new JPanel(new FlowLayout());
        bp.add(btnMakeReview);
        bp.add(btnCancel);
		
		getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(bp, BorderLayout.SOUTH);
        pack();
        setResizable(false);
	 }
	public void addListeners(JPanel parent) { 
		btnMakeReview.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	String criticism = "";
            	for(JTextArea ta : textAreaCriticisms) {
            		criticism+=(ta.getText()+"\n");
            	}
            	ReviewerTasks.submitReview(sub, ReviewerID, (String)comboBoxVerdicts.getSelectedItem(), textAreaSummary.getText(), textAreaTypoErrors.getText(), criticism);
            	dispose();
            }
        });
		btnAddCriticbox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	JTextArea ta = new JTextArea(5,10);
            	ta.setLineWrap(true);
            	textAreaCriticisms.add(ta);
            	updateCriticismsPanel();
            }
        });
		btnRemoveCriticbox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	if (!textAreaCriticisms.isEmpty()){
            		textAreaCriticisms.remove(textAreaCriticisms.size()-1);
            		updateCriticismsPanel();
            	}
            }
        });
		
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	}
	public void updateCriticismsPanel() {
		panelCriticisms.removeAll();
		for (JTextArea ta : textAreaCriticisms) {
    		panelCriticisms.add(ta);
    		panelCriticisms.add(Box.createRigidArea(new Dimension(0,5)));
    	}
    	panelCriticisms.revalidate(); 
    	panelCriticisms.repaint(); 
	}
}
