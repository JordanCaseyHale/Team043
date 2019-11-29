package assignment;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

public class MainPanel extends JPanel {
	public MainPanel() {
        this.setLayout(new BorderLayout());
        JPanel topButtons = new JPanel();
        topButtons.setLayout(new FlowLayout());
        topButtons.add(new JLabel("Welcome"));
        topButtons.add(buttonNewAuthor);
        topButtons.add(buttonLogin);
        this.add(topButtons,BorderLayout.NORTH);
        
        String[] exampleData = {"Journal01","Journal02","Journal03"};
        //String[] journals = JournalList.getJournals();
        journalList = new JList(exampleData);
        journalList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        journalList.setLayoutOrientation(JList.VERTICAL);
        journalList.setVisibleRowCount(-1);
        JScrollPane journalListScrollPane = new JScrollPane(journalList);
        journalListScrollPane.setPreferredSize(new Dimension(250, 80));
        
        this.add(journalListScrollPane,BorderLayout.CENTER);
    }
 
	protected JButton buttonNewAuthor = new JButton("New Author");
    protected JButton buttonLogin = new JButton("Login"); 
    protected JList journalList; 
    
    public void addListeners(JFrame parent) {
        buttonNewAuthor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	parent.getContentPane().removeAll();
            	AuthorPanel nextPanel = new AuthorPanel();
            	nextPanel.addListeners(parent);
            	parent.getContentPane().add(nextPanel, BorderLayout.CENTER);
            	parent.revalidate(); 
            	parent.repaint(); 
            }
        });
        buttonLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	parent.getContentPane().removeAll();
            	LoginPanel nextPanel = new LoginPanel();
            	nextPanel.addListeners(parent);
            	parent.getContentPane().add(nextPanel, BorderLayout.CENTER);
            	parent.revalidate(); 
            	parent.repaint(); 
            }
        });
    }
}