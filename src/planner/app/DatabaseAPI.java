package planner.app;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseAPI {
	
//	public static void connect() {
//	      Connection c = null;
//	      
//	      try {
//	         Class.forName("org.sqlite.JDBC");
//	         c = DriverManager.getConnection("jdbc:sqlite:SQLiteDatabase.db");
//	      } catch ( Exception e ) {
//	         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
//	         System.exit(0);
//	      }
//	      System.out.println("Opened database successfully");
//	   }
	
	public static void createTable() {
	      Connection c = null;
	      Statement stmt = null;
	      
	      try {
	         Class.forName("org.sqlite.JDBC");
	         c = DriverManager.getConnection("jdbc:sqlite:SQLiteDatabase.db");
	         System.out.println("Opened database successfully");

	         stmt = c.createStatement();
	         String sql = "CREATE TABLE employees" +
	                        "(initials    CHAR(4)     PRIMARY KEY     NOT NULL," +
	                        " name        TEXT                        NOT NULL)"; 
	         stmt.executeUpdate(sql);
	         
	         sql = "CREATE TABLE projects " +
                     "(projectNumber   INT    PRIMARY KEY     NOT NULL," +
                     " projectName     TEXT                   NOT NULL," + 
                     " projectLeader   TEXT)";
	         stmt.executeUpdate(sql);
	         
	         sql = "CREATE TABLE activities " +
                     "(activityName        TEXT    PRIMARY KEY     NOT NULL," +
                     " expectedMinutes     INT                     NOT NULL," + 
                     " startTime           DATE                    NOT NULL," +
                     " endTime             DATE                    NOT NULL," +
	         		 " project             INT                     NOT NULL)";
	         stmt.executeUpdate(sql);
	         
	         sql = "CREATE TABLE realisedTimeslot " +
                     "(id                  INT   PRIMARY KEY       NOT NULL," +
                     " employee            TEXT                    NOT NULL," + 
                     " activity            TEXT                    NOT NULL," +
                     " spendMinutes        INT                     NOT NULL," +
	         		 " day                 DATE                    NOT NULL)";
	         stmt.executeUpdate(sql);
	         
	         sql = "CREATE TABLE unrealisedTimeslot " +
                     "(id                  INT   PRIMARY KEY       NOT NULL," +
                     " assignedEmployee    TEXT                    NOT NULL," + 
                     " assignedMinutes     INT                     NOT NULL," +
	         		 " day                 DATE                    NOT NULL)";
	         stmt.executeUpdate(sql);
	         
	         sql = "CREATE TABLE parameters " +
                     "(serialNumber        INT     DEFAULT 0)";
	         stmt.executeUpdate(sql);
	         
	         
	         stmt.close();
	         c.close();
	      } catch ( Exception e ) {
	         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	         System.exit(0);
	      }
	      System.out.println("Table created successfully");
	   }

	public static void createStatement(String input) {
	      Connection c = null;
	      Statement stmt = null;
	      
	      try {
	         Class.forName("org.sqlite.JDBC");
	         c = DriverManager.getConnection("jdbc:sqlite:SQLiteDatabase.db");
	         c.setAutoCommit(false);
	         stmt = c.createStatement();
	         stmt.executeUpdate(input);
	         stmt.close();
	         c.commit();
	         c.close();
	      } catch ( Exception e ) {
	         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	         System.exit(0);
	      }
	   }
	
	public static ArrayList<String> selectString(String table, String column) {
		   ArrayList<String> list = new ArrayList<String>();
		   Connection c = null;
		   Statement stmt = null;
		   try {
		      Class.forName("org.sqlite.JDBC");
		      c = DriverManager.getConnection("jdbc:sqlite:SQLiteDatabase.db");
		      c.setAutoCommit(false);
		      stmt = c.createStatement();
		      ResultSet rs = stmt.executeQuery("SELECT * FROM " + table + ";");
		      
		      while ( rs.next() ) {
		         list.add(rs.getString(column));
		      }
		      
		      rs.close();
		      stmt.close();
		      c.close();
		      
		   } catch ( Exception e ) {
		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      System.exit(0);
		   }
		   return list;
		  }
	
	public static ArrayList<Integer> selectInt(String table, String column) {
		   ArrayList<Integer> list = new ArrayList<Integer>();
		   Connection c = null;
		   Statement stmt = null;
		   try {
		      Class.forName("org.sqlite.JDBC");
		      c = DriverManager.getConnection("jdbc:sqlite:SQLiteDatabase.db");
		      c.setAutoCommit(false);
		      stmt = c.createStatement();
		      ResultSet rs = stmt.executeQuery("SELECT * FROM " + table + ";");
		      
		      while ( rs.next() ) {
		         list.add(rs.getInt(column));
		      }
		      
		      rs.close();
		      stmt.close();
		      c.close();
		      
		   } catch ( Exception e ) {
		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      System.exit(0);
		   }
		   return list;
		  }

	
	
  public static void main( String args[] ) {
      //connect();
	  createTable();
	  //insert();
	  //selectEmployeesInitials();
	  //delete();
	  //create();
   }
}
