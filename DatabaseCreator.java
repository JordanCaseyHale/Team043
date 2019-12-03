import java.sql.*;

import assignment.MySQLConnection;

public class DatabaseCreator {
	public static void main(String [] args) {
		//dbDrop();
		//dbCreation();
		dbSetUp();
		/**
		try (Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team043?user=team043&password=38796815")){
			Statement stmt = con.createStatement();
			System.out.println("start");
			//stmt.executeUpdate("CREATE TABLE test (example varchar(20), fuckthis int);");
			stmt.executeUpdate("INSERT INTO journal (ISSN, Title) VALUES (111111111, 'test')");
			ResultSet x = stmt.executeQuery("SELECT * FROM test");
			System.out.println(x);
			System.out.println("end");
		} catch (SQLException ex ) {ex.printStackTrace();}
		*/
	}
	public static void dbSetUp() {
		try (Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team043","team043","38796815")) {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("INSERT INTO journal VALUES ('1234-5678', 'Test Journal', null)");
			stmt.executeUpdate("INSERT INTO volume VALUES ('1234-5678', 123, 2019)");
			stmt.executeUpdate("INSERT INTO edition VALUES ('1234-5678', 123, 456, 'December')");
			stmt.executeUpdate("INSERT INTO article VALUES (111, '1234-5678', 123, 456, '001-003', 'Test Article', 'Abstract paragraph', 'link', null)");
		} catch (SQLException ex) {ex.printStackTrace();}
	}
	
