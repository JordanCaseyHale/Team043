package assignment;
import java.sql.*;
import java.util.*;
public class MySQLConnection {
	public static void main(String [] args) {
		System.out.print(checkInput("TEST;ING"));
		
		
		try (Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team043?user=team043&password=38796815")){
			Statement stmt = con.createStatement();
			System.out.println("start");
			//stmt.executeUpdate("CREATE TABLE test (example varchar(20), fuckthis int);");
			//stmt.executeUpdate("INSERT INTO test VALUES ('Yoooo', 69)");
			ResultSet x = stmt.executeQuery("SELECT * FROM journal");
			while (x.next()) {
				System.out.println(x.getString(1));
				System.out.println(x.getString(2));
			}
			System.out.println("end");
		} catch (SQLException ex ) {ex.printStackTrace();}
		
	}
	
	/*public static CachedRowSet doQuery(String query) {
		try(Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team043","team043","38796815")){
			Statement stmt = con.createStatement();
			//gets results
			//Could check string to improve security
			ResultSet results = stmt.executeQuery(query);
			crs.populate(results);
		}
		catch (SQLException ex){
			ex.printStackTrace();
		}
		return crs;
	}*/
	//Doesn't work because ResultSet is closed when try catch closes
	
	
	/*
	public static CachedRowSet doQuery(String query) {
	try(Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team043","team043","38796815")){
		Statement stmt = con.createStatement();
		//gets results
		//Could check string to improve security
		ResultSet results = stmt.executeQuery(query);
		crs.populate(results);
	}
	catch (SQLException ex){
		ex.printStackTrace();
	}
		return crs;
	}
	*/
	
	
	public static boolean doUpdate(String update) {
		try(Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team043","team043","38796815")) {
			Statement stmt = con.createStatement();
			stmt.executeUpdate(update);
			return true;
		} catch (SQLException ex) {ex.printStackTrace();}
		return false;
	}

	//Basic sql injection prevention, add to as you think required
	private static String checkInput(String query) {
		if(query.contains(";")) {
			query = query.substring(0,query.indexOf(';'));
		}
		return query;
	}
}