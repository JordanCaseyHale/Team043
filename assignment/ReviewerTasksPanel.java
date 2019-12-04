package assignment;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.*;

public class ReviewerTasksPanel extends JPanel {
	public ReviewerTasksPanel() {
		
		//Check to see if has credentials here
        this.setLayout(new BorderLayout());
      
        JPanel topButtons = new JPanel(new FlowLayout());
        topButtons.add(labelTopMessage);
        topButtons.add(buttonLogout);
        topButtons.add(buttonResetPassword);
        this.add(topButtons,BorderLayout.NORTH);
        
        JPanel articleData = new JPanel(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.3;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        articleData.add(labelArticleName, constraints);
        constraints.gridy = 1;
        articleData.add(labelISSN, constraints);
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        constraints.weightx = 0.2;
        articleData.add(labelPDFLink, constraints);
        constraints.gridx = 1;
        constraints.weightx = 0.8;
        articleData.add(textPanePDFLink, constraints);
        constraints.weightx = 0.3;
        constraints.gridy = 3;
        constraints.gridx = 0;
        constraints.gridwidth = 1;
        articleData.add(buttonReview, constraints);
        
        this.add(articleData,BorderLayout.CENTER);        

        journalList = new JList<String>();
        journalList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        journalList.setLayoutOrientation(JList.VERTICAL);
        journalList.setVisibleRowCount(-1);
        JScrollPane journalListScrollPane = new JScrollPane(journalList);
        journalListScrollPane.setPreferredSize(new Dimension(250, 80));
        
        this.add(journalListScrollPane,BorderLayout.EAST);        
    }
	protected JLabel labelTopMessage = new JLabel("Welcome, ");
	
	protected JLabel labelArticleName = new JLabel("Article: ");
    protected JLabel labelISSN = new JLabel("ISSN: ");
    protected JLabel labelPDFLink = new JLabel("PDF Link: ");
    
    protected JTextPane textPanePDFLink = new JTextPane();
    
    protected JButton buttonReview = new JButton("Make Review");
    
    protected JButton buttonLogout = new JButton("Log Out");
    protected JButton buttonResetPassword = new JButton("Change Password");

    protected JList<String> journalList;
    
    public void addListeners(JFrame parent) {
    	
    }
}