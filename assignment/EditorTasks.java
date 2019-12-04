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
			pstmt = con.prepareStatement("SELECT * FROM account WHERE Email = ? AND UserType = 'Author'");
			ResultSet mainResults;
			String mainAuthorEmail;
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