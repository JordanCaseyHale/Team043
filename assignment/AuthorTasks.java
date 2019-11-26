package assignment;
import java.sql.*;

public class AuthorTasks {
	
	/**
	 * Submits an article for consideration
	 */
	public static void submission() {
		//??? need all parts of submission as parameters ???
		
		//SQL statement adding the article to the database
		Connection con = MySQLConnection.openConnection();
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("INSERT INTO Submission VALUES ");
		} catch (SQLException e) {e.printStackTrace();}
		
	}
	
	public static void createAccount() {
		
	}
	
	/**
	 * Gets the status of the author's articles
	 */
	public static void getArticleStatus() {
		//SQL statement to get the status' of an author's articles
	}
	
	/**
	 * Gets the reviews related to a particular article
	 * 
	 * @param article
	 */
	public static void getArticleReviews(String article) {
		//SQL statement to get article reviews
	}
	
	/**
	 * Submits an author's response to a review
	 * 
	 * @param response
	 */
	public static void submitReviewResponse(String response) {
		//SQL statement to add response to database
	}
}