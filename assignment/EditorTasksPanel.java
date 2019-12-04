package assignment;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;

public class EditorTasksPanel extends JPanel {
	public EditorTasksPanel() {
		
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
    
    protected JList<String> journalList;
    
    public void addListeners(JFrame parent) {
    	
    }
}
