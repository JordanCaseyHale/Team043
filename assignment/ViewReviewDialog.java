package assignment;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

public class ViewReviewDialog extends JDialog {	
	protected JLabel labelSummary = new JLabel ("Summary");
	protected JLabel labelTypoErrors = new JLabel ("Typographical Errors");
	protected JLabel labelCriticisms = new JLabel ("Criticisms");
	protected JLabel labelResponse = new JLabel ("Response");
	
	protected JTextArea textAreaSummary = new JTextArea(5,10);
	protected JTextArea textAreaTypoErrors = new JTextArea(5,10);
	protected JTextArea textAreaCriticisms = new JTextArea(5,10);
	protected JTextArea textAreaResponse = new JTextArea(5,10);
	
	protected int ReviewID;
	protected int SubID;
	
	protected JButton btnSubmit = new JButton("Submit");
	protected JButton btnCancel = new JButton("Cancel");
	public ViewReviewDialog(int ReviewID,int SubID) {
		this.ReviewID = ReviewID;
		this.SubID = SubID;
		this.setTitle("Viewing Review");
		JPanel panel = new JPanel(new GridBagLayout());
		panel.setPreferredSize(new Dimension (500,700));
		GridBagConstraints constraints = new GridBagConstraints();
		
		textAreaSummary.setEditable(false);
		textAreaSummary.setLineWrap(true);
		textAreaTypoErrors.setEditable(false);
		textAreaTypoErrors.setLineWrap(true);
		textAreaCriticisms.setEditable(false);
		textAreaCriticisms.setLineWrap(true);
		textAreaResponse.setLineWrap(true);
		
		ArrayList<String> reviewData = AuthorTasks.getSubmissionReviews(this.SubID, this.ReviewID);
		textAreaSummary.setText(reviewData.get(0)!=null?reviewData.get(0):"");
		textAreaTypoErrors.setText(reviewData.get(1)!=null?reviewData.get(1):"");
		textAreaCriticisms.setText(reviewData.get(2)!=null?reviewData.get(2):"");
		
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(10, 10, 10, 10);
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.weighty = 0.4;
		constraints.weightx = 0.2;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		panel.add(labelSummary, constraints);
		constraints.weightx = 1;
		constraints.gridwidth = 3;
		constraints.gridy = 1;
		panel.add(new JScrollPane(textAreaSummary),constraints);
		constraints.weightx = 0.3;
		constraints.gridy = 2;
		constraints.gridwidth = 1;
		panel.add(labelTypoErrors, constraints);
		constraints.weightx = 1;
		constraints.gridwidth = 3;
		constraints.gridy = 3;
		panel.add(new JScrollPane(textAreaTypoErrors),constraints);
		constraints.weightx = 0.3;
		constraints.gridy = 4;
		constraints.gridwidth = 1;
		panel.add(labelCriticisms, constraints);
		constraints.weightx = 1;
		constraints.gridy = 5;
		constraints.gridwidth = 3;
		panel.add(new JScrollPane(textAreaCriticisms),constraints);
		constraints.weightx = 0.3;
		constraints.gridy = 6;
		constraints.gridwidth = 1;
		panel.add(labelResponse, constraints);
		constraints.weightx = 1;
		constraints.gridy = 7;
		constraints.gridwidth = 3;
		panel.add(new JScrollPane(textAreaResponse),constraints);
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
            		AuthorTasks.submitReviewResponse(textAreaResponse.getText(), SubID, ReviewID);
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
