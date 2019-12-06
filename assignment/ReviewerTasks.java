package assignment;

import java.sql.*;
import java.util.ArrayList;

public class ReviewerTasks {
	
	public static void main(String[] args) {
		for(Submission sub:ReviewerTasks.getReviewsList("Test3@aol.com")) {
			System.out.println(sub.getName());
		}
	}
	/**
	 * Switches between RevID and email
	 */
	public static int getReviewerID(String author) {
		try(Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team043","team043","38796815")){
			PreparedStatement pstmt = con.prepareStatement("SELECT RevID FROM reviewers WHERE Email = ?");
			pstmt.setString(1, author);
			ResultSet results = pstmt.executeQuery();
			if(results.first()) {
				return results.getInt(1);
			}
		}
		catch (SQLException ex){
			ex.printStackTrace();
		}
		return 0;
	}
	
	public static int ReviewsRemaining(String email) {
		int left = 0;
		try(Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team043","team043","38796815")){
			PreparedStatement pstmt = con.prepareStatement("SELECT RemainingReviews FROM reviewers WHERE Email = ?");
			pstmt.setString(1, email);
			ResultSet result = pstmt.executeQuery();
			if(result.first()) {
				left= result.getInt(1);
			}
			return left;
		}
		catch (SQLException ex){
			ex.printStackTrace();
		}
		return left;
	}
	
	/**
	 * Gets the possible submissions for a potential reviewer to choose from
	 */
	public static ArrayList<Submission> getReviewableList(String author) {
		//only show review list if the reviewer needs to select more to review
		//otherwise should not have access to other submissions so dont show the list
		if(ReviewsRemaining(author)>0) {
			//Get info of author
			//Get list of submissions where there are no conflicts
			try(Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team043","team043","38796815")){
				PreparedStatement getAffilpstmt = con.prepareStatement("SELECT Affiliation FROM account WHERE Email = ?");
				PreparedStatement getSubIDBlacklistpstmt = con.prepareStatement("SELECT submissionAuthors.SubID FROM submissionAuthors INNER JOIN account USING(Email) WHERE account.Affiliation = ?");
				PreparedStatement selfReviewpstmt = con.prepareStatement("SELECT SubID FROM reviews WHERE RevID = ?");
				
				ArrayList<Integer> blackListedIDs = new ArrayList<Integer>(); 
				ArrayList<Integer> resultIDs = new ArrayList<Integer>(); 
				ArrayList<Submission> resultSubmissions = new ArrayList<Submission>(); 
				getAffilpstmt.setString(1, author);
				selfReviewpstmt.setInt(1, ReviewerTasks.getReviewerID(author));
				
				ResultSet getAffilresults = getAffilpstmt.executeQuery();
				if(getAffilresults.first()) {
					getSubIDBlacklistpstmt.setString(1, getAffilresults.getString(1));
					ResultSet subIDBlacklistresults = getSubIDBlacklistpstmt.executeQuery();
					ResultSet selfReviewresults = selfReviewpstmt.executeQuery();
					while(subIDBlacklistresults.next()) {
						blackListedIDs.add(subIDBlacklistresults.getInt(1));
					}
					while(selfReviewresults.next()) {
						blackListedIDs.add(selfReviewresults.getInt(1));
					}
					//gets list of SubIDs that have conflicting interests first
				}
				PreparedStatement pstmt = con.prepareStatement("SELECT SubID FROM submission");
				ResultSet results = pstmt.executeQuery();
				while(results.next()) {
					int i = results.getInt(1);
					if (!blackListedIDs.contains(i)) {
						resultIDs.add(i);
					}
				}
				for(int ID:resultIDs) {
					Submission s = Submission.getSubmissionByID(ID);
					if(Submission.ReviewsLeft(s)>0) {
						resultSubmissions.add(s);
					}
				}
				return resultSubmissions;
			}
			catch (SQLException ex){
				ex.printStackTrace();
			}
		}
		return new ArrayList<Submission>();
	}
	
	/**
	 * Gets the submissions already being reviewed by given reviewer
	 */
	
	public static ArrayList<Submission> getReviewsList(String author) {
		ArrayList<Submission> resultSubmissions = new ArrayList<Submission>();
		try(Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team043","team043","38796815")){
			PreparedStatement pstmt = con.prepareStatement("SELECT reviews.SubID FROM reviews INNER JOIN reviewers USING(RevID) WHERE reviewers.Email = ?");
			pstmt.setString(1, author);
			
			ResultSet results = pstmt.executeQuery();
			while(results.next()) {
				System.out.println("start");
				int i = results.getInt(1);
				Submission s = Submission.getSubmissionByID(i);
				resultSubmissions.add(s);
			}
		}
		catch (SQLException ex){
			ex.printStackTrace();
		}
		return resultSubmissions;
	}
	/**Primes submission to be reviewed by a certain reviewer.
	 */
	public static void submitChosenSubmission(int RevID, int subID) {
		try(Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team043","team043","38796815")){
			PreparedStatement pstmt = con.prepareStatement("INSERT INTO reviews (RevID, SubID) VALUES (?, ?)");
			pstmt.setInt(1, RevID);
			pstmt.setInt(2, subID);
			pstmt.executeUpdate();			
			incrementReviews(RevID);
		}
		catch (SQLException ex){
			ex.printStackTrace();
		}
	}
	
