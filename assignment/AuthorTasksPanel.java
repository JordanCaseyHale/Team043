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
		this.email = email;
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
        buttonSeeReview1.setEnabled(false);
        buttonSeeReview2.setEnabled(false);
        buttonSeeReview3.setEnabled(false);
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
        if (ReviewerTasks.ReviewsRemaining(email)>0) {
        	ReviewerTasksPanel rp = new ReviewerTasksPanel(email);
        	rp.addListeners(this);
        	tabbedPane.addTab("Reviewer", null, rp, "See Reviewer Tasks");        
        }
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
    protected String email;
    
    public void addListeners(JFrame parent) {
    	parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	AuthorTasksPanel parentSubPanel = this;
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
					int initVersCounter = 0;
					int responsesCounter = 0;
					int versCounter = 0;
					for(int i = 0;i<3;i++) {
						String label = "";
						if(responses[i] != null) {
							responsesCounter++;
						}
						if(initVers[i] != null) {
							initVersCounter++;
							label = initVers[i];
						}
						if(vers[i] != null) {
							versCounter++;
							label = vers[i];
						}
						Boolean shouldEnableButton =((reviewsDone[i] != 0)&&responses[i]==null); 
						switch(i) {
							case 0:
								labelVerdict1.setText("Verdict 1: " + label);
								buttonSeeReview1.setEnabled(shouldEnableButton);
								break;
							case 1:
								labelVerdict2.setText("Verdict 2: " + label);
								buttonSeeReview2.setEnabled(shouldEnableButton);
								break;
							case 2:
								labelVerdict3.setText("Verdict 3: " + label);
								buttonSeeReview3.setEnabled(shouldEnableButton);
								break;
							default:
								break;
						}

					}
					
					labelReviewCount.setText("Reviews on Article: " + (3-Submission.ReviewsLeft(selected)));
					String statusString;
					if (Submission.ReviewsLeft(selected)>0) {
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
    			MainHandler.logout(parent);
    		}
    	});
    	
    	buttonSeeReview1.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			Submission selected = submissions.get(journalList.getSelectedIndex());
				int[] reviewsDone = selected.getReviews();
				if (reviewsDone[0] != 0) {
					ViewReviewDialog dlg = new ViewReviewDialog(reviewsDone[0],selected.getSubID());
	            	dlg.addListeners(parentSubPanel);
	            	dlg.setVisible(true);
				}
    		}
    	});
    	buttonSeeReview2.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			Submission selected = submissions.get(journalList.getSelectedIndex());
				int[] reviewsDone = selected.getReviews();
				if (reviewsDone[1] != 0) {
					ViewReviewDialog dlg = new ViewReviewDialog(reviewsDone[1],selected.getSubID());
	            	dlg.addListeners(parentSubPanel);
	            	dlg.setVisible(true);
				}
    		}
    	});
    	buttonSeeReview3.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			Submission selected = submissions.get(journalList.getSelectedIndex());
				int[] reviewsDone = selected.getReviews();
				if (reviewsDone[2] != 0) {
					ViewReviewDialog dlg = new ViewReviewDialog(reviewsDone[2],selected.getSubID());
	            	dlg.addListeners(parentSubPanel);
	            	dlg.setVisible(true);
				}
    		}
    	});
    	
    	buttonResetPassword.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
				ChangePasswordDialog cpd = new ChangePasswordDialog(email);
				cpd.AddListeners();
				cpd.setVisible(true);
    		}
    	});
    }
}
