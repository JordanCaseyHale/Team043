package assignment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
	

	
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
		Boolean accountExists = false;
		try(Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team043","team043","38796815")){
			PreparedStatement pstmt = null;
			ResultSet result = null;
			if(userType == "Editor") {
				pstmt = con.prepareStatement("SELECT journalEditors.ISSN from journalEditors INNER JOIN account ON account.Email = journalEditors.Email WHERE account.Email = ?");
				pstmt.setString(1, email);
				result = pstmt.executeQuery();
				if (result.first()) {
					accountExists = true;
				}
			}
			else if(userType == "Author") {
				pstmt = con.prepareStatement("SELECT submissionAuthors.SubID from submissionAuthors INNER JOIN account ON account.Email = submissionAuthors.Email WHERE account.Email = ?");
				pstmt.setString(1, email);
				result = pstmt.executeQuery();
				if (result.first()) {
					accountExists = true;
				}
			}
			if (accountExists) {
				System.out.println("account exists");
				pstmt = con.prepareStatement("SELECT password FROM account WHERE Email = ?");
				pstmt.setString(1, email);
				ResultSet rs = pstmt.executeQuery();
				if (rs.first()) {
					pass = rs.getString(1);
				}
			}
			if(pass.equals(password)) {
				//take to correct page
				System.out.println("Should succ");
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
	public static void changePassword(String password, String email) {
		//Encrypt password probably
		//SQL statement to change the password
		try(Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team043","team043","38796815")){
			PreparedStatement pstmt = con.prepareStatement("UPDATE account SET password = ? WHERE email = ?");
			pstmt.setString(1, PasswordHash.getHashedString(password));
			pstmt.setString(2, email);
			//add email to statement
			pstmt.executeUpdate();
		}
		catch (SQLException ex){
			ex.printStackTrace();
		}
	}
}