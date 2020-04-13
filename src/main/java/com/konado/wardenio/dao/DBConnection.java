package com.konado.wardenio.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

	public static Connection getRemoteConnection() throws Exception {

		Class.forName("com.mysql.cj.jdbc.Driver");
		String dbName = System.getenv("RDS_DB_NAME");
		String userName = System.getenv("RDS_USERNAME");
		String password = System.getenv("RDS_PASSWORD");
		String hostname = System.getenv("RDS_HOSTNAME");
		String port = System.getenv("RDS_PORT");
		String jdbcUrl = "jdbc:mysql://" + hostname + ":" + port + "/" + dbName;
		Connection con = DriverManager.getConnection(jdbcUrl, userName, password);

		return con;
	}
}