	public static void dbCreation() {
		try(Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team043","team043","38796815")) {
			Statement stmt = con.createStatement();
			
			String str = "CREATE TABLE account (\r\n" + 
					"	Email varchar(255) NOT NULL PRIMARY KEY,\r\n" + 
					"	Title varchar(4),\r\n" + 
					"	Forename varchar(255),\r\n" + 
					"	Surname varchar(255),\r\n" + 
					"	Affiliation varchar(255),\r\n" + 
					"	Password varchar(255) NOT NULL);";
			//stmt.executeUpdate(str);
			str = "CREATE TABLE journal (\r\n" + 
					"	ISSN varchar(255) NOT NULL PRIMARY KEY,\r\n" + 
					"	Title varchar(255),\r\n" + 
					"	ChiefEditor varchar(255),\r\n" + 
					"	FOREIGN KEY(ChiefEditor) REFERENCES account(Email));";
			//stmt.executeUpdate(str);
			str = "CREATE TABLE journalEditors (\r\n" + 
					"	Email varchar(255) NOT NULL,\r\n" + 
					"	ISSN varchar(255) NOT NULL,\r\n" + 
					"	PRIMARY KEY(Email, ISSN),\r\n" + 
					"	FOREIGN KEY(Email) REFERENCES account(Email),\r\n" + 
					"	FOREIGN KEY(ISSN) REFERENCES journal(ISSN));";
			//stmt.executeUpdate(str);
			str = "CREATE TABLE volume (\r\n" + 
					"	ISSN varchar(255) NOT NULL,\r\n" + 
					"	Volume int NOT NULL,\r\n" + 
					"	Year int,\r\n" + 
					"	PRIMARY KEY (ISSN, Volume),\r\n" + 
					"	FOREIGN KEY (ISSN) REFERENCES journal(ISSN));";
			//stmt.executeUpdate(str);
			str = "CREATE TABLE edition (\r\n" + 
					"	ISSN varchar(255) NOT NULL,\r\n" + 
					"	Vol int NOT NULL,\r\n" + 
					"	Edition int NOT NULL,\r\n" + 
					"	Month varchar(255),\r\n" + 
					"	PRIMARY KEY (ISSN, Vol, Edition),\r\n" + 
					//"	FOREIGN KEY (ISSN) REFERENCES journal(ISSN),\r\n" + 
					"	FOREIGN KEY (ISSN, Vol) REFERENCES volume(ISSN, Volume));";
			//stmt.executeUpdate(str);
			str = "CREATE TABLE reviewer (\r\n" + 
					"	RevID int NOT NULL PRIMARY KEY,\r\n" + 
					"	Email varchar(255),\r\n" + 
					"	FOREIGN KEY (Email) REFERENCES account(Email));";
			//stmt.executeUpdate(str);
			str = "CREATE TABLE submission (\r\n" + 
					"	SubID int NOT NULL PRIMARY KEY,\r\n" + 
					"	Title varchar(255),\r\n" + 
					"	Abstract varchar(255),\r\n" + 
					"	MainAuthor varchar(255),\r\n" + 
					"	Link varchar(255),\r\n" + 
					"	FOREIGN KEY (MainAuthor) REFERENCES account(Email));";
			//stmt.executeUpdate(str);
			str = "CREATE TABLE reviews (\r\n" + 
					"	SubID int NOT NULL,\r\n" + 
					"	RevID int NOT NULL,\r\n" + 
					"	Initial_verdict varchar(255),\r\n" + 
					"	Initial_review varchar(255),\r\n" + 
					"	Response varchar(255),\r\n" + 
					"	Verdict varchar(255),\r\n" + 
					"	PRIMARY KEY (SubID, RevID),\r\n" + 
					"	FOREIGN KEY (SubID) REFERENCES submission(SubID),\r\n" + 
					"	FOREIGN KEY (RevID) REFERENCES reviewer(RevID));";
			//stmt.executeUpdate(str);
			str = "CREATE TABLE submissionAuthors (\r\n" + 
					"	Email varchar(255) NOT NULL,\r\n" + 
					"	SubID int NOT NULL,\r\n" + 
					"	PRIMARY KEY (Email,SubID),\r\n" + 
					"	FOREIGN KEY (Email) REFERENCES account(Email),\r\n" + 
					"	FOREIGN KEY (SubID) REFERENCES submission(SubID));";
			//stmt.executeUpdate(str);
			str = "CREATE TABLE article (\r\n" + 
					"	ArticleID int NOT NULL UNIQUE,\r\n" + 
					"	ISSN varchar(255) NOT NULL,\r\n" + 
					"	Volume int NOT NULL,\r\n" + 
					"	Edition int NOT NULL,\r\n" + 
					"	PageRange varchar(255) NOT NULL,\r\n" + 
					"	Title varchar(255),\r\n" + 
					"	Abstract varchar(255),\r\n" + 
					"	Link varchar(255),\r\n" + 
					"	ChiefAuthor varchar(255),\r\n" + 
					"	PRIMARY KEY (ISSN,Volume,Edition,PageRange),\r\n" + 
					"	FOREIGN KEY (ISSN,Volume,Edition) REFERENCES edition(ISSN,Vol,Edition),\r\n" + 
					"	FOREIGN KEY (ChiefAuthor) REFERENCES account(Email));";
			//stmt.executeUpdate(str);
			str = "CREATE TABLE author (\r\n" + 
					"	AuthorID int PRIMARY KEY NOT NULL,\r\n" + 
					"	Title varchar(255),\r\n" + 
					"	Forename varchar(255),\r\n" + 
					"	Surname varchar(255),\r\n" + 
					"	Email varchar(255));";
			//stmt.executeUpdate(str);
			str = "CREATE TABLE acrticleAuthors (\r\n" + 
					"AuthorID int NOT NULL,\r\n" + 
					"ArticleID int NOT NULL,\r\n" + 
					"PRIMARY KEY (AuthorID,ArticleID),\r\n" + 
					"FOREIGN KEY (AuthorID) REFERENCES author(AuthorID),\r\n" + 
					"FOREIGN KEY (ArticleID) REFERENCES article(ArticleID));";
			//stmt.executeUpdate(str);
			
		} catch (SQLException ex) {ex.printStackTrace();}
	}
	
	public static void dbDrop() {
		try(Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team043","team043","38796815")) {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("DROP TABLE account;");
			stmt.executeUpdate("DROP TABLE journalEditors;");
			stmt.executeUpdate("DROP TABLE journal;");
			stmt.executeUpdate("DROP TABLE volume;");
			stmt.executeUpdate("DROP TABLE edition;");
			stmt.executeUpdate("DROP TABLE reviewers;");
			stmt.executeUpdate("DROP TABLE submission;");
			stmt.executeUpdate("DROP TABLE reviews;");
			stmt.executeUpdate("DROP TABLE submissionAuthors;");
			stmt.executeUpdate("DROP TABLE article;");
			stmt.executeUpdate("DROP TABLE author;");
			stmt.executeUpdate("DROP TABLE articleAuthors;");
		} catch (SQLException e) {e.printStackTrace();}
	}
}