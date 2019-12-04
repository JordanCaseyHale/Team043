package assignment;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.*;

public class SubmissionPanel extends JPanel {

	public SubmissionPanel() {
        this.setLayout(new BorderLayout());
        JPanel grid = new JPanel(new GridBagLayout());
        this.add(grid,BorderLayout.NORTH);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weighty = 1;       
        constraints.weightx = 0.3;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        grid.add(labelTitle, constraints);

        constraints.gridx = 1;
        constraints.gridwidth = 2;
        grid.add(textFieldTitle,constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;     
        constraints.gridwidth = 1;
        grid.add(labelPDFLink, constraints);

        constraints.gridx = 1;
        constraints.gridwidth = 2;
        grid.add(textFieldPDFLink,constraints);  

        constraints.gridx = 0;
        constraints.gridy = 2;     
        constraints.gridwidth = 1;
        grid.add(labelCoauthorList, constraints);

        constraints.gridx = 1;
        constraints.gridwidth = 1;
        grid.add(buttonAddCoauthor,constraints);  
        
        constraints.gridx = 2;
        constraints.gridwidth = 1;
        grid.add(buttonRemoveCoauthor,constraints);  
        
        constraints.weightx = 1;
        constraints.weighty = 0.5;
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 3;
        
        
        listCoauthors.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listCoauthors.setLayoutOrientation(JList.VERTICAL);
        JScrollPane sp = new JScrollPane(listCoauthors);
        JScrollBar bar = sp.getVerticalScrollBar();
        bar.setPreferredSize(new Dimension(40, 0));
        grid.add(sp,constraints); 
        
        constraints.weightx = 0.3;
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 1;
        grid.add(labelAbstract, constraints);
        
        this.add(textAreaAbstract,BorderLayout.CENTER);
        
       
        JPanel bottomButtons = new JPanel(new FlowLayout());
        bottomButtons.add(buttonBack);
        bottomButtons.add(buttonMakeSubmission);      
        this.add(bottomButtons,BorderLayout.SOUTH);
        
    }
	protected JLabel labelTitle = new JLabel ("Title");
	protected JLabel labelPDFLink = new JLabel ("PDF Link");
	protected JLabel labelCoauthorList = new JLabel ("Co-Authors");
	protected JLabel labelAbstract= new JLabel ("Article Abstract");
	

	protected DefaultListModel<String> listModelCoauthors = new DefaultListModel<>();
	protected JList<String> listCoauthors = new JList<String>(listModelCoauthors);	
	protected ArrayList<ArrayList<String>> listCoauthorData = new ArrayList<ArrayList<String>>();
	protected ArrayList<String> mainAuthorData;
	
	protected JButton buttonAddCoauthor = new JButton("Add Co-author"); 
	protected JButton buttonRemoveCoauthor = new JButton("Remove Co-author"); 
	
	protected JTextField textFieldTitle = new JTextField(30);
	protected JTextArea textAreaAbstract = new JTextArea(20,10);
	protected JTextField textFieldPDFLink = new JTextField(30);
	
	public void addCoauthor(String title, String forename, String surname, String email, String pwhash) {
		listModelCoauthors.addElement(title+". "+forename+" "+surname);
		ArrayList<String> data = new ArrayList<>(Arrays.asList(title,forename,surname,email,pwhash));
		listCoauthorData.add(data);
	}
	
	public void setMainAuthor(String title, String forename, String surname, String email, String pwhash) {
		mainAuthorData = new ArrayList<String>(Arrays.asList(title,forename,surname,email,pwhash));
	}
	
	
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
        SubmissionPanel parentSubPanel = this;
        buttonAddCoauthor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	NewCoauthorDialog dlg = new NewCoauthorDialog(listModelCoauthors);
            	dlg.addListeners(parentSubPanel);
            	dlg.setVisible(true);
            	
            }
        });
        buttonRemoveCoauthor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	int i = listCoauthors.getSelectedIndex();
            	if(i > -1 && i<listCoauthors.getModel().getSize()) {
            		listModelCoauthors.remove(i);
            		listCoauthors.remove(i);
            	}
            }
        });
    }
}
