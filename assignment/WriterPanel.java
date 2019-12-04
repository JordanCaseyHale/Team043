package assignment;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.*;

public class WriterPanel extends JPanel {
	public WriterPanel() {
		
		//Check to see if has credentials here
		JTabbedPane tabbedPane = new JTabbedPane();
        this.setLayout(new BorderLayout());
        this.add(tabbedPane,BorderLayout.CENTER);    
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        JPanel topButtons = new JPanel(new FlowLayout());
        topButtons.add(buttonLogout);
        topButtons.add(buttonResetPassword);
        mainPanel.add(topButtons,BorderLayout.NORTH);
        
        JPanel articleData = new JPanel(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.3;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 3;
        articleData.add(labelArticleName, constraints);
        constraints.gridy = 1;
        articleData.add(labelISSN, constraints);
        constraints.gridy = 2;
        articleData.add(labelReviewCount, constraints);
        constraints.gridy = 3;
        constraints.gridx = 0;
        constraints.gridwidth = 1;
        articleData.add(labelVerdict1, constraints);
        constraints.gridx = 1;
        articleData.add(labelVerdict2, constraints);
        constraints.gridx = 2;
        articleData.add(labelVerdict3, constraints);
        constraints.gridy = 4;
        constraints.gridx = 0;
        articleData.add(buttonSeeReview1, constraints);
        constraints.gridx = 1;
        articleData.add(buttonSeeReview2, constraints);
        constraints.gridx = 2;
        articleData.add(buttonSeeReview3, constraints);
        
        constraints.gridy = 5;
        constraints.gridx = 0;
        constraints.gridwidth = 3;
        articleData.add(labelArticleStatus, constraints);
        
        mainPanel.add(articleData,BorderLayout.CENTER);        
        tabbedPane.addTab("Author", null, mainPanel,"See Author Tasks");
        
        
        journalList = new JList<String>();
        journalList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        journalList.setLayoutOrientation(JList.VERTICAL);
        journalList.setVisibleRowCount(-1);
        JScrollPane journalListScrollPane = new JScrollPane(journalList);
        journalListScrollPane.setPreferredSize(new Dimension(250, 80));
        
        mainPanel.add(journalListScrollPane,BorderLayout.EAST);
        
        //////// only show when having reviewer privileges
        ReviewerPanel rp = new ReviewerPanel();
        tabbedPane.addTab("Reviewer", null, rp, "See Reviewer Tasks");
        
    }
	protected JLabel labelTopMessage = new JLabel("Welcome, ");
	
	protected JLabel labelArticleName = new JLabel("Article: ");
    protected JLabel labelISSN = new JLabel("ISSN: ");
    protected JLabel labelReviewCount = new JLabel("Reviews on Article: ");
    protected JLabel labelVerdict1 = new JLabel("Verdict 1: ");
    protected JLabel labelVerdict2 = new JLabel("Verdict 2: ");
    protected JLabel labelVerdict3 = new JLabel("Verdict 3: ");
    
    protected JButton buttonSeeReview1 = new JButton("Review 1");
    protected JButton buttonSeeReview2 = new JButton("Review 2");
    protected JButton buttonSeeReview3 = new JButton("Review 3");
    
    protected JButton buttonLogout = new JButton("Log Out");
    protected JButton buttonResetPassword = new JButton("Change Password");
    
    protected JLabel labelArticleStatus = new JLabel("Article Status: ");
    
    protected JList<String> journalList;
    
    public void addListeners(JFrame parent) {
    	
    }
}
