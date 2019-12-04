package assignment;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class AuthorPanel extends JPanel {
	public AuthorPanel() {
        this.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        
        
        constraints.gridx = 0;
        constraints.gridy = 0;
        this.add(labelEmail, constraints);
        
        constraints.gridx = 1;
        this.add(textFieldEmail,constraints);
        
        constraints.gridx = 0;
        constraints.gridy = 1;     
        this.add(labelPassword, constraints);
        
        constraints.gridx = 1;
        this.add(textFieldPassword,constraints);
        
        constraints.gridx = 0;
        constraints.gridy = 2;     
        this.add(labelTitle, constraints);
        
        constraints.gridx = 1;
        this.add(textFieldTitle,constraints);  
        
        constraints.gridx = 0;
        constraints.gridy = 3;     
        this.add(labelForename, constraints);
        
        constraints.gridx = 1;
        this.add(textFieldForename,constraints);
        
        constraints.gridx = 0;
        constraints.gridy = 4;     
        this.add(labelSurname, constraints);
        
        constraints.gridx = 1;
        this.add(textFieldSurname,constraints);  
        
        constraints.gridx = 0;
        constraints.gridy = 5;     
        this.add(labelAffiliation, constraints);
        
        constraints.gridx = 1;
        this.add(textFieldAffiliation,constraints); 
        
        constraints.gridx = 0;
        constraints.gridy = 6;
        constraints.gridheight = 2;
        this.add(buttonBack,constraints);
        
        constraints.gridx = 1;
        this.add(buttonMakeSubmission,constraints);
        
        
    }
	protected JLabel labelEmail = new JLabel ("Email");
	protected JLabel labelPassword = new JLabel ("Password");
	protected JLabel labelTitle = new JLabel ("Title");
	protected JLabel labelForename = new JLabel ("Forename");
	protected JLabel labelSurname = new JLabel ("Surname");
	protected JLabel labelAffiliation = new JLabel("Affiliation");
	protected JTextField textFieldTitle = new JTextField(30);
	protected JTextField textFieldEmail = new JTextField(30);
	protected JTextField textFieldPassword = new JPasswordField(30);
	protected JTextField textFieldForename = new JTextField(30);
	protected JTextField textFieldSurname = new JTextField(30);
	protected JTextField textFieldAffiliation = new JTextField(30);
    protected JButton buttonMakeSubmission = new JButton("Make Submission"); 
    protected JButton buttonBack = new JButton("Back");
    
    private String email, title, forename, surname, affiliation, password, userType;
    
    public void addListeners(JFrame parent) {
    	buttonBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	parent.getContentPane().removeAll();
            	MainPanel nextPanel = new MainPanel();
            	nextPanel.addListeners(parent);
            	parent.getContentPane().add(nextPanel);
            	parent.revalidate(); 
            	parent.repaint(); 
            }
        });
        buttonMakeSubmission.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		//Get information
        		email = textFieldEmail.getText().trim();
        		title = textFieldTitle.getText().trim();
        		forename = textFieldForename.getText().trim();
        		surname = textFieldSurname.getText().trim();
        		affiliation = textFieldAffiliation.getText().trim();
        		password = textFieldPassword.getText().trim();
        		userType = "Editor";
        		String hashpass = PasswordHash.getHashedString(password);
        		Author author = new Author(email, title, forename, surname, affiliation, hashpass);
            	parent.getContentPane().removeAll();
            	SubmissionPanel nextPanel = new SubmissionPanel(author);
            	nextPanel.addListeners(parent);
            	parent.getContentPane().add(nextPanel);
            	parent.revalidate(); 
            	parent.repaint(); 
            }
        });
    }
}
