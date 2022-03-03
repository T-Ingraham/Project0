package com.banking.sunnybank;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import java.io.*;
import java.sql.*;

public class ConnectionUtil {
	static Logger logger;
	private ConnectionUtil() {
		logger = App.logger;
	}
	
	public static Connection getConnection() {
		Connection con = null;
		try 
		{
			FileReader rdr = new FileReader("mydb.properties");
			Properties properties = new Properties();
			properties.load(rdr);
			String driver = properties.getProperty("driver");
			String url = properties.getProperty("url");
			String username = properties.getProperty("username");
			String password = properties.getProperty("password");
			
			
			
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","postgres","toonmai23");
		} 
		catch (ClassNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    catch (SQLException e) 
	    {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    return con;
	}

	public static Statement createStatment() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
