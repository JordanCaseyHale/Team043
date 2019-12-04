package assignment;

public class Journal {
	//Instance variables
	private String issn;
	private String title;
	private String chiefEditor;
	
	public Journal(String issn, String title, String chiefEditor) {
		this.issn = issn;
		this.title = title;
		this.chiefEditor = chiefEditor;
	}
	
	public Journal() {
		// TODO Auto-generated constructor stub
	}

	//Get methods
	public String getISSN() {
		return this.issn;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public String getChiefEditor() {
		return this.chiefEditor;
	}
	
	//Set methods
	public void setISSN(String issn) {
		this.issn = issn;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setChiefEditor(String chiefEditor) {
		this.chiefEditor = chiefEditor;
	}
}