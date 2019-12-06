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
	
	public static List<Edition> getUnpublishedEditions() {
		List<Edition> eds = new ArrayList<Edition>();
		try(Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team043","team043","38796815")){
			PreparedStatement pstmt = con.prepareStatement("SELECT * FROM edition WHERE Published = 0");
			ResultSet results = pstmt.executeQuery();
			while (results.next()) {
				Edition ed = new Edition();
				ed.setISSN(results.getString(1));
				ed.setVolume(results.getInt(2));
				ed.setEdition(results.getInt(3));
				eds.add(ed);
			}
		} catch (SQLException ex) {ex.printStackTrace();}
		return eds;
	}
	
	public static List<Journal> getChiefJournals(String email) {
		List<Journal> journals = new ArrayList<Journal>();
		try(Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team043","team043","38796815")){
			PreparedStatement pstmt = con.prepareStatement("SELECT ISSN, Title FROM journal WHERE ChiefEditor = ?");
			pstmt.setString(1, email);
			ResultSet results = pstmt.executeQuery();
			while (results.next()) {
				Journal tmp = new Journal();
				tmp.setISSN(results.getString(1));
				tmp.setTitle(results.getString(2));
				journals.add(tmp);
			}
		} catch (SQLException ex) {ex.printStackTrace();}
		return journals;
	}
	
	public static List<Editor> getEditors(String issn, String email) {
		List<Editor> editors = new ArrayList<Editor>();
		try(Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team043","team043","38796815")){
			PreparedStatement pstmt = con.prepareStatement("SELECT Email FROM journalEditors WHERE ISSN = ? AND Email != ?");
			pstmt.setString(1, issn);
			pstmt.setString(2, email);
			ResultSet results = pstmt.executeQuery();
			while (results.next()) {
				
			}
		} catch (SQLException ex) {ex.printStackTrace();}
		return editors;
	}
	
	public static void appointEditor(String email, String issn) {
		try(Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team043","team043","38796815")){
	
			PreparedStatement pstmt = con.prepareStatement("INSERT INTO journalEditor VALUES (?, ?)");
			pstmt.setString(1, email);
			pstmt.setString(2, issn);
			pstmt.executeUpdate();
			//Need to already have account in order to become editor
		}
		catch (SQLException ex){
			ex.printStackTrace();
		}
	}
	
	public static void makeEditor(String email, String title, String forename, String surname, String affiliation, String password, String issn) {
		try(Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team043","team043","38796815")){

			boolean newUser = AuthorTasks.createAccount(email, title, forename, surname, affiliation, password);
			appointEditor(email, issn);
			PreparedStatement pstmt = con.prepareStatement("INSERT INTO journalEditor VALUES (?, ?)");	
			pstmt.setString(1, email);
			pstmt.setString(2, issn);
			pstmt.executeUpdate();
			
			//For when a new editor without an existing user account is needed
			//Call createAccount() from AuthorTasks.java
			//Call appointAEditor()
		}
		catch (SQLException ex){
			ex.printStackTrace();
		}
	}
	
	/**
	 * Creation of new Journal
	 */
	public static void newJournal(String issn, String title, String chiefEditor) {
		try(Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team043","team043","38796815")){

			PreparedStatement pstmt = con.prepareStatement("INSERT INTO journal VALUES (?, ?, ?)");
			pstmt.setString(1, issn);
			pstmt.setString(2, title);
			pstmt.setString(3, chiefEditor);
			pstmt.executeUpdate();
			
		}
		catch (SQLException ex){
			ex.printStackTrace();
		}
		
	}
	
	/**
	 * Allows chief to make different appointed editor chief
	 */
	public static void makeEditorChief(String email, String issn) {
		try(Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team043","team043","38796815")){

			PreparedStatement pstmt = con.prepareStatement("UPDATE journal SET ChiefEditor = ? WHERE ISSN = ?");
			pstmt.setString(1, email);
			pstmt.setString(2, issn);
			pstmt.executeUpdate();
			//Change status of editor to chief editor
		}
		catch (SQLException ex){
			ex.printStackTrace();
		}
	}
	
	
	public static void editorRetire(String email, String issn) {
		try(Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team043","team043","38796815")){
	
			PreparedStatement pstmt = con.prepareStatement("DELETE FROM journalEditors WHERE Email = ? AND ISSN = ?");
			pstmt.setString(1, email);
			pstmt.setString(2, issn);
			pstmt.executeUpdate();
			// Change status of editor back to user
		}
		catch (SQLException ex){
			ex.printStackTrace();
		}
	}
	
	public static void publishArticle(int subID, String issn, int volume, int edition) {
		try(Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team043","team043","38796815")){

			//then also need pageRange  - possibly assume length of article is 10. Dummy value should be mentioned in report
			
			PreparedStatement pstmt = con.prepareStatement("SELECT Title, Abstract, Link FROM submission WHERE ISSN = ? AND subID = ?");
			pstmt.setString(1, issn);
			pstmt.setInt(2, subID);
			ResultSet res = pstmt.executeQuery();

			String title = res.getString(1);
			String abstracts = res.getString(2);
			String link = res.getString(3);
			String pageRange = "3-7";
			
			pstmt = con.prepareStatement("SELECT COUNT(*) FROM edition WHERE issn = ? AND volume = ?"); 
			pstmt.setString(1, issn);
			pstmt.setInt(2, volume);
			ResultSet rs = pstmt.executeQuery();
			
			int editionContents = rs.getInt(1);
			if (editionContents <8) {
				pstmt = con.prepareStatement("INSERT (ISSN,Volume,Edition,PageRange,Title,Abstract,Link) INTO article VALUES (?, ?, ?, ?, ?, ?, ?)");
				pstmt.setString(1, issn);
				pstmt.setInt(2, volume);
				pstmt.setInt(3, edition);
				pstmt.setString(4, "10");
				pstmt.setString(5, title);
				pstmt.setString(6, abstracts);
				pstmt.setString(7, link);
				pstmt.executeUpdate();
			}
			//else call createEdition() function and add article to this new edition
			else {
				int editionNo = createEdition(issn, volume, edition+1, "Decemeber");
				pstmt = con.prepareStatement("INSERT (ISSN,Volume,Edition,PageRange,Title,Abstract,Link) INTO article VALUES (?, ?, ?, ?, ?, ?, ?)");
				pstmt.setString(2, issn);
				pstmt.setInt(3, volume);
				pstmt.setInt(4, editionNo);
				pstmt.setString(5, "10");
				pstmt.setString(6, title);
				pstmt.setString(7, abstracts);
				pstmt.setString(8, link);
				pstmt.executeUpdate();
			}
		}		
		catch (SQLException ex){
			ex.printStackTrace();
		}
	}
	
	
	public static void rejectSubmission(int subID) {
		//deletes a submission from submission table
		//assuming no related reviews to delete since submission is rejected before becoming an article?
		try(Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team043","team043","38796815")){
			PreparedStatement pstmt = con.prepareStatement("DELETE submission, reviews FROM submission INNER JOIN reviews WHERE subID = ?");
			pstmt.setInt(1, subID);
			pstmt.executeUpdate();
		} 
		catch (SQLException ex){
			ex.printStackTrace();
		}
	}
	
	public static int createEdition(String issn, int volume, int edition, String month){
		int editionNo = edition;
		try(Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team043","team043","38796815")){
	
			PreparedStatement pstmt = con.prepareStatement("SELECT COUNT(*) FROM volume WHERE ISSN = ? AND volume = ?");
			pstmt.setString(1, issn);
			pstmt.setInt(2, volume);
			ResultSet rs = pstmt.executeQuery();

			int volumeContents = rs.getInt(1);
			
			if (volumeContents <6) {
				pstmt = con.prepareStatement("INSERT INTO edition VALUES (?, ?, ? ,?, 0)");
				pstmt.setString(1, issn);
				pstmt.setInt(2, volume);
				pstmt.setInt(3, edition);
				pstmt.setString(4, month);
				pstmt.executeUpdate();
			}
			else {
				createVolume(issn, volume+1, 2019);
				pstmt = con.prepareStatement("INSERT INTO edition VALUES (?, ?, ? ,?, 0)");
				pstmt.setString(1, issn);
				pstmt.setInt(2, volume+1);
				pstmt.setInt(3, 1);
				pstmt.setString(4, month);
				pstmt.executeUpdate();
				editionNo = 1;
			}
			//check number of editions in volume is < 6
			//Should then call createVolume if necessary
			//issn volume edition month
		}
		catch (SQLException ex){
			ex.printStackTrace();
		}
		return editionNo;
	}
	
	public static void publishEdition(String issn, int volume, int edition) {
		try(Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team043","team043","38796815")){

			PreparedStatement pstmt = con.prepareStatement("UPDATE edition SET Published = 1 WHERE ISSN = ? AND Volume = ? AND Edition = ?");
			pstmt.setString(1, issn);
			pstmt.setInt(2, volume);
			pstmt.setInt(3, edition);
			pstmt.executeUpdate();
			//check number of editions in volume is < 6
			//Should then call createVolume if necessary
		}
		catch (SQLException ex){
			ex.printStackTrace();
		}
	}
	
	public static void createVolume(String issn, int volume, int year) {
		try(Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team043","team043","38796815")){

			PreparedStatement pstmt = con.prepareStatement("INSERT INTO volume VALUES ?, ?, ?");
			pstmt.setString(1, issn);
			pstmt.setInt(2, volume);
			pstmt.setInt(3, year);
			pstmt.executeUpdate();
		}
		catch (SQLException ex){
			ex.printStackTrace();
		}
	}
	
	public static void deleteAccount(String email) {
		try(Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team043","team043","38796815")){
			PreparedStatement pstmt = con.prepareStatement("DELETE FROM journalEditors WHERE Email = ?");
			pstmt.setString(1, email);
			pstmt.executeUpdate();
			pstmt = con.prepareStatement("SELECT FROM submissionAuthors WHERE Email = ?");
			pstmt.setString(1, email);
			ResultSet results = pstmt.executeQuery();
			if (!results.first()) {
				pstmt = con.prepareStatement("DELETE FROM account WHERE Email = ?");
				pstmt.setString(1, email);
				pstmt.executeUpdate();
			}
		} catch (SQLException ex){
			ex.printStackTrace();
		}
	}
}