	/**
	 * Submits a review
	 */
	public static void submitReview(Submission s,int RevID, String initVer,String summary, String typos, String criticisms) {
		try(Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team043","team043","38796815")){
			PreparedStatement pstmt = con.prepareStatement("UPDATE reviews SET Initial_verdict =  ?,Summary = ?,Typos = ?, Criticisms = ? WHERE (SubID,RevID) = (?,?)");
			pstmt.setString(1, initVer);
			pstmt.setString(2, summary);
			pstmt.setString(3, typos);
			pstmt.setString(4, criticisms);
			pstmt.setInt(5, s.getSubID());
			pstmt.setInt(6, RevID);
			pstmt.executeUpdate();
		}
		catch (SQLException ex){
			ex.printStackTrace();
		}
	}

	public static void submitFinalVerdict(String verdict,int SubID,int RevID) {
		//SQL submitting final verdict
		//If last review then removeReviewer
		try(Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team043","team043","38796815")){
			PreparedStatement pstmt = con.prepareStatement("UPDATE reviews SET FinalVerdict = ? WHERE (SubID,RevID) =  (?,?)");
			pstmt.setString(1, verdict);
			pstmt.setInt(2, SubID);
			pstmt.setInt(3, RevID);
			pstmt.executeUpdate();
			ReviewerTasks.checkLast(RevID);
			//checkLast();
		}
		catch (SQLException ex){
			ex.printStackTrace();
		}
	}
	
	public static boolean canPutFinalVerdict(int revID,int subID) {
		try(Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team043","team043","38796815")){
			PreparedStatement pstmt = con.prepareStatement("SELECT Response,Verdict FROM reviews WHERE (SubID,RevID) =  (?,?)");
			pstmt.setInt(1, subID);
			pstmt.setInt(2, revID);
			ResultSet results = pstmt.executeQuery();
			if (results.first()) {
				return results.getString(1) != null && results.getString(2) == null;
			}
		}
		catch (SQLException ex){
			ex.printStackTrace();
		}
		return false;
	}
	
	public static boolean reviewExists(int revID,int subID) {
		try(Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team043","team043","38796815")){
			PreparedStatement pstmt = con.prepareStatement("SELECT Initial_verdict FROM reviews WHERE (SubID,RevID) =  (?,?)");
			pstmt.setInt(1, subID);
			pstmt.setInt(2, revID);
			ResultSet results = pstmt.executeQuery();
			
			if (results.first()) {
				return results.getString(1) != null;
			}
		}
		catch (SQLException ex){
			ex.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Checks if all reviews by given revID are in the final stage (final verdicts commited)
	 * @param revID
	 */
	public static void checkLast(int revID) {
		try(Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team043","team043","38796815")){
			PreparedStatement pstmt = con.prepareStatement("SELECT Verdict FROM reviews WHERE RevID = ?");
			pstmt.setInt(1, revID);
			ResultSet results = pstmt.executeQuery();
			boolean stillLeft = false;
			while (results.next()) {
				if (results.getString(1) == null) {
					stillLeft = true;
				}
			}
			if(!stillLeft) {
				removeReviewer(revID);
			}
		}
		catch (SQLException ex){
			ex.printStackTrace();
		}
	}
	
	/**
	 * Subtracts one from RemainingReviews and accordingly changes values around
	 * @param revID
	 */
	public static void incrementReviews(int revID) {
		try(Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team043","team043","38796815")){
			PreparedStatement pstmt = con.prepareStatement("SELECT RemainingReviews FROM reviewers WHERE RevID = ?");
			pstmt.setInt(1, revID);
			ResultSet results = pstmt.executeQuery();
			int left = 0;
			if (results.first()) {
				left = results.getInt(1);
			}
			if(left == 0) {
				
			}
			else {
				left -= 1;
				PreparedStatement psmt = con.prepareStatement("UPDATE reviewers SET RemainingReviews = ? WHERE RevID = ?");
				psmt.setInt(1, left);
				psmt.setInt(2, revID);
				psmt.executeUpdate();
			}
		}
		catch (SQLException ex){
			ex.printStackTrace();
		}
	}
	
	public static void removeReviewer(int RevID) {
		try(Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team043","team043","38796815")){
			PreparedStatement pstmt = con.prepareStatement("DELETE FROM reviewers WHERE RevID = ?");
			pstmt.setInt(1, RevID);
			pstmt.executeUpdate();
			//checkLast();
		}
		catch (SQLException ex){
			ex.printStackTrace();
		}
		//Return to home page with messsage saying no more reviews left
	}
}