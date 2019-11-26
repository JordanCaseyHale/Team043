package assignment;
import java.sql.*;
import java.util.*;
public class MySQLConnection {
	public static void main(String [] args) {
		Enumeration<Driver> list = DriverManager.getDrivers();
		System.out.println("start");
		while (list.hasMoreElements()) {
			System.out.println(list.nextElement());
		}
		System.out.println("end");
	}
	public static Connection openConnection() {
		try(Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team043?user=team043&password=38796815")){
			return con;
		}
		catch (SQLException ex){
			ex.printStackTrace();
			return null;
		}
	}
	
	public static void closeConnection(Connection con) {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) { e.printStackTrace();}
		}
	}
}