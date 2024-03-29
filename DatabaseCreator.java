import java.sql.*;
import assignment.PasswordHash;

public class DatabaseCreator {
	public static void main(String [] args) {
		//dbDrop();
		//dbCreation();
		//dbSetUp();
		/*
		try (Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team043?user=team043&password=38796815")){
			Statement stmt = con.createStatement();
			System.out.println("start");
			//stmt.executeUpdate("CREATE TABLE test (example varchar(20), fuckthis int);");
			ResultSet x = stmt.executeQuery(String.format("SELECT Edition FROM edition WHERE ISSN = '1234-5678' AND Vol = 123"));
			while (x.next()) {
				System.out.println(x.getInt(1));
			}
			
			System.out.println("end");
		} catch (SQLException ex ) {ex.printStackTrace();}
		*/
	}
	public static void dbSetUp() {
		try (Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team043","team043","38796815")) {
			Statement stmt = con.createStatement();
			System.out.println("start");
			/*
			ResultSet results = stmt.executeQuery("SELECT * FROM author WHERE AuthorID = 321 AND Email ");
			while (results.next()) {
				System.out.println(results.getString(1));
				System.out.println(results.getString(2));
				System.out.println(results.getString(3));
				System.out.println(results.getString(4));
				System.out.println(results.getString(5));
			}
			*/
			/*
			stmt.executeUpdate("DELETE FROM articleAuthors");
			stmt.executeUpdate("DELETE FROM author");
			stmt.executeUpdate("DELETE FROM article");
			stmt.executeUpdate("DELETE FROM edition");
			stmt.executeUpdate("DELETE FROM volume");
			stmt.executeUpdate("DELETE FROM journalEditors");
			stmt.executeUpdate("DELETE FROM journal");
			stmt.executeUpdate("DELETE FROM account");
			*/
			System.out.println(PasswordHash.getHashedString("password"));
			/*
			stmt.executeUpdate(String.format("INSERT INTO account VALUES ('TestEditor@aol.com', 'Dr', 'Ed', 'Tor', 'University of Sheffield', '%s')",PasswordHash.getHashedString("password")));
			stmt.executeUpdate("INSERT INTO journal VALUES ('2019-0001', 'Test2 Journal', 'TestEditor@aol.com')");
			stmt.executeUpdate("INSERT INTO	journalEditors VALUES ('TestEditor@aol.com', '2019-0001')");
			stmt.executeUpdate("INSERT INTO volume VALUES ('2019-0001', 1, 2019)");
			stmt.executeUpdate("INSERT INTO edition VALUES ('2019-0001', 1, 11, 'December', 1)");
			stmt.executeUpdate("INSERT INTO article VALUES (111, '2019-0001', 1, 11, '001-003', 'Test Article', 'Abstract paragraph', 'link')");
			stmt.executeUpdate("INSERT INTO author VALUES (321, 'Mr', 'Test', 'Testy', 'TestAuthor@aol.com')");
			stmt.executeUpdate("INSERT INTO articleAuthors VALUES (321, 111)");
			
			stmt.executeUpdate(String.format("INSERT INTO account VALUES ('TestAuthor@aol.com', 'Dr', 'John', 'Smith', 'University of Sheffield', '%s')",PasswordHash.getHashedString("password")));
			*/
			/*
			stmt.executeUpdate("INSERT INTO submission VALUES (1235, 'Submit test', 'Abstract test', 'pdf link', '2019-0001', 'TestAuthor@aol.com')");
			stmt.executeUpdate("INSERT INTO submissionAuthors VALUES ('TestAuthor@aol.com', 1235)");
			stmt.executeUpdate(String.format("INSERT INTO account VALUES ('TestReview1@aol.com', 'Dr', 'Ed', 'Tor', 'University of Sheffield', '%s')",PasswordHash.getHashedString("password")));
			stmt.executeUpdate(String.format("INSERT INTO account VALUES ('TestReview2@aol.com', 'Dr', 'Ed', 'Tor', 'University of Sheffield', '%s')",PasswordHash.getHashedString("password")));
			stmt.executeUpdate(String.format("INSERT INTO account VALUES ('TestReview3@aol.com', 'Dr', 'Ed', 'Tor', 'University of Sheffield', '%s')",PasswordHash.getHashedString("password")));
			stmt.executeUpdate("INSERT INTO reviewers VALUES (1, 'TestReview1@aol.com', 0)");
			stmt.executeUpdate("INSERT INTO reviewers VALUES (2, 'TestReview2@aol.com', 0)");
			stmt.executeUpdate("INSERT INTO reviewers VALUES (3, 'TestReview3@aol.com', 0)");
			stmt.executeUpdate("INSERT INTO reviews VALUES (1235, 1, 'Strong Accept', 'review 1', 'typos text here', 'criticisms text here', 'response text here', 'Strong Accept')");
			stmt.executeUpdate("INSERT INTO reviews VALUES (1235, 2, 'Strong Accept', 'review 2', 'typos text here', 'criticisms text here', 'response text here', 'Strong Accept')");
			stmt.executeUpdate("INSERT INTO reviews VALUES (1235, 3, 'Strong Accept', 'review 3', 'typos text here', 'criticisms text here', 'response text here', 'Strong Accept')");
			
			stmt.executeUpdate("INSERT INTO submission VALUES (1234, 'Reject test', 'Abstract test', 'pdf link', '2019-0001', 'TestAuthor@aol.com')");
			stmt.executeUpdate("INSERT INTO submissionAuthors VALUES ('TestAuthor@aol.com', 1234)");
			
			stmt.executeUpdate("INSERT INTO reviews VALUES (1234, 1, 'Strong Accept', 'review 1', 'typos text here', 'criticisms text here', 'response text here', 'Strong Accept')");
			stmt.executeUpdate("INSERT INTO reviews VALUES (1234, 2, 'Strong Accept', 'review 2', 'typos text here', 'criticisms text here', 'response text here', 'Strong Accept')");
			stmt.executeUpdate("INSERT INTO reviews VALUES (1234, 3, 'Strong Accept', 'review 3', 'typos text here', 'criticisms text here', 'response text here', 'Strong Accept')");
			*/
			//stmt.executeUpdate("INSERT INTO reviewers VALUES (42, 'TestAuthor@aol.com', 2)");
			/*ResultSet results = stmt.executeQuery("SELECT column_name FROM information_schema.COLUMNS WHERE table_name LIKE 'reviews'");
			while (results.next()) {
				System.out.println(results.getString(1));
			}*/
			//stmt.executeUpdate("INSERT INTO reviews VALUES (123, 42, 'Strong Accept', 'summary text here', 'typos text here', 'criticisms text here', 'response text here', 'Strong Accept')");
			
			//ResultSet results = stmt.executeQuery("SELECT * FROM journalEditors");
			/*
			while (results.next()) {
				System.out.println(results.getString(1));
				System.out.println(results.getString(2));
			}
			*/
			System.out.println("end");
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
					"	Volume int NOT NULL,\r\n" + 
					"	Edition int NOT NULL,\r\n" + 
					"	Month varchar(255),\r\n" + 
					"	Published boolean,\r\n" + 
					"	PRIMARY KEY (ISSN, Volume, Edition),\r\n" + 
					"	FOREIGN KEY (ISSN, Volume) REFERENCES volume(ISSN, Volume));";
			//stmt.executeUpdate(str);
			str = "CREATE TABLE reviewers (\r\n" + 
					"	RevID int NOT NULL PRIMARY KEY,\r\n" + 
					"	Email varchar(255),\r\n" + 
					"   RemainingReviews int,\r\n" +
					"	FOREIGN KEY (Email) REFERENCES account(Email));";
			//stmt.executeUpdate(str);
			str = "CREATE TABLE submission (\r\n" + 
					"	SubID int NOT NULL PRIMARY KEY,\r\n" + 
					"	Title varchar(255),\r\n" + 
					"	Abstract varchar(255),\r\n" + 
					"	Link varchar(255),\r\n" +
					"	ISSN varchar(255),\r\n" + 
					"	MainAuthor varchar(255),\r\n" + 
					"	FOREIGN KEY (MainAuthor) REFERENCES account(Email));";
			stmt.executeUpdate(str);
			str = "CREATE TABLE reviews (\r\n" + 
					"	SubID int NOT NULL,\r\n" + 
					"	RevID int NOT NULL,\r\n" + 
					"	Initial_verdict varchar(255),\r\n" + 
					"	Summary varchar(255),\r\n" + 
					"	Typos varchar(255),\r\n" +
					"	Criticisms varchar(255),\r\n" +
					"	Response varchar(255),\r\n" + 
					"	Verdict varchar(255),\r\n" + 
					"	PRIMARY KEY (SubID, RevID),\r\n" + 
					"	FOREIGN KEY (SubID) REFERENCES submission(SubID),\r\n" + 
					"	FOREIGN KEY (RevID) REFERENCES reviewers(RevID));";
			stmt.executeUpdate(str);
			str = "CREATE TABLE submissionAuthors (\r\n" + 
					"	Email varchar(255) NOT NULL,\r\n" + 
					"	SubID int NOT NULL,\r\n" + 
					"	PRIMARY KEY (Email,SubID),\r\n" + 
					"	FOREIGN KEY (Email) REFERENCES account(Email),\r\n" + 
					"	FOREIGN KEY (SubID) REFERENCES submission(SubID));";
			stmt.executeUpdate(str);
			str = "CREATE TABLE article (\r\n" + 
					"	ArticleID int NOT NULL UNIQUE,\r\n" + 
					"	ISSN varchar(255) NOT NULL,\r\n" + 
					"	Volume int NOT NULL,\r\n" + 
					"	Edition int NOT NULL,\r\n" + 
					"	PageRange varchar(255) NOT NULL,\r\n" + 
					"	Title varchar(255),\r\n" + 
					"	Abstract varchar(255),\r\n" + 
					"	Link varchar(255),\r\n" + 
					"	PRIMARY KEY (ISSN,Volume,Edition,PageRange),\r\n" + 
					"	FOREIGN KEY (ISSN,Volume,Edition) REFERENCES edition(ISSN,Volume,Edition));"; 
			stmt.executeUpdate(str);
			str = "CREATE TABLE author (\r\n" + 
					"	AuthorID int PRIMARY KEY NOT NULL,\r\n" + 
					"	Title varchar(255),\r\n" + 
					"	Forename varchar(255),\r\n" + 
					"	Surname varchar(255),\r\n" + 
					"	Email varchar(255));";
			stmt.executeUpdate(str);
			str = "CREATE TABLE articleAuthors (\r\n" + 
					"AuthorID int NOT NULL,\r\n" + 
					"ArticleID int NOT NULL,\r\n" + 
					"PRIMARY KEY (AuthorID,ArticleID),\r\n" + 
					"FOREIGN KEY (AuthorID) REFERENCES author(AuthorID),\r\n" + 
					"FOREIGN KEY (ArticleID) REFERENCES article(ArticleID));";
			stmt.executeUpdate(str);
			
		} catch (SQLException ex) {ex.printStackTrace();}
	}
	
	public static void dbDrop() {
		try(Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team043","team043","38796815")) {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("DROP TABLE articleAuthors;");
			stmt.executeUpdate("DROP TABLE author;");
			stmt.executeUpdate("DROP TABLE article;");
			stmt.executeUpdate("DROP TABLE submissionAuthors;");
			stmt.executeUpdate("DROP TABLE reviews;");
			stmt.executeUpdate("DROP TABLE submission;");
			stmt.executeUpdate("DROP TABLE reviewers;");
			stmt.executeUpdate("DROP TABLE edition;");
			stmt.executeUpdate("DROP TABLE volume;");
			stmt.executeUpdate("DROP TABLE journalEditors;");
			stmt.executeUpdate("DROP TABLE journal;");
			stmt.executeUpdate("DROP TABLE account;");
		} catch (SQLException e) {e.printStackTrace();}
	}
}