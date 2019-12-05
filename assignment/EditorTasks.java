package assignment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EditorTasks {
	
	public static List<Submission> getSubmissions() {
		List<Submission> subs = new ArrayList<Submission>();
		try(Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team043","team043","38796815")){
			PreparedStatement pstmt = con.prepareStatement("SELECT * FROM submission");
			ResultSet results = pstmt.executeQuery();
			while (results.next()) {
				Submission tmp = new Submission();
				tmp.setSubID(results.getInt(1));
				tmp.setName(results.getString(2));
				tmp.setAbstractPara(results.getString(3));
				tmp.setPdfLink(results.getString(4));
				tmp.setJournal(results.getString(5));
				subs.add(tmp);
			}
		} catch (SQLException ex) {ex.printStackTrace();}
		return subs;
	}
	
	public static List<String> getVerdicts(int subID) {
		List<String> verdicts = new ArrayList<String>();
		try(Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team043","team043","38796815")){
			PreparedStatement pstmt = con.prepareStatement("SELECT * FROM reviews WHERE SubID = ?");
			pstmt.setInt(1, subID);
			ResultSet results = pstmt.executeQuery();
			String verdict;
			while (results.next()) {
				verdict = results.getString(8);
				if (verdict != null) {
					verdicts.add(verdict);
				}
			}
		} catch (SQLException ex) {ex.printStackTrace();}
		return verdicts;
	}
	
	public static void appointEditor() {
		
	}
	/**
	 * Creation of new Journal
	 */
	public static void newJournal() {
		
	}
	
	/**
	 * Allows chef to make different appointed editor chef
	 */
	public static void makeEditorChef() {
		
	}
	
	public static void editorRetire() {
		
	}
	
	public static void publishArticle() {
		//Needs to check if current edition has space
		//?Create new edition?
	}
	
	public static void rejectArticle() {
		
	}
	
	public static void publishEdition() {
		
	}
}