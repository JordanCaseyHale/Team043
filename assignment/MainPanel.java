package assignment;

import java.awt.*;
import java.awt.event.*;

import javax.accessibility.AccessibleContext;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

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
        DefaultListModel<String> listModel = new DefaultListModel<>();
        listModel.addElement("Journals1");
        listModel.addElement("Journals2");
        listModel.addElement("Journals3");
        journalList = new JList(exampleData);
        journalList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        journalList.setLayoutOrientation(JList.VERTICAL);
        journalList.setVisibleRowCount(-1);
        JScrollPane journalListScrollPane = new JScrollPane(journalList);
        journalListScrollPane.setPreferredSize(new Dimension(250, 80));
        
        this.add(journalListScrollPane,BorderLayout.CENTER);
        
        JPanel bottomButtons = new JPanel();
        bottomButtons.add(buttonBack);
        this.add(bottomButtons,BorderLayout.SOUTH);
        
        listSection = "Journals";
    }
 
	protected JButton buttonNewAuthor = new JButton("New Author");
    protected JButton buttonLogin = new JButton("Login");
    protected JList<String> journalList;
    protected JButton buttonBack = new JButton("Back");
    protected String listSection;
    protected String journal, volume, edition, article;

    
    public void addListeners(JFrame parent) {
        buttonNewAuthor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	//AuthorPanel
            	
            }
        });
        buttonLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	parent.getContentPane().removeAll();
            	parent.getContentPane().add(new LoginPanel(), BorderLayout.CENTER);
            	parent.revalidate(); 
            	parent.repaint(); 
            }
        });
		journalList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					String valueSelected = journalList.getSelectedValue();
					//Expand the select tree
					
					switch (listSection) {
						case "Journal":
							journal = valueSelected;
							listSection = "Volume";
							String[] volumes = JournalList.getVolumes(journal);
							//Change list display
							journalList.setListData(volumes);
							break;
						case "Volume":
							volume = valueSelected;
							listSection = "Edition";
							String[] editions = JournalList.getEditions(volume, journal);
							journalList.setListData(editions);
							break;
						case "Edition":
							edition = valueSelected;
							listSection = "Articles";
							String[] articles = JournalList.getArticles(edition, volume, journal);
							journalList.setListData(articles);
							break;
						case "Articles":
							article = valueSelected;
							String[] pageRange = article.split(" ");
							listSection = "Article";
							Article article = JournalList.getArticle(pageRange[1], valueSelected, valueSelected, valueSelected);
							//Do something to display article
					}
					/**
					System.out.println(valueSelected);
					if (x) {
						String[] tmp = {"11","22","33"};
						journalList.setListData(tmp);
					}
					x = false;
					*/
					System.out.println(valueSelected);
				}
			}
		});
		buttonBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Go back through List
				switch (listSection) {
					case "Volume":
						List<List<String>> journalsData = JournalList.getJournals();
						String[] journals = null;
						for (int i=0; i<(journalsData.size());i++) {
							journals[i] = journalsData.get(i).get(1);
						}
						journalList.setListData(journals);
				}
			}
		});
    }
}