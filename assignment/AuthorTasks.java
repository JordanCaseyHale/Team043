package assignment;
import java.sql.*;
import java.util.ArrayList;
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
		String str = String.format("INSERT INTO submission VALUES (%s,%s,%s,%s)",title,abstractPara,mainAuthor,link);
		result = MySQLConnection.doUpdate(str);
		try(Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team0043","team043","38796815")){
			PreparedStatement pstmt = con.prepareStatement("INSERT INTO submission VALUES (?, ?, ?, ?)");
			pstmt.setString(1, title);
			pstmt.setString(2, abstractPara);
			pstmt.setString(3, mainAuthor);
			pstmt.setString(4, link);
			pstmt.executeUpdate();
			result = true;
		}
		catch (SQLException ex){
			ex.printStackTrace();
		}
		return result;
	}
	
	public static boolean createAccount(String email, String title, String forename, String surname, String affiliation, String password, String userType) {
		boolean result = false;
		String str = String.format("INSERT INTO account VALUES (%s,%s,%s,%s,%s,%s,%s)",email,title,forename,surname,affiliation,password,userType);
		result = MySQLConnection.doUpdate(str);
		try(Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team0043","team043","38796815")){
			PreparedStatement pstmt = con.prepareStatement("INSERT INTO account VALUES (?, ?, ?, ?, ?, ?");
			pstmt.setString(1, email);
			pstmt.setString(2, title);
			pstmt.setString(3, forename);
			pstmt.setString(4, surname);
			pstmt.setString(5, affiliation);
			pstmt.setString(6, password);
		}
		catch (SQLException ex){
			ex.printStackTrace();
		}
		return result;
	}
	
	/**
	 * Gets the status of the author's articles
	 */
	public static String getArticleStatus(int articleID) {
		//SQL statement to get the status' of an author's articles
		try(Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team0043","team043","38796815")){
			PreparedStatement pstmt = con.prepareStatement("SELECT Status FROM article WHERE ArticleID = ?");
			pstmt.setInt(1, articleID);
			ResultSet results = pstmt.executeQuery();
			String status = results.getString(0);
			return status;
		}
		catch (SQLException ex){
			ex.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Gets a list of submission ids written by the author with the supplied email
	 */
	public static ArrayList<Integer> getSubIDs(String email) {
		try(Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team043","team043","38796815")){
			PreparedStatement pstmt = con.prepareStatement("SELECT SubID FROM submissionAuthors WHERE Email = ?");
			pstmt.setString(1, email);
			ResultSet result = pstmt.executeQuery();
			ArrayList<Integer> list = new ArrayList<Integer>();
			while (result.next()) {
				list.add(result.getInt(1));
			}
			return list;
		}
		catch(Exception e) {
			//
		}
		return null;
	}
	
	
	
	/**
	 * Gets the reviews related to a particular article
	 * 
	 * @param article
	 */
	public static List<List<String>> getSubmissionReviews(int subID) {
		//SQL statement to get article reviews
		List<String> temp = null;
		List<List<String>> subReviews = null;
		try(Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team043","team043","38796815")){
			PreparedStatement pstmt = con.prepareStatement("SELECT Initial Verdict, Response, Verdict FROM reviews WHERE SubID = ?");
			pstmt.setInt(1, subID);
			ResultSet results = pstmt.executeQuery();
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
		try(Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team0043","team043","38796815")){
			PreparedStatement pstmt = con.prepareStatement("UPDATE reviews SET response = ? WHERE SubID = ?, RevID = ?");
			pstmt.setString(1,response);
			pstmt.setInt(2, subID);
			pstmt.setInt(3,  revID);
			pstmt.executeUpdate();
		}
		catch (SQLException ex){
			ex.printStackTrace();
		}
	}
}