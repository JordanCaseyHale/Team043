package assignment;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
public class LoginPanel extends JPanel {
	public LoginPanel() {
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
        
        JPanel comboSelect = new JPanel();
        comboSelect.setLayout(new FlowLayout());
        comboSelect.add(new JLabel("Account Type"));
        comboSelect.add(buttonAuthorRole);
        buttonAuthorRole.setSelected(true);
        comboSelect.add(buttonEditorRole);
        
        ButtonGroup group = new ButtonGroup();
        group.add(buttonAuthorRole);
        group.add(buttonEditorRole);
        
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        this.add(comboSelect,constraints);    
        
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 1;
        this.add(buttonBack,constraints);
        
        constraints.gridx = 1;
        this.add(buttonLogin,constraints);
        
        
    }
	protected JRadioButton buttonAuthorRole = new JRadioButton("Author");
	protected JRadioButton buttonEditorRole = new JRadioButton("Editor");
	protected JLabel labelEmail = new JLabel ("Email");
	protected JLabel labelPassword = new JLabel ("Password");
	protected JTextField textFieldEmail = new JTextField(30);
	protected JTextField textFieldPassword = new JPasswordField(30);
    protected JButton buttonLogin = new JButton("Login"); 
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
        buttonLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	//get info
            	//checkLogin
            	//go to relevant page
            	String email = textFieldEmail.getText();
            	String password = textFieldPassword.getText();
            	String userType;
            	if (buttonAuthorRole.isSelected()) {
            		userType = "Author";
            	}
            	else {
            		userType = "Editor";
            	}
            	
            	email = MySQLConnection.checkInput(email);
            	
            	boolean succ = Main.login(email, password, userType);
            	if (succ) {
            		//go to relevant page
            		System.out.println("succ");
            	}
            	else {
            		//Display error message
            		System.out.println("not succ");
            		System.out.println("not succ".hashCode());
            		
            	}
            }
        });
    }
}
