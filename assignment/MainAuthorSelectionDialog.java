package assignment;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

public class MainAuthorSelectionDialog extends JDialog {		
	protected JButton btnMakeSubmission = new JButton("Finalize Submission");
	protected JButton btnCancel = new JButton("Cancel");
	protected List<Author> authors = new ArrayList<Author>();
	protected ButtonGroup radioButtons = new ButtonGroup();
	
	protected ArrayList<JSpinner> reviewCounters = new ArrayList<JSpinner>();
	public MainAuthorSelectionDialog(List<Author> as) {
		this.setTitle("Select Authors");
		this.authors = as;
		JPanel pane = new JPanel(new GridBagLayout());
		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension (500,400));
		GridBagConstraints constraints = new GridBagConstraints();

		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(10, 10, 10, 10);
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.weightx = 0.3;
		constraints.gridwidth = 1;
		constraints.gridx = 0;
		constraints.gridy = 0;
		pane.add(new JLabel("Corresponding Author"),constraints);
		constraints.gridx = 1;
		pane.add(new JLabel("Number of Reviews"),constraints);
		int i = 0;
		for(Author auth : authors) {
			constraints.gridx = 0;
			constraints.gridy = i+1;
			JRadioButton rb = new JRadioButton(auth.getTitle()+". "+auth.getForename()+" "+auth.getSurname());
			rb.setActionCommand(auth.getEmail());
			radioButtons.add(rb);
			pane.add(rb,constraints);
			constraints.gridx = 1;	
			JSpinner spinnerRevCount = new JSpinner(new SpinnerNumberModel(0,0,3,1));
			pane.add(spinnerRevCount,constraints);
			reviewCounters.add(i,spinnerRevCount);
			i++;
		} 
        JScrollPane sp = new JScrollPane(pane);
        JScrollBar bar = sp.getVerticalScrollBar();
        bar.setPreferredSize(new Dimension(10, 0));
        
		this.add(sp,BorderLayout.CENTER);
		
		JPanel bp = new JPanel(new FlowLayout());
        bp.add(btnMakeSubmission);
        bp.add(btnCancel);

        getContentPane().add(bp, BorderLayout.SOUTH);
		
        pack();
        setResizable(false);
	 }
	public void addListeners(SubmissionPanel parent) { 
		btnMakeSubmission.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	String selectedEmail = radioButtons.getSelection().getActionCommand();
            	System.out.println(selectedEmail);
            	Boolean selectedMainAuthor = false;
            	int totalSpinnerRevCounts = 0;
            	ArrayList<Integer> reviewsCount = new ArrayList<Integer>();
            	reviewsCount.clear();
            	for(JSpinner spin : reviewCounters) {
            		int spinnerValue = (int)spin.getValue();
            		totalSpinnerRevCounts+= spinnerValue;
            		reviewsCount.add(spinnerValue);
            	}
            	if (totalSpinnerRevCounts != 3) {
            		JOptionPane.showMessageDialog(parent,"You must allocate exactly 3 reviews.");
            		return;
            	}
            	for(Author auth : authors) {
        			if(auth.getEmail() == selectedEmail) {
        				parent.setMainAuthor(auth.getTitle(),auth.getForename(),auth.getSurname(),auth.getEmail());
        				System.out.println("inputted author to system");
        				selectedMainAuthor = true;
        			}
        		} 
            	if(selectedMainAuthor) {
            		parent.submitSubmission(authors,reviewsCount);
            		dispose();
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
