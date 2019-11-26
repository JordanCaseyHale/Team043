package assignment;

import java.awt.*;
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

    }
}
