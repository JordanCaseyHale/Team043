package assignment;
import java.sql.*;
import java.util.*;
public class MySQLConnection {
	public static void main(String [] args) {
		try (Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team043?user=team043&password=38796815")){
			Statement stmt = con.createStatement();
			System.out.println("start");
			//stmt.executeUpdate("CREATE TABLE test (example varchar(20), fuckthis int);");
			//stmt.executeUpdate("INSERT INTO test VALUES ('Yoooo', 69)");
			ResultSet x = stmt.executeQuery("SELECT * FROM test");
			String a= "W";
			int b = 0;
			while (x.next()) {
				a = x.getString(1);
				b = x.getInt(2);
			}
			System.out.println(a + b);
			System.out.println("end");
		} catch (SQLException ex ) {ex.printStackTrace();}
	}
	
	public static ResultSet doQuery(String query) {
		ResultSet results = null;
		try(Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team0043","team043","38796815")){
			Statement stmt = con.createStatement();
			//gets results
			//Could check string to improve sercuity
			results = stmt.executeQuery(query);
		}
		catch (SQLException ex){
			ex.printStackTrace();
		}
		return results;
	}
}