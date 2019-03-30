package com.kai.base;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BaseCrud {
	private static Connection conn = null;
	private static Statement st = null;
	private static ResultSet rs = null;
	
	/**
	 * 创建表格
	 * @author ggk
	 * @data 2019年3月29日上午10:50:42
	 * @param sql
	 * @return
	 */
	public int  createTable(String sql)  {
		try {
			conn=JDBCUtils.getConnection();
			st = conn.createStatement();
			return st.executeUpdate(sql);
		}catch(Exception e) {
			JDBCUtils.colseResource(conn, st, null);
			return -1;
		}
	}
	
	/**
	 * 插入数据
	 * @author ggk
	 * @data 2019年3月29日上午10:50:34
	 * @param sql
	 * @return
	 */
	public int insert(String sql) {
		try {
			conn=JDBCUtils.getConnection();
			st = conn.createStatement();
			return st.executeUpdate(sql);
		}catch(Exception e) {
			JDBCUtils.colseResource(conn, st, null);
			return -1;
		}
	}
	
	/**
	 * 删除数据
	 * @author ggk
	 * @data 2019年3月29日上午10:50:09
	 * @param sql
	 * @return
	 */
	public int delete(String sql) {
		try {
			conn=JDBCUtils.getConnection();
			st = conn.createStatement();
			return st.executeUpdate(sql);
		}catch(Exception e) {
			JDBCUtils.colseResource(conn, st, null);
			return -1;
		}
	}
	
	/**
	 * 查询表 
	 * @author ggk
	 * @data 2019年3月28日下午5:18:11
	 * @param sql
	 * @param set  列名集合
	 * @return
	 */
	public List<Map<String,Object>> findList(String sql,Set<String> set){
		try {
			conn=JDBCUtils.getConnection();
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			List<Map<String,Object>> dataList=new ArrayList<>();
			 while(rs.next()) {
				 Map<String,Object> data=new HashMap<String,Object>();
				 for(String column: set) {
					 data.put(column, rs.getObject(column));
				 }
				 dataList.add(data);
			 }
			 return dataList;
		}catch(Exception e) {
			JDBCUtils.colseResource(conn, st, rs);
			return null;
		}
	}
}
