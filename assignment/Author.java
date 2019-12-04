package assignment;

public class Author {
	
	//Instance Variables
	private int authorID;
	private String title;
	private String forename;
	private String surname;
	private String email;
	
	//Constructor
	public Author(int authorID, String title, String forename, String surname, String email) {
		this.authorID = authorID;
		this.title = title;
		this.forename = forename;
		this.surname = surname;
		this.email = email;
	}
	
	//Default Constructor
	public Author() {
		
	}
	
	//Get methods
	public int getAuthorID() {
		return this.authorID;
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
	
	//Set methods
	public void setAuthorID(int authorID) {
		this.authorID = authorID;
	}
	
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