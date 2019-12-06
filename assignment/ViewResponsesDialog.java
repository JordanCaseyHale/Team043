package assignment;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

public class ViewResponsesDialog extends JDialog {	
	protected JLabel labelCriticisms = new JLabel ("Criticisms");
	protected JLabel labelResponse = new JLabel ("Response");
	protected JLabel labelComboBoxVerdicts = new JLabel ("Final Verdict");
	
	protected JTextArea textAreaCriticisms = new JTextArea(5,10);
	protected JTextArea textAreaResponse = new JTextArea(5,10);
	
	protected int ReviewID;
	protected int SubID;
	
	private String[] possibleVerdicts = new String[] {"Strong Accept","Weak Accept","Weak Reject","Strong Reject"};
	protected JComboBox<String> comboBoxVerdicts = new JComboBox<String>(possibleVerdicts);
	
	protected JButton btnSubmit = new JButton("Submit");
	protected JButton btnCancel = new JButton("Cancel");
	public ViewResponsesDialog(int SubID,int ReviewID) {
		this.ReviewID = ReviewID;
		this.SubID = SubID;
		this.setTitle("Viewing Responses");
		JPanel panel = new JPanel(new GridBagLayout());
		panel.setPreferredSize(new Dimension (500,700));
		GridBagConstraints constraints = new GridBagConstraints();
		
		textAreaCriticisms.setEditable(false);
		textAreaCriticisms.setLineWrap(true);
		textAreaResponse.setLineWrap(true);
		textAreaResponse.setEditable(false);
		
		ArrayList<String> reviewData = AuthorTasks.getSubmissionReviews(this.SubID, this.ReviewID);
		textAreaCriticisms.setText(reviewData.get(2)!=null?reviewData.get(2):"");
		textAreaResponse.setText(reviewData.get(2)!=null?reviewData.get(3):"");
		
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(10, 10, 10, 10);
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.weighty = 0.4;
		constraints.weightx = 0.2;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		panel.add(labelCriticisms, constraints);
		constraints.weightx = 1;
		constraints.gridy = 1;
		constraints.gridwidth = 3;
		panel.add(new JScrollPane(textAreaCriticisms),constraints);
		constraints.weightx = 0.3;
		constraints.gridy = 2;
		constraints.gridwidth = 1;
		panel.add(labelResponse, constraints);
		constraints.weightx = 1;
		constraints.gridy = 3;
		constraints.gridwidth = 3;
		panel.add(new JScrollPane(textAreaResponse),constraints);
		constraints.gridy = 4;
		constraints.gridwidth = 1;
		panel.add(labelComboBoxVerdicts, constraints);
		constraints.weightx = 0.6;
		constraints.gridx = 1;
		constraints.gridwidth = 2;
		panel.add(comboBoxVerdicts,constraints);
		
        JPanel bp = new JPanel(new FlowLayout());
        bp.add(btnSubmit);
        bp.add(btnCancel);
		
		getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(bp, BorderLayout.SOUTH);
        pack();
        setResizable(false);
	 }
	public void addListeners(JPanel parent) { 
		btnSubmit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	if (textAreaResponse.getText().length()>10) {
            		ReviewerTasks.submitFinalVerdict((String)comboBoxVerdicts.getSelectedItem(), SubID, ReviewID);
            		dispose();
            	}
            	else {
            		JOptionPane.showMessageDialog(parent,"You must respond to the given criticisms.");
            	}
            }
        });
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	}
}
