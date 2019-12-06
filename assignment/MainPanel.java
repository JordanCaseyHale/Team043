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
        
        
		List<Journal> journalsData = JournalList.getJournals();
		for (int i=0; i<(journalsData.size());i++) {
			listModel.addElement(journalsData.get(i).getTitle() + ", ISSN: " + journalsData.get(i).getISSN());
		}
		
        journalList = new JList<String>(listModel);
        //journalList = new JList<String>(exampleData);
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
        
        listSection = "Journal";
    }
 
	protected JButton buttonNewAuthor = new JButton("New Author");
    protected JButton buttonLogin = new JButton("Login");
    protected JList<String> journalList;
    protected JButton buttonBack = new JButton("Back");
    protected String listSection;
    protected String article;
    protected Journal journal = new Journal();
    protected Volume volume = new Volume();
    protected Edition edition = new Edition();
    
    //Article Display
    protected JLabel articleName = new JLabel("Article: ");
    protected JLabel issnNo = new JLabel("ISSN: ");
    protected JLabel abstractText = new JLabel("Abstract: ");
    protected JLabel pdfLink = new JLabel("PDF Link: ");
    protected JLabel respondingAuthor = new JLabel("Responding Author: ");
    protected JLabel respondingEmail = new JLabel("Email: ");
    protected JLabel otherAuthors = new JLabel("Authors: ");

    protected DefaultListModel<String> listModel = new DefaultListModel<>();
    
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
				if (!journalList.isSelectionEmpty()) {
					String valueSelected = journalList.getSelectedValue();
					//journalList.clearSelection();
					//System.out.println("start");
					//Expand the select tree
					
					switch (listSection) {
						case "Journal":
							journal.setISSN(valueSelected.split("ISSN:")[1].trim());
							journal.setTitle(valueSelected.split("ISSN:")[0].trim());
							listSection = "Volume";
							//System.out.println(journal);
							//System.out.println("middle");
							//System.out.println(parts[1].trim());
							List<Volume> volumes = JournalList.getVolumes(journal.getISSN());
							//System.out.println(volumes);
							//Change list display
							listModel.clear();
							String str;
							for (Volume v : volumes) {
								str = "Year: "+v.getYear() + " Volume:" + v.getVolume();
								listModel.addElement(str);
							}
							journalList.setModel(listModel);
							break;
						case "Volume":
							volume.setVolume(Integer.parseInt(valueSelected.split("Volume:")[1].trim()));
							volume.setYear(Integer.parseInt(valueSelected.split("Volume:")[0].replace("Year:", "").trim()));
							listSection = "Edition";
							System.out.println("edition");
							//System.out.println(volumeNo);
							//System.out.println(journalName);
							List<Edition> editions = JournalList.getEditions(volume.getVolume(), journal.getISSN());
							listModel.clear();
							for (Edition ed : editions) {
								str = "Month: " + ed.getMonth() + " Edition: " + ed.getEdition();
								listModel.addElement(str);
							}
							journalList.setModel(listModel);
							break;
						case "Edition":
							edition.setEdition(Integer.parseInt(valueSelected.split("Edition:")[1].trim()));
							edition.setMonth(valueSelected.split("Edition:")[0].replace("Month:","").trim());
							listSection = "Articles";
							List<String> articles = JournalList.getArticles(edition.getEdition(), volume.getVolume(), journal.getISSN());
							listModel.clear();
							for (String s : articles) {
								listModel.addElement(s);
							}
							journalList.setModel(listModel);
							break;
						case "Articles":
							article = valueSelected;
							String[] info = article.split("Page Range:");
							String pageRange = info[1].trim();
							Article article = JournalList.getArticle(pageRange, edition.getEdition(), volume.getVolume(), journal.getISSN());
							//Do something to display article
							articleName.setText("Article: \n" + article.getName());
							issnNo.setText("ISSN:\n" + article.getISSN());
							abstractText.setText("Abstract: \n" + article.getAbstractPara());
							pdfLink.setText("PDF Link:\n" + article.getPdfLink());
							
							respondingAuthor.setText("Responding Author:\n" + article.getRespondName());
							respondingEmail.setText("Email:\n" + article.getRespondEmail());
							List<Author> authors = article.getCoAuthors();
							String authorsText = "";
							for (Author a: authors) {
								authorsText += "\n"+a.getTitle()+", "+a.getForename()+" "+a.getSurname();
							}
							otherAuthors.setText("Authors:"+authorsText);
							
							System.out.println("Article should be displayed");
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
					//System.out.println(valueSelected);
				}
			}
		});
		buttonBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Go back through List
				
				switch (listSection) {
					case "Articles":
						List<Edition> editions = JournalList.getEditions(volume.getVolume(), journal.getISSN());
						listModel.clear();
						System.out.println("Back");
						String str;
						for (Edition ed : editions) {
							str = "Month: " + ed.getMonth() + " Edition: " + ed.getEdition();
							listModel.addElement(str);
						}
						journalList.setModel(listModel);
						listSection = "Edition";
						break;
					case "Edition":
						List<Volume> volumes = JournalList.getVolumes(journal.getISSN());
						listModel.clear();
						for (Volume v : volumes) {
							str = "Year: "+v.getYear() + " Volume:" + v.getVolume();
							listModel.addElement(str);
						}
						journalList.setModel(listModel);
						listSection = "Volume";
						break;
					case "Volume":
						List<Journal> journals = JournalList.getJournals();
						listModel.clear();
						for (Journal j : journals) {
							listModel.addElement(j.getTitle() + ", ISSN: " + j.getISSN());
						}
						journalList.setModel(listModel);
						listSection = "Journal";
						break;
				}
			}
		});
    }
}