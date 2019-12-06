package assignment;

import java.awt.BorderLayout;
import java.util.*;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ReviewerTasksPanel extends JPanel {
	public ReviewerTasksPanel(String email) {
		
		//Check to see if has credentials here
		this.email = email;
        this.setLayout(new BorderLayout());
      
        JPanel topButtons = new JPanel(new FlowLayout());
        labelTopMessage.setText("Welcome, "+email);
        topButtons.add(labelTopMessage);
        topButtons.add(buttonLogout);
        topButtons.add(buttonResetPassword);
        this.add(topButtons,BorderLayout.NORTH);
        update();
        
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
        constraints.weightx = 0.3;
        constraints.gridx = 1;
        constraints.gridwidth = 1;
        articleData.add(buttonFinalVerdict, constraints);
        
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
	protected void update() {
		journalListModel.clear();
		selectedJournalListModel.clear();
        toSelect.clear();
        toSelect = ReviewerTasks.getReviewableList(this.email);
        for(Submission sub:toSelect) {
        	journalListModel.addElement(sub.getName());
        }
        journalList.setModel(journalListModel);
        alreadySelected.clear();
		alreadySelected = ReviewerTasks.getReviewsList(this.email);
		for(Submission sub:alreadySelected) {
        	selectedJournalListModel.addElement(sub.getName());
        }
		selectedJournalList.setModel(selectedJournalListModel);
		labelReviewsNeeded.setText("Reviews to do: "+ReviewerTasks.ReviewsRemaining(email));
	}
	protected JLabel labelTopMessage = new JLabel("Welcome, ");
	
	protected JLabel labelReviewsNeeded = new JLabel("Reviews to do: ");
	protected JLabel labelSideMessage = new JLabel("Select Articles to Review");
	
	protected JLabel labelArticleName = new JLabel("Article: ");
    protected JLabel labelISSN = new JLabel("ISSN: ");
    protected JLabel labelPDFLink = new JLabel("PDF Link: ");
    
    protected JTextPane textPanePDFLink = new JTextPane();
    
    protected JButton buttonReview = new JButton("Make Review");

    protected JButton buttonFinalVerdict = new JButton("Make Final Verdict");
    
    protected JButton buttonLogout = new JButton("Log Out");
    protected JButton buttonResetPassword = new JButton("Change Password");
    protected JButton buttonChooseArticles = new JButton("Choose to Review");
    
    protected DefaultListModel<String> journalListModel = new DefaultListModel<>();
    protected DefaultListModel<String> selectedJournalListModel = new DefaultListModel<>();
    
    protected ArrayList<Submission> toSelect  = new ArrayList<Submission>();
    protected ArrayList<Submission> alreadySelected  = new ArrayList<Submission>();
    protected String email;
    
    protected JList<String> journalList = new JList<String>(journalListModel);
    protected JList<String> selectedJournalList = new JList<String>(selectedJournalListModel);
    ReviewerTasksPanel parentRevPanel = this;
    
    public void addListeners(AuthorTasksPanel parent) {
    	 buttonChooseArticles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(journalList.getSelectedIndices().length > ReviewerTasks.ReviewsRemaining(email)) {
					JOptionPane.showMessageDialog(parentRevPanel, "You can not select more reviews than you currently have allocated.");
					return;
				}
				for(int i:journalList.getSelectedIndices()) {
					Submission sub = toSelect.get(i);
					ReviewerTasks.submitChosenSubmission(ReviewerTasks.getReviewerID(email), sub.getSubID());
				}
				update();
			} 
    	 });
    	 selectedJournalList.addListSelectionListener(new ListSelectionListener() {
 			public void valueChanged(ListSelectionEvent e) {
 				if (!selectedJournalList.isSelectionEmpty()) {
 					Submission selected = alreadySelected.get(selectedJournalList.getSelectedIndex());

 					labelArticleName.setText("Article: " + selected.getName());
 					labelISSN.setText("ISSN: " + selected.getJournal());
 					textPanePDFLink.setText(selected.getPdfLink());
 					Submission sub = alreadySelected.get(selectedJournalList.getSelectedIndex());
 					buttonReview.setEnabled(!ReviewerTasks.reviewExists(ReviewerTasks.getReviewerID(email),sub.getSubID()));
 					buttonFinalVerdict.setEnabled(ReviewerTasks.canPutFinalVerdict(ReviewerTasks.getReviewerID(email),sub.getSubID()));
 					
 				}	
 			}
 		});
    	 buttonReview.addActionListener(new ActionListener() {
             public void actionPerformed(ActionEvent e) {
            	Submission selected = alreadySelected.get(selectedJournalList.getSelectedIndex());
            	MakeReviewDialog dlg = new MakeReviewDialog(selected,ReviewerTasks.getReviewerID(email));
             	dlg.addListeners(parentRevPanel);
             	dlg.setVisible(true);
             	System.out.print("make panel");
             }
    	 });
    	 buttonFinalVerdict.addActionListener(new ActionListener() {
             public void actionPerformed(ActionEvent e) {
            	Submission selected = alreadySelected.get(selectedJournalList.getSelectedIndex());
            	ViewResponsesDialog dlg = new ViewResponsesDialog(selected.getSubID(),ReviewerTasks.getReviewerID(email));
             	dlg.addListeners(parentRevPanel);
             	dlg.setVisible(true);
             	System.out.print("make panel");
             }
    	 });
    	 buttonResetPassword.addActionListener(new ActionListener() {
    		 public void actionPerformed(ActionEvent e) {
    			 ChangePasswordDialog cpd = new ChangePasswordDialog(email);
    			 cpd.AddListeners();
    			 cpd.setVisible(true);
    		 }
    	 });
    	 buttonLogout.addActionListener(new ActionListener() {
     		public void actionPerformed(ActionEvent e) {
     			parent.buttonLogout.doClick();
     		}
     	});
    }



}