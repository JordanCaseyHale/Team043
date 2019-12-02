package assignment;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class SubmissionPanel extends JPanel {
	public SubmissionPanel() {
        this.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        
        
        constraints.gridx = 0;
        constraints.gridy = 0;
        this.add(labelTitle, constraints);
        
        constraints.gridx = 1;
        this.add(textFieldTitle,constraints);
        
        constraints.gridx = 0;
        constraints.gridy = 1;     
        this.add(labelAbstract, constraints);
        
        constraints.gridx = 1;
        this.add(textFieldAbstract,constraints);
        
        constraints.gridx = 0;
        constraints.gridy = 2;     
        this.add(labelPDFLink, constraints);
        
        constraints.gridx = 1;
        this.add(textFieldPDFLink,constraints);   
        
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 2;
        this.add(buttonBack,constraints);
        
        constraints.gridx = 1;
        this.add(buttonMakeSubmission,constraints);
        
        
    }
	protected JLabel labelTitle = new JLabel ("Title");
	protected JLabel labelAbstract= new JLabel ("Article Abstract");
	protected JLabel labelPDFLink = new JLabel ("PDF Link");
	protected JTextField textFieldTitle = new JTextField(30);
	protected JTextField textFieldAbstract = new JTextField(500);
	protected JTextField textFieldPDFLink = new JTextField(30);
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
