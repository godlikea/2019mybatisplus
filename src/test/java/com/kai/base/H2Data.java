package com.kai.base;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class H2Data {
	// 数据库连接URL，当前连接的是E:/H2目录下的gacl数据库
	private static final String JDBC_URL = "jdbc:h2:mem:test";
	// 连接数据库时使用的用户名
	private static final String USER = "sa";
	// 连接数据库时使用的密码
	private static final String PASSWORD = "";
	// 连接H2数据库时使用的驱动类，org.h2.Driver这个类是由H2数据库自己提供的，在H2数据库的jar包中可以找到
	private static final String DRIVER_CLASS = "org.h2.Driver";
	static {
		try {
			Class.forName(DRIVER_CLASS);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
	}
	
	
	public int createTable() {
		return 1;
	}
	public static void main(String[] args) throws Exception {
		 Class.forName(DRIVER_CLASS);       // 根据连接URL，用户名，密码获取数据库连接
		 Connection conn = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
		 Statement statement = conn.createStatement();
		 int update = statement.executeUpdate("CREATE TABLE  t_manager (id INT PRIMARY KEY, name VARCHAR(64),pwd VARCHAR(64));");
		 System.out.println(update);
		 statement.executeUpdate("INSERT INTO t_manager VALUES('" + 1+ "','大日如来','男')");
		 statement.executeUpdate("INSERT INTO t_manager VALUES('" + 2+ "','xiaoxiao','123')");
		 ResultSet query = statement.executeQuery("select *  from t_manager");
		 while(query.next()) {
			 System.out.println(query.getInt("id")+query.getString("name")+query.getString("pwd"));
		 }
	}
}
