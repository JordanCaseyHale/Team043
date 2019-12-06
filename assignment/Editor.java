package assignment;

public class Editor {
	//Instance variables
	private String title;
	private String forename;
	private String surname;
	private String email;
	
	//Constructor
	public Editor(String title, String forename, String surname, String email) {
		this.title = title;
		this.forename = forename;
		this.surname = surname;
		this.email = email;
	}
	
	public Editor() {
		
	}
	
	//get methods
	public String getTitle() {
		return this.title;
	}
	public String getForename() {
		return this.forename;
	}
	public String getSurname() {
		return this.surname;
	}
	public String getEmail() {
		return this.email;
	}
	
	//Set methods
	public void setTitle(String title) {
		this.title = title;
	}
	public void setForename(String forename) {
		this.forename = forename;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}