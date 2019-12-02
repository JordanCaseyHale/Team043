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
        this.add(labelFullName, constraints);
        
        constraints.gridx = 1;
        this.add(textFieldFullName,constraints);   
        
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridheight = 2;
        this.add(buttonBack,constraints);
        
        constraints.gridx = 1;
        this.add(buttonMakeSubmission,constraints);
        
        
    }
	protected JLabel labelEmail = new JLabel ("Email");
	protected JLabel labelPassword = new JLabel ("Password");
	protected JLabel labelFullName = new JLabel ("Full Name");
	protected JTextField textFieldEmail = new JTextField(30);
	protected JTextField textFieldPassword = new JPasswordField(30);
	protected JTextField textFieldFullName = new JTextField(30);
    protected JButton buttonMakeSubmission = new JButton("Make Submission"); 
    protected JButton buttonBack = new JButton("Back"); 
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
            	
            }
        });
    }
}
