package assignment;

import java.sql.ResultSet;

public class ReviewerTasks {
	/**
	 * Gets the possible articles for a reviewer to choose from
	 */
	public static void getReviewsList(String author) {
		
		//Get info of author
		//Get list of submissions where there are no conflicts
		ResultSet results = MySQLConnection.doQuery("SELECT Affiliation FROM account WHERE submissionAuthor.Email = " + VARIABLE + " AND account.Email = submissionAuthor.Email");
		String affil = results.getString(1);
		results = MySQLConnection.doQuery("SELECT ?? FROM submission WHERE account.affiliation != affil AND subbmisionAuthors.Email = account.Email");
		//PROCESS RESULTS
		
	}
	
	/**
	 * Submits a review
	 */
	public static void submitReview() {
		boolean success = MySQLConnection.doUpdate("UPDATE reviews SET InitialVerdict = " + VARIABLE + " WHERE SubID = " + VARIABLE + " AND RevID = " + VARAIBLE);
		if(success) {
			//output success
		}
		else {
			//output error
		}
	}
	
	public static void submitIntialVerdict(String verdict) {
		boolean success = MySQLConnection.doUpdate("UPDATE reviews SET InitialReview = " + VARIABLE + " WHERE SubID = " + VARIABLE + " AND RevID = " + VARAIBLE);
		if(success) {
			//output success
		}
		else {
			//output error
		}
	}
	
	public static void getResponses() {
		ResultSet result = doQuery("SELECT Response FROM reviews WHERE RevID = " + VARAIBLE + " SubID = " + VARAIBLE);
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
		boolean success = MySQLConnection.doUpdate("UPDATE reviews SET InitialReview = " + VARIABLE + " WHERE SubID = " + VARIABLE + " AND RevID = " + VARAIBLE);
		if(success) {
			//output success
			checkLast();
			
		}
		else {
			//output error
		}
	}
	
	public static void checkLast() {
		ResultSet results = MySQLConnection.doQuery("SELECT SubID FROM reviews WHERE RevID = " + Variable);
		if(results == null) {
			removeReviewer();
		}
		
	}
	
	public static void removeReviewer() {
		boolean author = MySQLConnection.doQuery("DELETE FROM reviewers WHERE RevID = " + variable);
	}
}