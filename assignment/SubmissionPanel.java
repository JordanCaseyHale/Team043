package assignment;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

public class SubmissionPanel extends JPanel {
	//TODO: Dialog where you can allocate reviews and choose correnspondent
	private Author author;
	
	public SubmissionPanel(Author author) {
		this.author = author;
        this.setLayout(new BorderLayout());
        JPanel grid = new JPanel(new GridBagLayout());
        this.add(grid,BorderLayout.NORTH);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weighty = 1;       
        constraints.weightx = 0.3;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        grid.add(labelTitle, constraints);

        constraints.gridx = 1;
        constraints.gridwidth = 2;
        grid.add(textFieldTitle,constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;     
        constraints.gridwidth = 1;
        grid.add(labelPDFLink, constraints);

        constraints.gridx = 1;
        constraints.gridwidth = 2;
        grid.add(textFieldPDFLink,constraints);
        
        constraints.gridx = 0;
        constraints.gridy = 2;     
        constraints.gridwidth = 1;
        grid.add(labelJournal, constraints);

        constraints.gridx = 1;
        constraints.gridwidth = 2;
        List<Journal> journalsData = JournalList.getJournals();
		for (int i=0; i<(journalsData.size());i++) {
			journalComboBoxModel.addElement(journalsData.get(i).getTitle() + ", ISSN: " + journalsData.get(i).getISSN());
		}
        comboBoxJournals.setModel(journalComboBoxModel);
        grid.add(comboBoxJournals,constraints); 
        
        
        constraints.gridx = 0;
        constraints.gridy = 3;     
        constraints.gridwidth = 1;
        grid.add(labelCoauthorList, constraints);

        constraints.gridx = 1;
        constraints.gridwidth = 1;
        grid.add(buttonAddCoauthor,constraints);  
        
        constraints.gridx = 2;
        constraints.gridwidth = 1;
        grid.add(buttonRemoveCoauthor,constraints);  
        
        constraints.weightx = 1;
        constraints.weighty = 0.5;
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 3;
        
        
        listCoauthors.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listCoauthors.setLayoutOrientation(JList.VERTICAL);
        JScrollPane sp = new JScrollPane(listCoauthors);
        JScrollBar bar = sp.getVerticalScrollBar();
        bar.setPreferredSize(new Dimension(10, 0));
        grid.add(sp,constraints); 
        
        constraints.weightx = 0.3;
        constraints.gridx = 0;
        constraints.gridy = 5;
        constraints.gridwidth = 1;
        grid.add(labelAbstract, constraints);
        
        textAreaAbstract.setLineWrap(true);
        JScrollPane textsp = new JScrollPane(textAreaAbstract);
        JScrollBar textspbar = textsp.getVerticalScrollBar();
        textspbar.setPreferredSize(new Dimension(10, 0));
        this.add(textsp,BorderLayout.CENTER);
        
       
        JPanel bottomButtons = new JPanel(new FlowLayout());
        bottomButtons.add(buttonBack);
        bottomButtons.add(buttonMakeSubmission);      
        this.add(bottomButtons,BorderLayout.SOUTH);
        
    }
	protected JLabel labelTitle = new JLabel ("Title");
	protected JLabel labelPDFLink = new JLabel ("PDF Link");
	protected JLabel labelJournal = new JLabel ("Target Journal");
	protected JLabel labelCoauthorList = new JLabel ("Co-Authors");
	protected JLabel labelAbstract= new JLabel ("Article Abstract");
	

	protected DefaultListModel<String> listModelCoauthors = new DefaultListModel<>();
	protected JList<String> listCoauthors = new JList<String>(listModelCoauthors);	
	protected ArrayList<Author> listCoauthorData = new ArrayList<Author>();
	protected JComboBox<String> comboBoxJournals = new JComboBox<String>();
    protected DefaultComboBoxModel<String> journalComboBoxModel = new DefaultComboBoxModel<String>();	
	
	protected String respondTitle;
	protected String respondForename;
	protected String respondSurname;
	protected String respondEmail;
	
	protected JButton buttonAddCoauthor = new JButton("Add Co-author"); 
	protected JButton buttonRemoveCoauthor = new JButton("Remove Co-author"); 
	
	protected Submission sub = new Submission();
	
	protected JTextField textFieldTitle = new JTextField(30);
	protected JTextArea textAreaAbstract = new JTextArea(20,10);
	protected JTextField textFieldPDFLink = new JTextField(30);
	
	public void addCoauthor(String title, String forename, String surname, String email, String affiliation, String pwhash) {
		//data into author object
		Author data = new Author(title, forename, surname, email, affiliation, pwhash); 
		if (data.isNotComplete()) {
			JOptionPane.showMessageDialog(this,"Not all fields were filled out.");
			return;
		}
		listModelCoauthors.addElement(title+". "+forename+" "+surname);
		//add author to list of coauthors
		listCoauthorData.add(data);
	}
	
	public void setMainAuthor(String title, String forename, String surname, String email) {
		//mainAuthorData = new ArrayList<String>(Arrays.asList(title,forename,surname,email,pwhash));
    	sub.setRespondTitle(title);
    	sub.setRespondForename(forename);
    	sub.setRespondSurname(surname);
    	sub.setRespondEmail(email);
	}
	public void submitSubmission(List<Author> allAuthors,List<Integer> reviewCounters) {
		//Add all author accounts to DB
    	int i =0;
		for(Author ath : allAuthors) {
			AuthorTasks.createAccount(ath.getEmail(), ath.getTitle(), ath.getForename(), ath.getSurname(), ath.getAffiliation(), ath.getPasswordhashed());
			AuthorTasks.addReviewerPrivileges(ath.getEmail(),(int)reviewCounters.get(i));
			i++;
		}
		int generatedSubID = AuthorTasks.submissionToDB(sub);
		for(Author ath : allAuthors) {
			AuthorTasks.addSubAuthor(ath.getEmail(),generatedSubID);
		}
		
		JFrame parent = (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, this);
		
		boolean succ = Main.login(author.getEmail(), author.getPasswordhashed(), "Author");
    	if (succ) {
    		System.out.println("succ");
        	parent.getContentPane().removeAll();
        	AuthorTasksPanel nextPanel = new AuthorTasksPanel(author.getEmail());
        	nextPanel.addListeners(parent);
        	parent.getContentPane().add(nextPanel);
        	parent.revalidate(); 
        	parent.repaint();   		
    	}
	}
    protected JButton buttonMakeSubmission = new JButton("Make Submission"); 
    protected JButton buttonBack = new JButton("Back"); 
    public void addListeners(JFrame parent) {
    	buttonBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	parent.getContentPane().removeAll();
            	MainPanel nextPanel = new MainPanel();
            	nextPanel.addListeners(parent);
            	parent.getContentPane().add(nextPanel);
            	parent.revalidate(); 
            	parent.repaint(); 
            }
        });
        SubmissionPanel parentSubPanel = this;
        buttonMakeSubmission.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	//pass info to submission object
            	sub.setName(textFieldTitle.getText().trim());
            	sub.setPdfLink(textFieldPDFLink.getText().trim());
            	sub.setAbstractPara(textAreaAbstract.getText().trim());
            	sub.setCoAuthors(listCoauthorData);
            	sub.setJournal(((String) comboBoxJournals.getSelectedItem()).split("ISSN:")[1].trim());
            	ArrayList<Author> allAuthors = new ArrayList<Author>();
            	allAuthors.addAll(listCoauthorData);
            	allAuthors.add(author);
            	MainAuthorSelectionDialog dlg = new MainAuthorSelectionDialog(allAuthors);
            	dlg.addListeners(parentSubPanel);
            	dlg.setVisible(true);
            }
        });

        buttonAddCoauthor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	NewCoauthorDialog dlg = new NewCoauthorDialog(listModelCoauthors);
            	dlg.addListeners(parentSubPanel);
            	dlg.setVisible(true);
            	//Add each co author info to list
            }
        });
        buttonRemoveCoauthor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	int i = listCoauthors.getSelectedIndex();
            	if(i > -1 && i<listCoauthors.getModel().getSize()) {
            		listModelCoauthors.remove(i);
            		listCoauthorData.remove(i);
            	}
            }
        });
    }
}
