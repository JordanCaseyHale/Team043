package assignment;
import java.sql.*;

public class sql {
	
/*	
		try(connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team0043","team043","38796815"){
			stmt = con.createStatement();
		}
		catch (SQLException ex){
			ex.printStackTrace();
		}
*/		
		
	public boolean login(String role, String email, String password) {
		try(Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team0043","team043","38796815")){
			Statement stmt = con.createStatement();
			ResultSet results = null;
			if(role == "editior"){
				results = stmt.executeQuery("SELECT password FROM account WHERE Email = email AND account.Email = journalEditors.Email AND account.Email = journal.ChiefEditor");
			}
			else if(role == "author"){
				results = stmt.executeQuery("SELECT password FROM account WHERE Email = email AND account.Email = submissionAuthors.Email AND account.Email = submission.ChiefAuthor");
			}
			else{
				results = stmt.executeQuery("SELECT password FROM account WHERE Email = email AND account.email = reviewers.Email");
			}
			
			try{
				if(results.getString(1) == password){
					return true;
				}
				else{
					return false;
				}
			}
			catch (Exception e){
				return false;
			}
		}
		catch (SQLException ex){
			ex.printStackTrace();
			return false;
		}
		
	}
	
	public String[] getChiefJournals(String email){
		try(Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team0043","team043","38796815")){
			Statement stmt = con.createStatement();
			ResultSet results = stmt.executeQuery("SELECT Title FROM journal WHERE ChiefEditor = email");
			String[] journals = null;
			int count = 0;
			while (results.next()){
				journals[count] = results.getString(1);
				count += 1;
			}
			return journals;
		}
		catch (SQLException ex){
			ex.printStackTrace();
			return null;
		}
	}
	
	//This creates an author account, I (Jordan) have redone this in AuthorTasks.java
	public boolean createChief(String email, String title, String forname, String surname, String affiliation, String passwrod){
		try(Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team0043","team043","38796815")){
			Statement stmt = con.createStatement();
			try{
				stmt.executeUpdate("INSERT INTO account VALUES email title forename surname affiliation password");
				return true;
			}
			catch (Exception e){
				return false;
			}
			
		}
		catch (SQLException ex){
			ex.printStackTrace();
			return false;
		}
	}
}