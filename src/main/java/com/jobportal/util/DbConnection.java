package com.jobportal.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
	private static final String DB_NAME="job_portal";
	private static final String DB_URL="jdbc:mysql://localhost:3306/"+DB_NAME;
	private static final String DB_USER="root";
	private static final String DB_PASSWORD="manoj123";
	public static Connection con=null;

	public static Connection getConnection() {
		if(con==null) {
			return getConnection(DB_URL,DB_USER,DB_PASSWORD);
		}
		return con;
		
	}

	private static Connection getConnection(String dbUrl, String dbUser, String dbPassword) {
		// TODO Auto-generated method stub
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con=DriverManager.getConnection(dbUrl,dbUser,dbPassword);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return con;
	}

}
