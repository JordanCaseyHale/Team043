package assignment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

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
	public static boolean login(String email, String password, String userType) { //Called when login attempted
		
		//Get parameters from window

		//Encrypt password probably
		
		//SQL statement to check password
		
		//If false call window change to inform user
		
		//If true call go to relevant type of user home page
		
		//Possibly make account object
		String pass = "";
		try(Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team043","team043","38796815")){
			Statement stmt = con.createStatement();
			ResultSet result = null;
			String str;
			if(userType == "Editor") {
				str = String.format("SELECT * FROM journalEditors WHERE Email = %s", email);
				result = stmt.executeQuery(str);
				if (result.first()) {
					str = String.format("SELECT password FROM account WHERE Email = %s", email);
					result = stmt.executeQuery(str);
				}
			}
			else if(userType == "Author") {
				str = String.format("SELECT * FROM submissionAuthors WHERE Email = %s",email);
				result = stmt.executeQuery(str);
				if (result.first()) {
					str = String.format("SELECT password FROM account WHERE Email = %s", email);
					result = stmt.executeQuery(str);
				}
			}
			pass = result.getString(1);
			if(pass == password) {
				//take to correct page
				return true;
			}
			else {
				//output message password incorrect
				return false;
			}
		}
		catch(Exception e) {
			//account doesnt exist for that role/at all, check email
		}
		return false;
		
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