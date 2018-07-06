package com.jvc.ext;

import java.sql.*;
public class ConnectionFactory {

	public static final String url = "jdbc:mysql://cse.unl.edu/datduyn";
	public static final String username = "datduyn";
	public static final String password = "Chimcuteo1";
	public static final int MAX_CONNS = 50;
	public static int noOfConnections = 0;
	
	public ConnectionFactory() {
	}
	
	public static Connection getOne()
	{
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection(url, username, password);
			noOfConnections++;
			return conn;
			
		} catch (InstantiationException e) {
			System.out.println("InstantiationException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			System.out.println("IllegalAccessException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public static void release(Connection conn)
	{
	   try {
			if(conn != null && !conn.isClosed())
			{	
			   conn.close();
			   noOfConnections--;
			}
		}
		
		catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	
}
