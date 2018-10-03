package com.ocp.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.Test;

public class JDBCTest {

	@Test
	public void testCreateConnectionWithDriverManager() throws ClassNotFoundException, SQLException{
		
		Class.forName("com.mysql.jdbc.Driver");
		Connection mConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/MY_DB");
	}
}
