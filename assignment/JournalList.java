package assignment;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JournalList {
	public static void main(String [] args) {
		System.out.println(getJournals());
	}
	public static List<List<String>> getJournals() {
		/*
		Connection con = MySQLConnection.openConnection();
		try {
			Statement stmt = con.createStatement();
			ResultSet results = stmt.executeQuery("SELECT Title, ISSN FROM Journal");
			String[] journals = null;
			List<List<String>> journals2 = new ArrayList<List<String>>();
			int count = 0;
			List<String> temp = new ArrayList<String>();
			while (results.next()){
				temp.clear();
				temp.add(results.getString(0));
				temp.add(results.getString(1));
				journals2.add(temp);
				journals[count] = results.getString(0);
				count += 1;
			}
			MySQLConnection.closeConnection(con);
			return journals2;
		} catch (SQLException e) {e.printStackTrace();}
		MySQLConnection.closeConnection(con);
		return null;
		*/
		String str = "SELECT Title, ISSN FROM journal";
		List<List<String>> journals = new ArrayList<List<String>>();
		int count = 0;
		List<String> temp = new ArrayList<String>();
		try(Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team043","team043","38796815")){
			Statement stmt = con.createStatement();
			ResultSet results = stmt.executeQuery(str);
			while (results.next()){
				temp.clear();
				temp.add(results.getString(1));
				temp.add(results.getString(2));
				journals.add(temp);
				count += 1;
			}
			System.out.println(journals);
			return journals;
		} catch (SQLException ex) {ex.printStackTrace();}
		return null;
	}
	
	public static String[] getVolumes(String journal) {
		/*
		Connection con = MySQLConnection.openConnection();
		try {
			Statement stmt = con.createStatement();
			ResultSet results = stmt.executeQuery(String.format("SELECT Volume FROM Volume WHERE ISSN = %s",journal));
			String[] volumes = null;
			int count = 0;
			while (results.next()) {
				volumes[count] = results.getString(0);
				count += 1;
			}
			MySQLConnection.closeConnection(con);
			return volumes;
		}
		catch (SQLException e) {e.printStackTrace();}
		MySQLConnection.closeConnection(con);
		return null;
		*/
		String str = String.format("SELECT Volume FROM volume WHERE ISSN = %s",journal);
		try(Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team043","team043","38796815")){
			Statement stmt = con.createStatement();
			ResultSet results = stmt.executeQuery(str);
			String[] volumes = null;
			int count = 0;
			while (results.next()) {
				volumes[count] = results.getString(0);
				count += 1;
			}
			return volumes;
		} catch (SQLException ex) {ex.printStackTrace();}
		return null;
	}
	
	public static String[] getEditions(String volume, String journal) {
		/*
		Connection con = MySQLConnection.openConnection();
		try {
			Statement stmt = con.createStatement();
			ResultSet results = stmt.executeQuery(String.format("SELECT Edition FROM Edition WHERE ISSN = %s, Volume = %s",journal, volume));
			String[] editions = null;
			int count = 0;
			while (results.next()) {
				editions[count] = results.getString(0)+", "+results.getString(1); 
				count += 1;
			}
			MySQLConnection.closeConnection(con);
			return editions;
		}
		catch (SQLException e) {e.printStackTrace();}
		MySQLConnection.closeConnection(con);
		return null;
		*/
		String str = String.format("SELECT Edition FROM edition WHERE ISSN = %s, Volume = %s",journal, volume);
		try(Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team043","team043","38796815")){
			Statement stmt = con.createStatement();
			ResultSet results = stmt.executeQuery(str);
			String[] editions = null;
			int count = 0;
			while (results.next()) {
				editions[count] = results.getString(0)+", "+results.getString(1); 
				count += 1;
			}
			return editions;
		} catch (SQLException ex) { ex.printStackTrace();}
		return null;
	}
	
	public static String[] getArticles(String edition, String volume, String journal) {
		/*
		Connection con = MySQLConnection.openConnection();
		try {
			Statement stmt = con.createStatement();
			ResultSet results = stmt.executeQuery(String.format("SELECT Name FROM Article WHERE ISSN = %s, Volume = %s, Edition = %s",journal, volume, edition));
			String[] articles = null;
			int count = 0;
			while (results.next()) {
				articles[count] = results.getString(1); //Need to combine Month and Edition
				count += 1;
			}
			MySQLConnection.closeConnection(con);
			return articles;
		}
		catch (SQLException e) {e.printStackTrace();}
		MySQLConnection.closeConnection(con);
		return null;
		*/
		String str = String.format("SELECT Name, pageRange FROM article WHERE ISSN = %s, Volume = %s, Edition = %s",journal, volume, edition);
		try(Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team043","team043","38796815")){
			Statement stmt = con.createStatement();
			ResultSet results = stmt.executeQuery(str);
			String[] articles = null;
			int count = 0;
			while (results.next()) {
				articles[count] = results.getString(1) + ", " + results.getString(2); //Need to combine Month and Edition
				count += 1;
			}
			return articles;
		} catch (SQLException e) {e.printStackTrace();}
		return null;
	}
	
	
	/**
	 * 
	 * @param pageRange
	 * @param edition
	 * @param volume
	 * @param journal
	 * @return
	 */
	public static Article getArticle(String pageRange, String edition, String volume, String journal) {
		/**
		Connection con = MySQLConnection.openConnection();
		try {
			Statement stmt = con.createStatement();
			ResultSet results = stmt.executeQuery(String.format("SELECT * FROM Article WHERE ISSN = %s, Volume = %s, Edition = %s, pageRange = %s",journal, volume, edition, pageRange));
			Article article = null;
			int articleID = results.getInt(0);
			article.setName(results.getString(1));
			article.setISSN(results.getInt(2));
			article.setVolume(results.getInt(3));
			article.setEdition(results.getInt(4));
			article.setPageRange(results.getString(5));
			article.setAbstractPara(results.getString(6));
			article.setPdfLink(results.getString(7));
			article.setRespondName(results.getString(8));
			article.setRespondEmail(results.getString(9));
			results = stmt.executeQuery(String.format("SELECT * FROM Article Author WHERE ArticleID = %2d",articleID));
			List<List<String>> coAuthors = null;
			List<String> temp = null;
			while (results.next()) {
				results = stmt.executeQuery(String.format("SELECT * FROM Author WHERE AuthorID = %2d",results.getString(1)));
				temp.add(results.getString(1));
				temp.add(results.getString(2));
				temp.add(results.getString(3));
				coAuthors.add(temp);
			}
			article.setCoAuthors(coAuthors);
			MySQLConnection.closeConnection(con);
			return article;
		}
		catch (SQLException e) {e.printStackTrace();}
		MySQLConnection.closeConnection(con);
		return null;
		*/
		String str = String.format("SELECT * FROM article WHERE ISSN = %s, Volume = %s, Edition = %s, PageRange = %s",journal, volume, edition, pageRange);
		try(Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team043","team043","38796815")){
			Statement stmt = con.createStatement();
			ResultSet results = stmt.executeQuery(str);
			Article article = null;
			int articleID = results.getInt(0);
			article.setName(results.getString(1));
			article.setISSN(results.getString(2));
			article.setVolume(results.getInt(3));
			article.setEdition(results.getInt(4));
			article.setPageRange(results.getString(5));
			article.setAbstractPara(results.getString(6));
			article.setPdfLink(results.getString(7));
			article.setRespondName(results.getString(8));
			article.setRespondEmail(results.getString(9));
			str = String.format("SELECT * FROM Article Author WHERE ArticleID = %2d",articleID);
			results = stmt.executeQuery(str);
			List<List<String>> coAuthors = null;
			List<String> temp = null;
			ResultSet authorResult = null;
			while (results.next()) {
				str = String.format("SELECT * FROM Author WHERE AuthorID = %2d",results.getString(1));
				authorResult = stmt.executeQuery(str);
				temp.add(authorResult.getString(1));
				temp.add(authorResult.getString(2));
				temp.add(authorResult.getString(3));
				coAuthors.add(temp);
			}
			article.setCoAuthors(coAuthors);
			return article;
		} catch (SQLException ex) {ex.printStackTrace();}
		return null;
	}
}