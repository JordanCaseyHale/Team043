package assignment;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

        journalList = new JList<String>();
        journalList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        journalList.setLayoutOrientation(JList.VERTICAL);
        journalList.setVisibleRowCount(-1);
        JScrollPane journalListScrollPane = new JScrollPane(journalList);
        journalListScrollPane.setPreferredSize(new Dimension(200, 160));
        
        this.add(journalListScrollPane,BorderLayout.CENTER);
        
        JPanel sideStuff = new JPanel();
        sideStuff.setLayout(new BoxLayout(sideStuff, BoxLayout.Y_AXIS));
        sideStuff.add(labelReviewsNeeded);
        sideStuff.add(labelSideMessage);
        sideStuff.add(buttonChooseArticles);
        
        
        this.add(sideStuff,BorderLayout.EAST);
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
        
        
        selectedJournalList = new JList<String>();
        selectedJournalList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        selectedJournalList.setLayoutOrientation(JList.VERTICAL);
        selectedJournalList.setVisibleRowCount(-1);
        JScrollPane selectedJournalListScrollPane = new JScrollPane(selectedJournalList);
        selectedJournalListScrollPane.setPreferredSize(new Dimension(150, 160));
        constraints.gridy = 0;
        constraints.gridx = 2;
        constraints.gridheight = 4;
        constraints.gridwidth = 1;
        
        articleData.add(selectedJournalListScrollPane, constraints);  
        this.add(articleData,BorderLayout.SOUTH); 
    }
	protected JLabel labelTopMessage = new JLabel("Welcome, ");
	
	protected JLabel labelReviewsNeeded = new JLabel("Reviews to do: ");
	protected JLabel labelSideMessage = new JLabel("Select Articles to Review");
	
	protected JLabel labelArticleName = new JLabel("Article: ");
    protected JLabel labelISSN = new JLabel("ISSN: ");
    protected JLabel labelPDFLink = new JLabel("PDF Link: ");
    
    protected JTextPane textPanePDFLink = new JTextPane();
    
    protected JButton buttonReview = new JButton("Make Review");
    
    protected JButton buttonLogout = new JButton("Log Out");
    protected JButton buttonResetPassword = new JButton("Change Password");
    protected JButton buttonChooseArticles = new JButton("Choose to Review");
    
    
    protected JList<String> journalList;
    protected JList<String> selectedJournalList;
    ReviewerTasksPanel parentRevPanel = this;
    public void addListeners() {
    	 buttonReview.addActionListener(new ActionListener() {
             public void actionPerformed(ActionEvent e) {
            	MakeReviewDialog dlg = new MakeReviewDialog();
             	dlg.addListeners(parentRevPanel);
             	dlg.setVisible(true);
             	System.out.print("make panel");
             }
    	 });
    }
}