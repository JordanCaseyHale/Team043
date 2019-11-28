package assignment;

import java.sql.ResultSet;

public class Main {
	
	public static void main(String[] args) {
		//Start Window
		
	}
	
	/**
	 * Checks the login details against the database
	 * 
	 * @param email
	 * @param password
	 * @param userType
	 */
	public static void login(String email, String password, String userType) { //Called when login attempted
		
		//Get parameters from window

		//Encrypt password probably
		
		//SQL statement to check password
		
		//If false call window change to inform user
		
		//If true call go to relevant type of user home page
		
		//Possibly make account object
		String pass = "";
		ResultSet result = null;
		try {
			if(userType == "Editor") {
				result = MySQLConnection.doQuery("SELECT password FROM account WHERE Email = email AND account.Email = journalEditors.Email AND account.Email = journal.ChiefEditor");
			}
			else if(userType == "Author") {
				result = MySQLConnection.doQuery("SELECT password FROM account WHERE Email = email AND account.Email = submissionAuthors.Email AND account.Email = submission.ChiefAuthor");
			}
			else {
				result = MySQLConnection.doQuery("SELECT password FROM account WHERE Email = email AND account.email = reviewers.Email");
			}
			pass = result.getString(1);
			if(pass == password) {
				//take to correct page
			}
			else {
				//output message password incorrect
			}
		}
		catch(Exception e) {
			//account doesnt exist for that role/at all, check email
		}
		
	}
	
	/**
	 * Changes the password for the account
	 * @param password
	 */
	public static void changePassword(String password) {
		//Encrypt password probably
		//SQL statement to change the password
	}
}