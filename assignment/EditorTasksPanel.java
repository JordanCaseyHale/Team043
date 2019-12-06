package assignment;

import java.util.ArrayList;
import java.util.List;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.*;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


public class EditorTasksPanel extends JPanel {
	
	private static String email;
	
	public EditorTasksPanel(String email) {
		this.email = email;
		//Check to see if has credentials here
        this.setLayout(new BorderLayout());
        
        JPanel topButtons = new JPanel(new FlowLayout());
        topButtons.add(labelTopMessage);
        topButtons.add(buttonLogout);
        topButtons.add(buttonResetPassword);
        topButtons.add(buttonAddEditor);
        topButtons.add(buttonManageChiefRole);
        topButtons.add(buttonRetire);
        this.add(topButtons,BorderLayout.NORTH);
        
		editionList = new JList<String>();
		editionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		editionList.setLayoutOrientation(JList.VERTICAL);
		editionList.setVisibleRowCount(-1);
		JScrollPane editionListScrollPane = new JScrollPane(editionList);
		editionListScrollPane.setPreferredSize(new Dimension(100,200));
        
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
        constraints.gridwidth = 3;
        constraints.gridx = 0;
        constraints.gridy = 2;
        articleData.add(labelArticleStatus, constraints);     
        constraints.gridy = 3;
        constraints.gridx = 0;
        constraints.gridwidth = 1;
        articleData.add(labelFinalVerdict1, constraints); 
        constraints.gridx = 1;
        articleData.add(labelFinalVerdict2, constraints); 
        constraints.gridx = 2;
        articleData.add(labelFinalVerdict3, constraints); 
        constraints.gridx = 1;
        constraints.gridy = 4;
        articleData.add(buttonAddToEdition, constraints); 
        constraints.gridx = 0;
        constraints.gridy = 4;
        articleData.add(buttonReject, constraints); 
        constraints.gridx = 2;
        constraints.gridy = 4;
        articleData.add(buttonAddEditionToJournal, constraints);
        constraints.gridx = 0;
        constraints.gridy = 5;
        articleData.add(labelUnpublishedEdition, constraints);
        constraints.gridx = 0;
        constraints.gridwidth = 3;
        constraints.gridy = 6;
        constraints.weighty = 0.2;
        articleData.add(editionListScrollPane, constraints);
        this.add(articleData,BorderLayout.CENTER);        
 
        submissionList = new JList<String>();
        submissionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        submissionList.setLayoutOrientation(JList.VERTICAL);
        submissionList.setVisibleRowCount(-1);
        JScrollPane journalListScrollPane = new JScrollPane(submissionList);
        journalListScrollPane.setPreferredSize(new Dimension(250, 80));
        
        this.add(journalListScrollPane,BorderLayout.EAST);
        
		for (Submission s : subs) {
			listModel.addElement("ISSN: "+s.getJournal()+" - "+s.getSubID()+", "+s.getName());
		}
		submissionList.setModel(listModel);
		
		//Show all unpublished editions
		System.out.println(listModel);

		for (Edition ed : eds) {
			listEditionModel.addElement("ISSN: "+ed.getISSN()+", Volume: "+ed.getVolume()+", Edition: "+ed.getEdition());
		}
		String[] exampleData = {"Click on a "};
		editionList.setModel(listEditionModel);
		
    }
	protected JLabel labelTopMessage = new JLabel("Welcome, ");
	
	protected JLabel labelArticleName = new JLabel("Article: ");
    protected JLabel labelISSN = new JLabel("ISSN: ");
    protected JLabel labelPDFLink = new JLabel("PDF Link: ");
    protected JLabel labelFinalVerdict1 = new JLabel("Verdict 1: ");
    protected JLabel labelFinalVerdict2 = new JLabel("Verdict 2: ");
    protected JLabel labelFinalVerdict3 = new JLabel("Verdict 3: ");
    protected JLabel labelUnpublishedEdition = new JLabel("Unpublished Editions");
    
    protected JLabel labelArticleStatus = new JLabel("Article Status: ");    
    
    protected JTextPane textPanePDFLink = new JTextPane();
    
    protected JButton buttonLogout = new JButton("Log Out");
    protected JButton buttonResetPassword = new JButton("Change Password");
    protected JButton buttonAddEditor = new JButton("Add New Editor");
    protected JButton buttonManageChiefRole = new JButton("Manage Chief Roles");
    protected JButton buttonRetire = new JButton("Retire");
    protected JButton buttonAddToEdition = new JButton("Add Article to Edition");
    protected JButton buttonReject = new JButton("Reject");
    protected JButton buttonAddEditionToJournal = new JButton("Add Edition to Journal");
    
