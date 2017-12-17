package cn.net.inlink.utils;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class JdbcUtil {

	private static final ComboPooledDataSource dataSource = new ComboPooledDataSource();
	
	static{
		InputStream in = JdbcUtil.class.getClassLoader().getResourceAsStream("Jdbc.properties");
		Properties ps = new Properties();
		try {
			ps.load(in);
			
			String className = ps.getProperty("className");
			String url = ps.getProperty("url");
			String username = ps.getProperty("uname");
			String userpass = ps.getProperty("upass");
			
			dataSource.setDriverClass(className);
			dataSource.setJdbcUrl(url);
			dataSource.setUser(username);
			dataSource.setPassword(userpass);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection(){
		
		Connection con = null;
		try {
			con = dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}
	
	public static void close(ResultSet rs,Statement st,Connection con){
		try {
			if(rs!=null) rs.close();
			if(st!=null) st.close();
			if(con!=null) con.close();
		} catch (Exception e) {
		    e.printStackTrace();
		}
	}
	
	public static DataSource getDataSource(){
		
		return dataSource;
	}
}
