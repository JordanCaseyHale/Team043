package assignment;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.*;

public class RetireDialog extends JDialog {
	protected JLabel labelDelete = new JLabel("To delete account there must be no journal where the editor is chief");
	
	protected JComboBox<String> comboBoxJournals = new JComboBox<String>();
	protected JComboBox<String> comboBoxEditors = new JComboBox<String>();
	
	protected JButton buttonDelete = new JButton("Delete");
	protected JButton buttonMakeChief = new JButton("Make Editor Chief");
	
    protected DefaultComboBoxModel<String> journalComboBoxModel = new DefaultComboBoxModel<String>();	
    protected DefaultComboBoxModel<String> editorsComboBoxModel = new DefaultComboBoxModel<String>();	
	
	private String email;
	private EditorTasksPanel parent;
	private List<Journal> journals;
	
	public RetireDialog(String email, EditorTasksPanel parent) {
		this.email = email;
		this.parent = parent;
		
		JPanel panel = new JPanel(new GridBagLayout());
		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension (500,600));
		GridBagConstraints constraints = new GridBagConstraints();
		
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(10, 10, 10, 10);
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.weightx = 0.3;
		constraints.gridwidth = 1;
		constraints.gridx = 0;
		constraints.gridy = 0;
		journals = EditorTasks.getChiefJournals(this.email);
		for (Journal j : journals) {
			journalComboBoxModel.addElement("ISSN: "+j.getISSN()+", "+j.getTitle());
		}
		comboBoxJournals.setModel(journalComboBoxModel);
		if (journalComboBoxModel.getSize() == 0) {
			buttonDelete.setEnabled(true);
		}
		else {
			buttonDelete.setEnabled(false);
		}
		panel.add(comboBoxJournals, constraints);
		constraints.gridx = 1;
		System.out.println(journals.size());
		if (journals.size() > 0) {
			System.out.println("Editors:");
			List<Editor> editors = EditorTasks.getEditors(journals.get(0).getISSN(), this.email);
			System.out.println(editors);
			for (Editor e : editors) {
				editorsComboBoxModel.addElement(e.getTitle()+", "+e.getForename()+" "+e.getSurname()+" Email: "+e.getEmail());
			}
			comboBoxEditors.setModel(editorsComboBoxModel);
		}
		panel.add(comboBoxEditors, constraints);
		constraints.gridy = 1;
		panel.add(buttonMakeChief, constraints);
		constraints.gridx = 0;
		panel.add(buttonDelete, constraints);
		constraints.gridy = 2;
		panel.add(labelDelete, constraints);
		
		getContentPane().add(panel, BorderLayout.CENTER);
        pack();
        setResizable(false);
		
	}
	
	public void AddListeners() {
		buttonMakeChief.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String editorSelected = (String) comboBoxEditors.getSelectedItem();
				String journalSelected = ((String) comboBoxJournals.getSelectedItem()).split(", ")[0].replaceAll("ISSN: ", "");
				if (editorSelected != null) {
					String emailSelected = editorSelected.split("Email: ")[1].trim();
					EditorTasks.makeEditorChief(emailSelected, journalSelected);
					journals = EditorTasks.getChiefJournals(email);
					for (Journal j : journals) {
						journalComboBoxModel.addElement("ISSN: "+j.getISSN()+", "+j.getTitle());
					}
					comboBoxJournals.setModel(journalComboBoxModel);
					if (journalComboBoxModel.getSize() == 0) {
						buttonDelete.setEnabled(true);
					}
					else {
						buttonDelete.setEnabled(false);
					}
					String journalISSN = ((String) comboBoxJournals.getSelectedItem()).split(", ")[0].replaceAll("ISSN: ", "").trim();
					List<Editor> editors = EditorTasks.getEditors(journalISSN, email);
					for (Editor ed : editors) {
						editorsComboBoxModel.addElement(ed.getTitle()+", "+ed.getForename()+" "+ed.getSurname()+", Email: "+ed.getEmail());
					}
					comboBoxEditors.setModel(editorsComboBoxModel);
				}
				dispose();
			}
		});
		
		buttonDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EditorTasks.deleteAccount(email);
				parent.logout();
				dispose();
			}
		});
		
		comboBoxJournals.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String journalSelected = (String)comboBoxJournals.getSelectedItem();
				String issnSel = journalSelected.split(", ")[0].replaceAll("ISSN: ", "").trim();
				List<Editor> editors = EditorTasks.getEditors(issnSel, email);
				for (Editor ed : editors) {
					editorsComboBoxModel.addElement(ed.getTitle()+", "+ed.getForename()+" "+ed.getSurname()+" - "+ed.getEmail());
				}
				comboBoxEditors.setModel(editorsComboBoxModel);
			}
		});
	}
}