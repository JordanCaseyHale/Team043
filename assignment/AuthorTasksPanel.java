package assignment;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class AuthorTasksPanel extends JPanel {
	public AuthorTasksPanel(String email) {
		
		//Check to see if has credentials here
		JTabbedPane tabbedPane = new JTabbedPane();
        this.setLayout(new BorderLayout());
        this.add(tabbedPane,BorderLayout.CENTER);    
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        JPanel topButtons = new JPanel(new FlowLayout());
        labelTopMessage.setText("Welcome, "+email);
        topButtons.add(labelTopMessage);
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
        constraints.gridwidth = 1;
        articleData.add(labelPDFLink, constraints);
        textPanePDFLink.setEditable(false);
        textPanePDFLink.setContentType("text/html");
      
        constraints.gridx = 1;
        constraints.gridwidth = 2;
        articleData.add(textPanePDFLink, constraints);
        constraints.gridwidth = 3;
        constraints.gridx = 0;
        constraints.gridy = 3;
        articleData.add(labelReviewCount, constraints);
        constraints.gridy = 4;
        constraints.gridx = 0;
        constraints.gridwidth = 1;
        articleData.add(labelVerdict1, constraints);
        constraints.gridx = 1;
        articleData.add(labelVerdict2, constraints);
        constraints.gridx = 2;
        articleData.add(labelVerdict3, constraints);
        constraints.gridy = 5;
        constraints.gridx = 0;
        articleData.add(buttonSeeReview1, constraints);
        constraints.gridx = 1;
        articleData.add(buttonSeeReview2, constraints);
        constraints.gridx = 2;
        articleData.add(buttonSeeReview3, constraints);
        
        constraints.gridy = 6;
        constraints.gridx = 0;
        constraints.gridwidth = 3;
        articleData.add(labelArticleStatus, constraints);
        
        mainPanel.add(articleData,BorderLayout.CENTER);        
        tabbedPane.addTab("Author", null, mainPanel,"See Author Tasks");
        
        
        journalList = new JList<String>();
        journalList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        journalList.setLayoutOrientation(JList.VERTICAL);
        journalList.setVisibleRowCount(-1);
        
        submissions.clear();
        listModelSubs.clear();       
        ArrayList<Integer> subids = AuthorTasks.getSubIDs(email);
        for(int i : subids) {
        	System.out.println(i);
        	Submission s = Submission.getSubmissionByID(i);
        	System.out.println(s.getName());
        	listModelSubs.addElement(s.getName());
        	submissions.add(s);
        };
		
        journalList.setModel(listModelSubs);
        
        JScrollPane journalListScrollPane = new JScrollPane(journalList);
        journalListScrollPane.setPreferredSize(new Dimension(250, 80));
        
        mainPanel.add(journalListScrollPane,BorderLayout.EAST);
        
        //only show when having reviewer privileges
        ReviewerTasksPanel rp = new ReviewerTasksPanel(email);
        rp.addListeners();
        tabbedPane.addTab("Reviewer", null, rp, "See Reviewer Tasks");        
    }
	protected JLabel labelTopMessage = new JLabel("Welcome, ");
	
	protected JLabel labelArticleName = new JLabel("Article: ");
    protected JLabel labelISSN = new JLabel("ISSN: ");
    protected JLabel labelPDFLink = new JLabel("PDF Link: ");
    protected JLabel labelReviewCount = new JLabel("Reviews on Article: ");
    protected JLabel labelVerdict1 = new JLabel("Verdict 1: ");
    protected JLabel labelVerdict2 = new JLabel("Verdict 2: ");
    protected JLabel labelVerdict3 = new JLabel("Verdict 3: ");
    
    protected DefaultListModel<String> listModelSubs = new DefaultListModel<>();
    
    protected JTextPane textPanePDFLink = new JTextPane();
    
    protected JButton buttonSeeReview1 = new JButton("Review 1");
    protected JButton buttonSeeReview2 = new JButton("Review 2");
    protected JButton buttonSeeReview3 = new JButton("Review 3");
    
    protected JButton buttonLogout = new JButton("Log Out");
    protected JButton buttonResetPassword = new JButton("Change Password");
    
    protected JLabel labelArticleStatus = new JLabel("Article Status: ");
    
    protected JList<String> journalList;
    protected ArrayList<Submission> submissions = new ArrayList<Submission>();
    
    public void addListeners(JFrame parent) {
    	journalList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (!journalList.isSelectionEmpty()) {
					Submission selected = submissions.get(journalList.getSelectedIndex());

					labelArticleName.setText("Article: " + selected.getName());
					labelISSN.setText("ISSN: " + selected.getJournal());
					textPanePDFLink.setText(selected.getPdfLink());
					int[] reviewsDone = selected.getReviews();
					String[] initVers = selected.getInitVerdicts();
					String[] vers = selected.getVerdicts();
					String[] responses = selected.getResponses();
					int reviewCounter = 0;
					int initVersCounter = 0;
					int responsesCounter = 0;
					int versCounter = 0;
					for(int i = 0;i<3;i++) {
						String label = "";
						if(reviewsDone[i] != 0) {
							reviewCounter++;
						}
						if(responses[i] != null) {
							reviewCounter++;
						}
						if(initVers[i] != null) {
							initVersCounter++;
							label = initVers[i];
						}
						if(vers[i] != null) {
							versCounter++;
							label = vers[i];
						}
						switch(i) {
							case 0:
								labelVerdict1.setText("Verdict 1 " + label);
								break;
							case 1:
								labelVerdict2.setText("Verdict 2 " + label);
								break;
							case 2:
								labelVerdict3.setText("Verdict 3 " + label);
								break;
							default:
								break;
						}

					}
					
					labelReviewCount.setText("Reviews on Article: " + reviewCounter);
					String statusString;
					if (reviewCounter <= 0) {
						statusString = "Submitted";
					}
					else if(initVersCounter < 3){
						statusString = "Reviews Recieved";
					}
					else if(responsesCounter < 3) {
						statusString = "Initial Verdict";
					}
					else if(versCounter < 3) {
						statusString = "Responses Recieved";
					}
					else if(!selected.getComplete()) {
						statusString = "Final Verdict";
					}
					else {
						statusString = "Completed";
					}
					labelArticleStatus.setText("Article Status: "+statusString);
				}
			}
		});
    	buttonLogout.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			parent.getContentPane().removeAll();
        		MainPanel nextPanel = new MainPanel();
        		nextPanel.addListeners(parent);
        		parent.getContentPane().add(nextPanel);
        		parent.revalidate(); 
        		parent.repaint();
    		}
    	});
    }
}
