package com.jobportal.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
	private static final String DB_NAME="job_portal";
	private static final String DB_URL="jdbc:mysql://localhost:3306/"+DB_NAME;
	private static final String DB_USER="root";
	private static final String DB_PASSWORD="Sandeep@#1";
	
	private static Connection con=null;
	
	private DbConnection(){}
	
	public static Connection getConnection() {
				try
				{
					if(con==null || con.isClosed())
					{
						Class.forName("com.mysql.cj.jdbc.Driver");
						con=DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
			    	}
				}
        catch(ClassNotFoundException  | SQLException e )
        {
        	e.printStackTrace();
	    }
				
    return con;
  }
	
}
