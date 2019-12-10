package assignment;

public class Author {
	
	//Instance Variables
	private String title;
	private String forename;
	private String surname;
	private String email;
	private String affiliation;
	private String pwhash;
	
	//Constructor
	public Author(String title, String forename, String surname, String email, String affiliation, String pwhash) {
		this.title = title;
		this.forename = forename;
		this.surname = surname;
		this.email = email;
		this.affiliation = affiliation;
		this.pwhash = pwhash;
	}
	
	//Default Constructor
	public Author() {
		
	}
	
	//Get methods
	public boolean isNotComplete() {
		return (title.isEmpty() || forename.isEmpty() || surname.isEmpty() || email.isEmpty() || affiliation.isEmpty() || pwhash.isEmpty());
	}
	
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
	
	public String getAffiliation() {
		return this.affiliation;
	}
	
	public String getPasswordhashed() {
		return this.pwhash;
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
	
	public void setAffiliation(String affiliation) {
		this.affiliation = affiliation;
	}
	
	public void setPasswordHashed(String pwhash) {
		this.pwhash = pwhash;
	}
}