    protected JList<String> submissionList;
    protected JList<String> editionList;
    
    private DefaultListModel<String> listModel = new DefaultListModel<>();
	private List<Submission> subs = EditorTasks.getSubmissions();
	private DefaultListModel<String> listEditionModel = new DefaultListModel<>();
	private List<Edition> eds = EditorTasks.getUnpublishedEditions();
    
    public void addListeners(JFrame parent) {
    	buttonLogout.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			EditorTasksPanel.email = null;
    			//go to main panel
    			parent.getContentPane().removeAll();
        		MainPanel nextPanel = new MainPanel();
        		nextPanel.addListeners(parent);
        		parent.getContentPane().add(nextPanel);
        		parent.revalidate(); 
        		parent.repaint();
    		}
    	});
    	//JList listener
    	submissionList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				//enable buttons
				List<String> verdicts = new ArrayList<String>();
				if (!buttonReject.isEnabled()) {
					buttonReject.setEnabled(true);
				}
				if (!buttonAddToEdition.isEnabled()) {
					buttonAddToEdition.setEnabled(true);
				}
				//gets info to display
				if (!submissionList.isSelectionEmpty()) {
					int index = submissionList.getSelectedIndex();
					Submission sub = subs.get(index);
					labelArticleName.setText("Article: "+sub.getName());
					labelISSN.setText("ISSN: "+sub.getJournal());
					//Get Verdicts
					verdicts = EditorTasks.getVerdicts(sub.getSubID());
				}
				int size = verdicts.size();
				if (size>0) {
					labelFinalVerdict1.setText("Verdict 1: "+verdicts.get(0));
				}
				else {
					labelFinalVerdict1.setText("Verdict 1: Not completed");
				}
				if (size>1) {
					labelFinalVerdict2.setText("Verdict 2: "+verdicts.get(1));
				}
				else {
					labelFinalVerdict2.setText("Verdict 2: Not completed");
				}
				if (size>2) {
					labelFinalVerdict3.setText("Verdict 3: "+verdicts.get(2));
				}
				else {
					labelFinalVerdict3.setText("Verdict 3: Not completed");
					buttonReject.setEnabled(false);
					buttonAddToEdition.setEnabled(false);
				}
				//fill out edition list
				System.out.println("Click");
				
				//uses the info to decide status
				//disable certain buttons
			}
    	});
    	
    	buttonReject.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			if (!submissionList.isSelectionEmpty()) {
    				String subValue = submissionList.getSelectedValue();
    				String subID = subValue.split(" - ")[2].split(",")[0].trim();
    				EditorTasks.rejectSubmission(subID);
    			}
    		}
    	});
    	
    	buttonAddToEdition.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			if (!submissionList.isSelectionEmpty()) {
    				if (!editionList.isSelectionEmpty()) {
    					String subValue = submissionList.getSelectedValue();
    					String edValue = editionList.getSelectedValue();
    					int editionSel = Integer.parseInt(edValue.split("Edition: ")[1].trim());
    					int volumeSel = Integer.parseInt(edValue.split(", ")[1].replaceAll("Volume: ", "").trim());
    					String issnSel = edValue.split(", ")[0].replaceAll("ISSN: ", "").trim();
    					String subID = subValue.split(" - ")[2].split(",")[0].trim();
    					String subISSN = subValue.split(" ")[1].trim();
    					System.out.println("issnSel = "+issnSel);
    					System.out.println("subISSN = "+subISSN);
    					//get sub issn
    					//if match edition issn then
    					if (subID.contentEquals(subISSN)) {
    						EditorTasks.publishArticle(subID, issnSel, volumeSel, editionSel);
    					}
    				}
    			}
    			//get info on article
    			//get info on selected edition
    			//publish article
    		}
    	});
    	
    	buttonAddEditionToJournal.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			//get edition selected
    			//submitted edition
    			if (!editionList.isSelectionEmpty()) {
    				String valueSelected = editionList.getSelectedValue();
    				EditorTasks.publishEdition(valueSelected);
    			}
    		}
    	});
    	
    	buttonManageChiefRole.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			//create dialog to give other editor chief role
    			
    		}
    	});
    	
    	buttonRetire.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			
    		}
    	});
    	
    	buttonResetPassword.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			System.out.println(EditorTasksPanel.email);
    			ChangePasswordDialog cpd = new ChangePasswordDialog(EditorTasksPanel.email);
    			cpd.AddListeners();
    			cpd.setVisible(true);
    		}
    	});
    }
}
