package assignment;
import java.sql.*;
import java.util.List;

public class AuthorTasks {
	
	/**
	 * Submits an article for consideration
	 */
	public static boolean submission(String title, String abstractPara, String mainAuthor, String link) {
		//??? need all parts of submission as parameters ???
		
		//SQL statement adding the article to the database
		/**
		Connection con = MySQLConnection.openConnection();
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("INSERT INTO Submission VALUES ");
		} catch (SQLException e) {e.printStackTrace();}
		*/
		boolean result = false;
		String str = String.format("INSERT INTO Submission VALUES (%s,%s,%s,%s)",title,abstractPara,mainAuthor,link);
		result = MySQLConnection.doUpdate(str);
		return result;
	}
	
	public static boolean createAccount(String email, String title, String forename, String surname, String affiliation, String password) {
		boolean result = false;
		String str = String.format("INSERT INTO Account VALUES (%s,%s,%s,%s,%s,%s)",email,title,forename,surname,affiliation,password);
		result = MySQLConnection.doUpdate(str);
		return result;
	}
	
	/**
	 * Gets the status of the author's articles
	 */
	public static String getArticleStatus(int articleID) {
		//SQL statement to get the status' of an author's articles
		boolean result = false;
		String str = String.format("SELECT Status FROM Article WHERE ArticleID = %2d",articleID);
		ResultSet results = MySQLConnection.doQuery(str);
		try {
			String status = results.getString(0);
			return status;
		} catch (SQLException e) {e.printStackTrace();}
		return null;
	}
	
	/**
	 * Gets the reviews related to a particular article
	 * 
	 * @param article
	 */
	public static List<List<String>> getSubmissionReviews(int subID) {
		//SQL statement to get article reviews
		String str = String.format("SELECT Initial Verdict, Response, Verdict FROM Reviews WHERE Sub ID = %2d",subID);
		ResultSet results = MySQLConnection.doQuery(str);
		List<String> temp = null;
		List<List<String>> subReviews = null;
		try {
			while (results.next()) {
				temp.add(results.getString(1));
				temp.add(results.getString(2));
				temp.add(results.getString(3));
				subReviews.add(temp);
			}
			return subReviews;
		} catch (SQLException ex) {ex.printStackTrace();}
		return null;
	}
	
	/**
	 * Submits an author's response to a review
	 * 
	 * @param response
	 */
	public static void submitReviewResponse(String response, int subID, int revID) {
		//SQL statement to add response to database
		String str = String.format("INSERT Response INTO Reviews WHERE SubID = %2d, RevID = %2d", subID, revID);
		MySQLConnection.doUpdate(str);
	}
}