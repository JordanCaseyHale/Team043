package assignment;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class AddEditorDialog extends JDialog {
	protected JLabel labelTitle = new JLabel("Title: ");
	protected JLabel labelForename = new JLabel("Forename: ");
	protected JLabel labelSurname = new JLabel("Surname: ");
	protected JLabel labelEmail = new JLabel("Email: ");
	protected JLabel labelAffiliation = new JLabel("Affiliation: ");
	protected JLabel labelPassword = new JLabel("Temp password: ");
	protected JLabel labelISSN = new JLabel("ISSN: ");
	
	protected JTextField textTitle = new JTextField(30);
	protected JTextField textForename = new JTextField(30);
	protected JTextField textSurname = new JTextField(30);
	protected JTextField textEmail = new JTextField(30);
	protected JTextField textAffiliation = new JTextField(30);
	protected JTextField textPassword = new JPasswordField(30);
	protected JTextField textISSN = new JTextField(30);
	
	protected JButton buttonCreate = new JButton("Create Editor");
	protected JButton buttonCancel = new JButton("Cancel");
	
	public AddEditorDialog() {
		JPanel panel = new JPanel(new GridBagLayout());
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
		panel.add(labelTitle, constraints);
		constraints.gridx = 1;
		panel.add(textTitle, constraints);
		constraints.gridy = 1;
		panel.add(textForename, constraints);
		constraints.gridx = 0;
		panel.add(labelForename, constraints);
		constraints.gridy = 2;
		panel.add(labelSurname, constraints);
		constraints.gridx = 1;
		panel.add(textSurname, constraints);
		constraints.gridy = 3;
		panel.add(textEmail, constraints);
		constraints.gridx = 0;
		panel.add(labelEmail, constraints);
		constraints.gridy = 4;
		panel.add(labelAffiliation, constraints);
		constraints.gridx = 1;
		panel.add(textAffiliation, constraints);
		constraints.gridy = 5;
		panel.add(textPassword, constraints);
		constraints.gridx = 0;
		panel.add(labelPassword, constraints);
		constraints.gridy = 6;
		panel.add(labelISSN, constraints);
		constraints.gridx = 1;
		panel.add(textISSN, constraints);
		constraints.gridy = 7;
		panel.add(buttonCreate, constraints);
		constraints.gridx = 0;
		panel.add(buttonCancel, constraints);
		
		getContentPane().add(panel, BorderLayout.CENTER);
        pack();
        setResizable(false);
	}
	
	public void AddListeners() {
		buttonCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		buttonCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean create = true;
				String email = textEmail.getText();
				String title = textTitle.getText();
				String forename = textForename.getText();
				String surname = textSurname.getText();
				String affiliation = textAffiliation.getText();
				String password = textPassword.getText().trim();
				String issn = textISSN.getText();
				EditorTasks.makeEditor(email, title, forename, surname, affiliation, password, issn);
			}
		});
	}
}