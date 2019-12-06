package assignment;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JournalList {
	public static void main(String [] args) {
		//System.out.println(getJournals());
	}
	public static List<Journal> getJournals() {
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
		List<Journal> journals = new ArrayList<Journal>();
		Journal temp = new Journal();
		try(Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team043","team043","38796815")){
			PreparedStatement pstmt = con.prepareStatement("SELECT Title, ISSN FROM journal");
			ResultSet results = pstmt.executeQuery();
			while (results.next()){
				temp = new Journal();
				temp.setTitle(results.getString(1));
				temp.setISSN(results.getString(2));
				//System.out.println(temp);
				journals.add(temp);
			}
			//System.out.println("1");
			//System.out.println(journals);
			//System.out.println("2");
			return journals;
		} catch (SQLException ex) {ex.printStackTrace();}
		return null;
	}
	
	public static List<Volume> getVolumes(String journal) {
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
		List<Volume> volumes = new ArrayList<Volume>();
		try(Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team043","team043","38796815")){
			PreparedStatement pstmt = con.prepareStatement("SELECT Year, Volume FROM volume WHERE ISSN = ?");
			pstmt.setString(1, journal);
			ResultSet results = pstmt.executeQuery();
			while (results.next()) {
				Volume tmp = new Volume();
				tmp.setYear(results.getInt(1));
				tmp.setVolume(results.getInt(2));
				volumes.add(tmp);
			}
			//System.out.println(volumes);
			return volumes;
		} catch (SQLException ex) {ex.printStackTrace();}
		return null;
	}
	
	public static List<Edition> getEditions(int volume, String journal) {
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
		List<Edition> editions = new ArrayList<Edition>();
		try(Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team043","team043","38796815")){
			PreparedStatement pstmt = con.prepareStatement("SELECT Edition, Month FROM edition WHERE ISSN = ? AND Volume = ? AND Published = 1");
			pstmt.setString(1, journal);
			pstmt.setInt(2, volume);
			ResultSet results = pstmt.executeQuery();
			while (results.next()) {
				Edition tmp = new Edition();
				tmp.setEdition(results.getInt(1));
				tmp.setMonth(results.getString(2));
				editions.add(tmp); 
			}
			return editions;
		} catch (SQLException ex) { ex.printStackTrace();}
		return null;
	}
	
	public static List<String> getArticles(int edition, int volume, String journal) {
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
		String str = String.format("SELECT Title, PageRange FROM article WHERE ISSN = '%s' AND Volume = %2d AND Edition = %s",journal, volume, edition);
		try(Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team043","team043","38796815")){
			PreparedStatement pstmt = con.prepareStatement("SELECT Title, PageRange FROM article WHERE ISSN = ? AND Volume = ? AND Edition = ?");
			pstmt.setString(1, journal);
			pstmt.setInt(2,volume);
			pstmt.setInt(3, edition);
			ResultSet results = pstmt.executeQuery();
			List<String> articles = new ArrayList<String>();
			int count = 0;
			while (results.next()) {
				articles.add(results.getString(1) + ", Page Range: " + results.getString(2)); //Need to combine Month and Edition
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
	public static Article getArticle(String pageRange, int edition, int volume, String journal) {
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
		try(Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team043","team043","38796815")){
			PreparedStatement pstmt = con.prepareStatement("SELECT * FROM article WHERE ISSN = ? AND Volume = ? AND Edition = ? AND PageRange = ?");
			pstmt.setString(1, journal);
			pstmt.setInt(2, volume);
			pstmt.setInt(3, edition);
			pstmt.setString(4, pageRange);
			ResultSet results = pstmt.executeQuery();
			ResultSet idResults, resResults;
			Article article = new Article();
			int authorID = 0;
			while (results.next()) {
				article.setArticleID(results.getInt(1));
				article.setISSN(results.getString(2));
				article.setVolume(results.getInt(3));
				article.setEdition(results.getInt(4));
				article.setPageRange(results.getString(5));
				article.setName(results.getString(6));
				article.setAbstractPara(results.getString(7));
				article.setPdfLink(results.getString(8));
				
				//article.setRespondName(results.getString(9));
				//article.setRespondEmail(results.getString(10));
			}
			pstmt = con.prepareStatement("SELECT AuthorID FROM articleAuthors WHERE ArticleID = ?");
			pstmt.setInt(1, article.getArticleID());
			idResults = pstmt.executeQuery();
			while (idResults.next()) {
				authorID = idResults.getInt(1);
			}
			System.out.println(authorID);
			
			pstmt = con.prepareStatement("SELECT * FROM author WHERE AuthorID = ?");
			pstmt.setInt(1, authorID);
			resResults = pstmt.executeQuery();
			while (resResults.next()) {
				if (resResults.getString(5) != null) {
					article.setRespondName(resResults.getString(2)+", "+resResults.getString(3)+" "+resResults.getString(4));
					article.setRespondEmail(resResults.getString(5));
				}
			}
			System.out.println(article.getRespondEmail());
			System.out.println(article.getRespondName());
			pstmt = con.prepareStatement("SELECT Title, Forename, Surname FROM author WHERE AuthorID = ? AND Email = null");
			pstmt.setInt(1, authorID);
			List<Author> coAuthors = new ArrayList<Author>();
			ResultSet authorResult = pstmt.executeQuery();
			while (authorResult.next()) {
				Author temp = new Author();
				temp.setTitle(authorResult.getString(1));
				temp.setForename(authorResult.getString(2));
				temp.setSurname(authorResult.getString(3));
				coAuthors.add(temp);
			}
			article.setCoAuthors(coAuthors);
			return article;
		} catch (SQLException ex) {ex.printStackTrace();}
		return null;
	}
}