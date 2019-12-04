package assignment;

import java.sql.*;

public class ReviewerTasks {
	/**
	 * Gets the possible articles for a reviewer to choose from
	 */
	public static void getReviewsList(String author) {
		//only show review list if the reviewer needs to select more to review
		//otherwise should not have access to other submissions so dont show the list
		
		boolean show = false;
		try(Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team0043","team043","38796815")){
			Statement stmt = con.createStatement();
			String str = String.format("SELECT RemainingReviews FROM reviewers WHERE RevId = %d", "VARIABLE");
			ResultSet result = stmt.executeQuery(str);
			int left = result.getInt(1);
			
			str = String.format("SELECT COUNT FROM reviews WHERE RevID = %d", "VARIABLE");
			result = stmt.executeQuery(str);
			int inProgress = result.getInt(1); 
			if(left != inProgress) {
				show = true;
			}
		}
		catch (SQLException ex){
			ex.printStackTrace();
		}
		
		
		if(show) {
			//Get info of author
			//Get list of submissions where there are no conflicts
			try(Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team0043","team043","38796815")){
				Statement stmt = con.createStatement();
				String str = String.format("SELECT Affiliation FROM account WHERE submissionAuthor.Email = %s AND account.Email = submissionAuthor.Email", author);
				ResultSet results = stmt.executeQuery(str);
				String affil = results.getString(1);
				str = String.format("SELECT ?? FROM submission WHERE account.Affiliation != %s AND subbmisionAuthors.Email = account.Email",affil);
				results = stmt.executeQuery(str);
				while(results.next()) {
					//output list
				}
			}
			catch (SQLException ex){
				ex.printStackTrace();
			}
		}
		
	}
	
	/**
	 * Submits a review
	 */
	public static void submitReview() {
		String str = String.format("UPDATE reviews SET InitialReview = %s, InitialVerdict =  %s WHERE RevID = %d AND SubID = %s", "VARIABLES");
		boolean result = MySQLConnection.doUpdate(str);
		if(result) {
			//output success
		}
		else {
			//output error
		}
	}
	
	public static void getResponses() {
		try(Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team0043","team043","38796815")){
			Statement stmt = con.createStatement();
			String str = String.format("SELECT Response FROM reviews WHERE RevID = %d SubID = %d", "VARIABLES");
			ResultSet result = stmt.executeQuery(str);
			while(result.next()) {
				//output reponses
			}
		}
		catch (SQLException ex){
			ex.printStackTrace();
		}
		
	}
	
	public static void submitFinalVerdict() {
		//SQL submitting final verdict
		//If last review then removeReviewer
		String str = String.format("UPDATE reviews SET FinalVerdict = %s WHERE RevID = %d AND SubID = %s", "VARIABLES");
		boolean result = MySQLConnection.doUpdate(str);
		if(result) {
			//output success
			checkLast();
			
		}
		else {
			//output error
		}
	}
	
	public static void checkLast() {
		
		try(Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team0043","team043","38796815")){
			Statement stmt = con.createStatement();
			String str = String.format("SELECT RemainingReviews FROM reviewers WHERE RevID = ", "VARIABLE");
			ResultSet results = stmt.executeQuery(str);
			int left = results.getInt(1);
			if(left == 1) {
				removeReviewer();
			}
			else {
				left -= 1;
				str = String.format("UPDATE reviewers SET RemainingReviews = %d WHERE RevID = %d", left "VARIABLE");
				MySQLConnection.doUpdate(str);
			}
		}
		catch (SQLException ex){
			ex.printStackTrace();
		}
		
		
	}
	
	public static void removeReviewer() {
		String str = String.format("DELETE FROM reviewers WHERE RevID = %d", "VARIABLE");
		MySQLConnection.doUpdate(str);
		//Return to home page with messsage saying no more reviews left
	}
}