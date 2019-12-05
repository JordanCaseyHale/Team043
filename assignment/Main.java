package assignment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
			PreparedStatement pstmt = con.prepareStatement("SELECT password FROM account WHERE Email = ? AND userType = ?");
			ResultSet result = null;
			pstmt.setString(1, email);
			if(userType == "Editor") {
				pstmt.setString(2, userType);
				result = pstmt.executeQuery();
				if (result.first()) {
					pass = result.getString(1);
					System.out.println(pass);
				}
			}
			else if(userType == "Author") {
				pstmt.setString(2, userType);
				result = pstmt.executeQuery();
				if (result.first()) {
					pass = result.getString(1);
					System.out.println(pass);
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
	public static void changePassword(String password) {
		//Encrypt password probably
		//SQL statement to change the password
		try(Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team0043","team043","38796815")){
			PreparedStatement pstmt = con.prepareStatement("UPDATE account SET password = ? WHERE email = ?");
			pstmt.setString(1, PasswordHash.getHashedString(password));
			//add email to statement
			pstmt.executeUpdate();
		}
		catch (SQLException ex){
			ex.printStackTrace();
		}
	}
}