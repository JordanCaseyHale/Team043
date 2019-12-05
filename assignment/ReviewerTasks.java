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
			PreparedStatement pstmt = con.prepareStatement("SELECT RemainingReviews FROM reviewers WHERE RevId = ?");
			ResultSet result = pstmt.executeQuery();
			int left = result.getInt(1);
			
			pstmt = con.prepareStatement("SELECT COUNT FROM reviews WHERE RevID = ?");
			result = pstmt.executeQuery();
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
				PreparedStatement pstmt = con.prepareStatement("SELECT Affiliation FROM account WHERE submissionAuthor.Email = ? AND account.Email = submissionAuthor.Email");
				ResultSet results = pstmt.executeQuery();
				String affil = results.getString(1);
				//the ?? means idk what data to return and display
				pstmt = con.prepareStatement("SELECT ?? FROM submission WHERE account.Affiliation != ? AND subbmisionAuthors.Email = account.Email");
				pstmt.setString(1, affil);
				results = pstmt.executeQuery();
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
		try(Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team0043","team043","38796815")){
			PreparedStatement pstmt = con.prepareStatement("UPDATE reviews SET InitialReview = ?, InitialVerdict =  ? WHERE RevID = ? AND SubID = ?");
			pstmt.executeUpdate();
		}
		catch (SQLException ex){
			ex.printStackTrace();
		}
	}
	
	public static void getResponses() {
		try(Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team0043","team043","38796815")){
			PreparedStatement pstmt = con.prepareStatement("SELECT Response FROM reviews WHERE RevID = ? SubID = ?");
			ResultSet result = pstmt.executeQuery();
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
		try(Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team0043","team043","38796815")){
			PreparedStatement pstmt = con.prepareStatement("UPDATE reviews SET FinalVerdict = ? WHERE RevID = ? AND SubID =  ?");
			pstmt.executeUpdate();
			checkLast();
		}
		catch (SQLException ex){
			ex.printStackTrace();
		}
	}
	
	public static void checkLast() {
		
		try(Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team0043","team043","38796815")){
			PreparedStatement pstmt = con.prepareStatement("SELECT RemainingReviews FROM reviewers WHERE RevID = ?");
			ResultSet results = pstmt.executeQuery();
			int left = results.getInt(1);
			if(left == 1) {
				removeReviewer();
			}
			else {
				left -= 1;
				PreparedStatement psmt = con.prepareStatement("UPDATE reviewers SET RemainingReviews = ? WHERE RevID = ?");
				psmt.executeUpdate();
			}
		}
		catch (SQLException ex){
			ex.printStackTrace();
		}
		
		
	}
	
	public static void removeReviewer() {
		try(Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team0043","team043","38796815")){
			PreparedStatement pstmt = con.prepareStatement("DELETE FROM reviewers WHERE RevID = ?");
			pstmt.executeUpdate();
			checkLast();
		}
		catch (SQLException ex){
			ex.printStackTrace();
		}
		//Return to home page with messsage saying no more reviews left
	}
}