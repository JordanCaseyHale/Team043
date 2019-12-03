package assignment;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class NewCoauthorDialog extends JDialog {	
	protected JLabel labelTitle = new JLabel ("Title");
	protected JLabel labelForename = new JLabel ("Forename");
	protected JLabel labelSurname = new JLabel ("Surname");
	protected JLabel labelEmail = new JLabel ("Email");
	protected JLabel labelPassword = new JLabel ("Password");
	
	protected JTextField textFieldTitle = new JTextField(10);
	protected JTextField textFieldForename = new JTextField(30);
	protected JTextField textFieldSurname = new JTextField(30);
	protected JTextField textFieldEmail = new JTextField(30);
	protected JTextField textFieldPassword = new JPasswordField(30);
	
	protected JButton btnAddCoauthor = new JButton("Add Co-author");
	protected JButton btnCancel = new JButton("Cancel");
	public NewCoauthorDialog(DefaultListModel<String> coauthList) {
		this.setTitle("Add New Co-author");
		JPanel panel = new JPanel(new GridBagLayout());
		panel.setPreferredSize(new Dimension (300,200));
		GridBagConstraints constraints = new GridBagConstraints();

		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(10, 10, 10, 10);
		constraints.fill = GridBagConstraints.HORIZONTAL;
    
		constraints.weightx = 0.3;
		constraints.gridx = 0;
		constraints.gridy = 0;
		panel.add(labelTitle, constraints);
		constraints.weightx = 0.7;
		constraints.gridx = 1;
		panel.add(textFieldTitle,constraints);
        
		constraints.weightx = 0.3;
		constraints.gridx = 0;
		constraints.gridy = 1;
		panel.add(labelForename, constraints);
		constraints.weightx = 0.7;
		constraints.gridx = 1;
		panel.add(textFieldForename,constraints);
		
		constraints.weightx = 0.3;
		constraints.gridx = 0;
		constraints.gridy = 2;
		panel.add(labelSurname, constraints);
		constraints.weightx = 0.7;
		constraints.gridx = 1;
		panel.add(textFieldSurname,constraints);
		
		constraints.weightx = 0.3;
		constraints.gridx = 0;
		constraints.gridy = 3;
		panel.add(labelEmail, constraints);
		constraints.weightx = 0.7;
		constraints.gridx = 1;
		panel.add(textFieldEmail,constraints);
		
		constraints.weightx = 0.3;
		constraints.gridx = 0;
		constraints.gridy = 4;
		panel.add(labelPassword, constraints);
		constraints.weightx = 0.7;
		constraints.gridx = 1;
		panel.add(textFieldPassword,constraints);
		
        JPanel bp = new JPanel(new FlowLayout());
        bp.add(btnAddCoauthor);
        bp.add(btnCancel);
		
        this.addListeners();
		getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(bp, BorderLayout.SOUTH);
        pack();
        setResizable(false);
	 }
	public void addListeners() { 
		btnAddCoauthor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	
            }
        });
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	}
}
