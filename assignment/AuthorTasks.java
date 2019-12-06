package assignment;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuthorTasks {
	
	/**
	 * Submits an article for consideration
	 * returns submission ID
	 */
	public static int submissionToDB(Submission s) {
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
		int generatedSubID = 0;
		try(Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team043","team043","38796815")){
			PreparedStatement pstmt = con.prepareStatement("INSERT INTO submission (Title,Abstract,Link,ISSN,MainAuthor) VALUES (?, ?, ?, ?, ?)",Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, s.getName());
			pstmt.setString(2, s.getAbstractPara());
			pstmt.setString(3, s.getPdfLink());
			pstmt.setString(4, s.getJournal());
			pstmt.setString(5, s.getRespondEmail());
			pstmt.executeUpdate();
			ResultSet genIDResult = pstmt.getGeneratedKeys();
			if (genIDResult.first()) {
				generatedSubID = genIDResult.getInt(1);
			}
			
			result = true;
		}
		catch (SQLException ex){
			ex.printStackTrace();
		}
		return generatedSubID;
	}
	/**
	 * updates submissionAuthor with given author email and submission ID
	 */
	public static boolean addSubAuthor(String authorEmail,int SubID) {
		boolean result = false;
		try(Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team043","team043","38796815")){
			PreparedStatement pstmt = con.prepareStatement("INSERT INTO submissionAuthors VALUES (?, ?)");
			pstmt.setString(1, authorEmail);
			pstmt.setInt(2, SubID);
			pstmt.executeUpdate();
			result = true;
		}
		catch (SQLException ex){
			ex.printStackTrace();
		}
		return result;
	}
	
	/**
	 * Adds given email and reviews allocated to reviewers table
	 */
	public static boolean addReviewerPrivileges(String authorEmail,int reviews) {
		boolean result = false;
		if (reviews <=0) {
			return false;
		}
		try(Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team043","team043","38796815")){
			PreparedStatement pstmt = con.prepareStatement("INSERT INTO reviewers (Email,RemainingReviews) VALUES (?, ?)");
			pstmt.setString(1, authorEmail);
			pstmt.setInt(2, reviews);
			pstmt.executeUpdate();
			result = true;
		}
		catch (SQLException ex){
			ex.printStackTrace();
		}
		return result;
	}
	
	/**
	 * Creates an account
	 */
	public static boolean createAccount(String email, String title, String forename, String surname, String affiliation, String password) {
		boolean result = false;
		try(Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team043","team043","38796815")){
			PreparedStatement pstmt = con.prepareStatement("INSERT INTO account VALUES (?, ?, ?, ?, ?, ?)");
			pstmt.setString(1, email);
			pstmt.setString(2, title);
			pstmt.setString(3, forename);
			pstmt.setString(4, surname);
			pstmt.setString(5, affiliation);
			pstmt.setString(6, password);
			pstmt.executeUpdate();
			result = true;
		}
		catch (SQLException ex){
			ex.printStackTrace();
		}
		return result;
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
		return new ArrayList<Integer>();
	}
	
	
	
	/**
	 * Gets the data of a certain review found with subID and revID
	 *
	 */
	public static ArrayList<String> getSubmissionReviews(int subID, int revID) {
		//SQL statement to get article reviews
		ArrayList<String> temp = new ArrayList<String>();
		try(Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team043","team043","38796815")){
			PreparedStatement pstmt = con.prepareStatement("SELECT Summary,Typos,Criticisms,Response FROM reviews WHERE (SubID,RevID) = (?,?)");
			pstmt.setInt(1, subID);
			pstmt.setInt(2, revID);
			ResultSet results = pstmt.executeQuery();
			if (results.first()) {
				temp.add(results.getString(1));
				temp.add(results.getString(2));
				temp.add(results.getString(3));
				temp.add(results.getString(4));
				return temp;
			}
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
		try(Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team043","team043","38796815")){
			PreparedStatement pstmt = con.prepareStatement("UPDATE reviews SET Response = ? WHERE (SubID,RevID) = (?,?)");
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