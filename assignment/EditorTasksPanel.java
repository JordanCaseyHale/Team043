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
        this.add(topButtons,BorderLayout.NORTH);
        
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
        constraints.gridx = 0;
        constraints.gridy = 4;
        articleData.add(buttonAccept, constraints); 
        constraints.gridy = 5;
        articleData.add(buttonReject, constraints); 
        constraints.gridy = 6;
        articleData.add(buttonAddToJournal, constraints); 
        this.add(articleData,BorderLayout.CENTER);        
 
        submissionList = new JList<String>();
        submissionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        submissionList.setLayoutOrientation(JList.VERTICAL);
        submissionList.setVisibleRowCount(-1);
        JScrollPane journalListScrollPane = new JScrollPane(submissionList);
        journalListScrollPane.setPreferredSize(new Dimension(250, 80));
        
        this.add(journalListScrollPane,BorderLayout.EAST);
        
		for (Submission s : subs) {
			listModel.addElement(s.getName());
		}
		submissionList.setModel(listModel);
    }
	protected JLabel labelTopMessage = new JLabel("Welcome, ");
	
	protected JLabel labelArticleName = new JLabel("Article: ");
    protected JLabel labelISSN = new JLabel("ISSN: ");
    protected JLabel labelPDFLink = new JLabel("PDF Link: ");
    protected JLabel labelFinalVerdict1 = new JLabel("Verdict 1: ");
    protected JLabel labelFinalVerdict2 = new JLabel("Verdict 2: ");
    protected JLabel labelFinalVerdict3 = new JLabel("Verdict 3: ");
    
    protected JLabel labelArticleStatus = new JLabel("Article Status: ");    
    
    protected JTextPane textPanePDFLink = new JTextPane();
    
    protected JButton buttonLogout = new JButton("Log Out");
    protected JButton buttonResetPassword = new JButton("Change Password");
    protected JButton buttonAddEditor = new JButton("Add New Editor");
    protected JButton buttonAccept = new JButton("Accept");
    protected JButton buttonReject = new JButton("Reject");
    protected JButton buttonAddToJournal = new JButton("Add To Journal");
    
    protected JList<String> submissionList;
    
    private DefaultListModel<String> listModel = new DefaultListModel<>();
	private List<Submission> subs = EditorTasks.getSubmissions();
    
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
				if (!buttonAddToJournal.isEnabled()) {
					buttonAddToJournal.setEnabled(true);
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
				if (size>1) {
					labelFinalVerdict2.setText("Verdict 2: "+verdicts.get(1));
				}
				if (size>2) {
					labelFinalVerdict3.setText("Verdict 3: "+verdicts.get(2));
				}
				
				//uses the info to decide status
				//disable certain buttons
			}
    	});
    }
}
