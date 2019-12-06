package assignment;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class ChangePasswordDialog extends JDialog {
	protected JLabel labelNewPass = new JLabel("New Password: ");
	protected JLabel labelConfirmPass = new JLabel("Confirm Password: ");
	
	protected JTextField textNewPass = new JPasswordField(30);
	protected JTextField textConfirmPass = new JPasswordField(30);
	
	protected JButton buttonChange = new JButton("Change Password");
	protected JButton buttonCancel = new JButton("Cancel");
	
	private String email;
	
	public ChangePasswordDialog(String email) {
		this.email = email;
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
		pane.add(labelNewPass, constraints);
		constraints.gridy = 1;
		pane.add(labelConfirmPass, constraints);
		constraints.gridx = 1;
		constraints.gridy = 0;
		pane.add(textNewPass, constraints);
		constraints.gridy = 1;
		pane.add(textConfirmPass, constraints);
		constraints.gridy = 2;
		pane.add(buttonChange, constraints);
		constraints.gridx = 0;
		pane.add(buttonCancel, constraints);
		
		getContentPane().add(pane, BorderLayout.CENTER);
        pack();
        setResizable(false);
	}
	
	public void AddListeners() {
		buttonChange.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String pass = textNewPass.getText();
				String confirmPass = textConfirmPass.getText();
				if (pass.contentEquals(confirmPass)) {
					Main.changePassword(pass, email);
					dispose();
				}
				else {
					labelConfirmPass.setText("Inputs don't match");
				}
			}
		});
		
		buttonCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	}
}