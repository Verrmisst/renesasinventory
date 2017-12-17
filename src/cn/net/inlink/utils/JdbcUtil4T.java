package cn.net.inlink.utils;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class JdbcUtil4T {

	public static DataSource getDataSource() {
		InputStream in = JdbcUtil.class.getClassLoader().getResourceAsStream(
				"Jdbc_tomcat.properties");
		Properties ps = new Properties();

		DataSource dataSource = null;
		try {
			ps.load(in);

			//String dsName = "java:comp/env/jdbc/RSB";
			
			String dsName = ps.getProperty("dsName");
			
		    dsName = dsName.trim();
			
			//System.out.println(dsName);
			
			Context ctx = new InitialContext();

			dataSource = (DataSource) ctx.lookup(dsName);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return dataSource;
	}

	public static Connection getConnection() {

		Connection conn = null;

		try {
			conn = getDataSource().getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return conn;
	}

	public static void close(ResultSet rs, Statement st, Connection con) {
		try {
			if (rs != null)
				rs.close();
			if (st != null)
				st.close();
			if (con != null)
				con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
