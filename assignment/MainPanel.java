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
        /*
        DefaultListModel<String> listModel = new DefaultListModel<>();
		List<List<String>> journalsData = JournalList.getJournals();
		for (int i=0; i<(journalsData.size());i++) {
			listModel.addElement(journalsData.get(i).get(0) + ", " + journalsData.get(i).get(1));
		}
		*/
        //journalList = new JList<String>(listModel);
        journalList = new JList<String>(exampleData);
        journalList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        journalList.setLayoutOrientation(JList.VERTICAL);
        journalList.setVisibleRowCount(-1);
        JScrollPane journalListScrollPane = new JScrollPane(journalList);
        journalListScrollPane.setPreferredSize(new Dimension(250, 80));
        
        this.add(journalListScrollPane,BorderLayout.CENTER);
        
        JPanel bottomButtons = new JPanel();
        bottomButtons.add(buttonBack);
        this.add(bottomButtons,BorderLayout.SOUTH);
        
        JPanel articleDisplay = new JPanel();
        articleDisplay.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        
        articleDisplay.setPreferredSize(new Dimension(350, 420));
        constraints.gridy = 0;
        articleDisplay.add(articleName, constraints);
        constraints.gridy = 1;
        articleDisplay.add(issnNo, constraints);
        constraints.gridy = 2;
        articleDisplay.add(abstractText, constraints);
        constraints.gridy = 3;
        articleDisplay.add(pdfLink, constraints);
        constraints.gridx = 0;
        constraints.gridy = 4;
        articleDisplay.add(respondingAuthor, constraints);
        constraints.gridx = 0;
        constraints.gridy = 5;
        articleDisplay.add(respondingEmail, constraints);
        constraints.gridx = 1;
        constraints.gridy = 4;
        articleDisplay.add(otherAuthors, constraints);
        this.add(articleDisplay, BorderLayout.EAST);
        
        listSection = "Test";
    }
 
	protected JButton buttonNewAuthor = new JButton("New Author");
    protected JButton buttonLogin = new JButton("Login");
    protected JList<String> journalList;
    protected JButton buttonBack = new JButton("Back");
    protected String listSection;
    protected String journal, volume, edition, article;
    
    //Article Display
    protected JLabel articleName = new JLabel("Article: ");
    protected JLabel issnNo = new JLabel("ISSN: ");
    protected JLabel abstractText = new JLabel("Abstract: ");
    protected JLabel pdfLink = new JLabel("PDF Link: ");
    protected JLabel respondingAuthor = new JLabel("Responding Author: ");
    protected JLabel respondingEmail = new JLabel("Email: ");
    protected JLabel otherAuthors = new JLabel("Authors: ");

    
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
		journalList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					String valueSelected = journalList.getSelectedValue();
					//Expand the select tree
					
					switch (listSection) {
						case "Journal":
							journal = valueSelected;
							String[] parts = journal.split(",");
							listSection = "Volume";
							System.out.println(journal);
							System.out.println("middle");
							System.out.println(parts[1].trim());
							String[] volumes = JournalList.getVolumes(parts[1].trim());
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
							String[] info = article.split(" ");
							String pageRange = info[1];
							listSection = "Article";
							Article article = JournalList.getArticle(pageRange, edition, volume, journal);
							//Do something to display article
							articleName.setText("Article: \n" + article.getName());
							issnNo.setText("ISSN:\n" + article.getISSN());
							abstractText.setText("Abstract: \n" + article.getAbstractPara());
							pdfLink.setText("PDF Link:\n" + article.getPdfLink());
							respondingAuthor.setText("Responding Author:\n" + article.getRespondName());
							respondingEmail.setText("Email:\n" + article.getRespondEmail());
							otherAuthors.setText("Authors:\n" + article.getCoAuthors());
							break;
							
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
					case "Articles":
						String[] editions = JournalList.getEditions(volume, journal);
						journalList.setListData(editions);
						listSection = "Edition";
						break;
					case "Edition":
						String[] volumes = JournalList.getVolumes(journal);
						journalList.setListData(volumes);
						listSection = "Volume";
						break;
					case "Volume":
						List<List<String>> journalsData = JournalList.getJournals();
						String[] journals = null;
						for (int i=0; i<(journalsData.size());i++) {
							journals[i] = journalsData.get(i).get(1);
						}
						journalList.setListData(journals);
						listSection = "Journal";
						break;
				}
			}
		});
    }
}