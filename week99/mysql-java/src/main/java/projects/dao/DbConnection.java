package projects.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import projects.Exception.DbException;


public class DbConnection {
	

    private static final String HOST = "localhost";
    private static final int PORT = 3306;
    private static final String SCHEMA = "projects";
    private static final String USER = "projects";
    private static final String PASSWORD = "projects";
    private static Connection conn;

    public static Connection getConnection() {
    	
		
        if (conn == null) {
            String uri = String.format("jdbc:mysql://%s:%d/%s", HOST, PORT, SCHEMA);
            try {
                conn = DriverManager.getConnection(uri, USER, PASSWORD);
                System.out.println("Connection to schema " + SCHEMA + " is successful.");
                return getConnection();
            } catch (SQLException e) {
            	 throw new DbException(e);
               
               
            }
        }
		return conn;
       
    }
}
