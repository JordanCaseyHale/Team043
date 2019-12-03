package assignment;

import java.sql.ResultSet;

public class ReviewerTasks {
	/**
	 * Gets the possible articles for a reviewer to choose from
	 */
	public static void getReviewsList(String author) {
		
		//Get info of author
		//Get list of submissions where there are no conflicts
		try {
			String str = String.format("SELECT Affiliation FROM account WHERE submissionAuthor.Email = %s AND account.Email = submissionAuthor.Email", author);
			ResultSet results = MySQLConnection.doQuery(str);
			String affil = results.getString(1);
			str = String.format("SELECT ?? FROM submission WHERE account.Affiliation != %s AND subbmisionAuthors.Email = account.Email",affil);
			results = MySQLConnection.doQuery(str);
		}
		catch(Exception e) {
			//output no results message
		}
		
		//PROCESS RESULTS
		
	}
	
	/**
	 * Submits a review
	 */
	public static void submitReview() {
		String str = String.format("UPDATE reviews SET InitialReview = %s, InitialVerdict =  %s WHERE RevID = %d AND SubID = %s", "VARIABLES");
		boolean success = MySQLConnection.doUpdate(str);
		if(success) {
			//output success
		}
		else {
			//output error
		}
	}
	
	public static void getResponses() {
		String str = String.format("SELECT Response FROM reviews WHERE RevID = %d SubID = %d", "VARIABLES");
		ResultSet result = MySQLConnection.doQuery(str);
		if(result != null) {
			//OUTPUT REVIEWS
		}
		else {
			//OUTPUT ERROR MESSAGE
		}
	}
	
	public static void submitFinalVerdict() {
		//SQL submitting final verdict
		//If last review then removeReviewer
		String str = String.format("UPDATE reviews SET FinalVerdict = %s WHERE RevID = %d AND SubID = %s", "VARIABLES");
		boolean success = MySQLConnection.doUpdate(str);
		if(success) {
			//output success
			checkLast();
			
		}
		else {
			//output error
		}
	}
	
	public static void checkLast() {
		String str = String.format("SELECT SubID FROM reviews WHERE RevID = %d", "VARIABLE");
		ResultSet results = MySQLConnection.doQuery(str);
		if(results == null) {
			removeReviewer();
		}
		
	}
	
	public static void removeReviewer() {
		String str = String.format("DELETE FROM reviewers WHERE RevID = %d", "VARIABLE");
		MySQLConnection.doUpdate(str);
		//Return to home page with messsage saying no more reviews left
	}
}