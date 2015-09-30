package org.developerworld.commons.db.info;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 用于测试数据库编码的类
 * 
 * @author Roy Huang
 * @version 20101204
 * @deprecated 不再提供支持
 */
public class DbCharsetChecker {

	private String[] testCharsets;
	private String driver;
	private String url;
	private String user;
	private String password;
	private Connection connection;
	private String testText = "测试编码";
	private String testTable = this.getClass().getName().replaceAll("\\.", "_")
			+ "_table";

	public DbCharsetChecker(String driver, String url, String user,
			String password, String[] testCharsets) {
		this.driver = driver;
		this.url = url;
		this.user = user;
		this.password = password;
		this.testCharsets = testCharsets;
	}

	public void setTestText(String testText) {
		this.testText = testText;
	}

	public void setTestTable(String testTable) {
		this.testTable = testTable;
	}

	public String runTest() throws SQLException, UnsupportedEncodingException,
			ClassNotFoundException {
		String rst = null;
		try {
			connection();
			dropTable();
			createTable();
			insertData();
			rst = getResult();
			dropTable();
		} finally {
			close();
		}
		return rst;
	}

	private void connection() throws SQLException, ClassNotFoundException {
		if (connection == null || connection.isClosed()) {
			Class.forName(driver);
			connection = DriverManager.getConnection(url, user, password);
		}
	}

	private void close() throws SQLException {
		if (connection != null && !connection.isClosed())
			connection.close();
		connection = null;
	}

	private String getResult() throws SQLException,
			UnsupportedEncodingException {
		StringBuffer rst = new StringBuffer();
		Statement s = null;
		ResultSet rs = null;
		try {
			String sql = "select charset,testtext from " + testTable;
			s = connection.createStatement();
			rs = s.executeQuery(sql);
			while (rs.next()) {
				String chartset = rs.getString("charset");
				String value = rs.getString("testtext");
				rst.append("====================" + chartset
						+ "====================\n");
				rst.append("defaule:" + value).append("\n");
				for (String charset : testCharsets) {
					for (String charset2 : testCharsets) {
						String tmp = value;
						if (charset != null && charset2 != null)
							tmp = new String(tmp.getBytes(charset), charset2);
						else if (charset2 != null)
							tmp = new String(tmp.getBytes(), charset2);
						rst.append(
								"select type:new String(value.getBytes(\""
										+ charset + "\"),\"" + charset2
										+ "\")=" + tmp).append("\n");
					}
				}
			}
		} finally {
			if (rs != null)
				rs.close();
			if (s != null)
				s.close();
		}
		rst.trimToSize();
		return rst.toString();
	}

	private void dropTable() throws SQLException {
		Statement s = connection.createStatement();
		try {
			String sql = "drop table " + testTable;
			s.executeUpdate(sql);
		} 
		catch(SQLException e){
			e.printStackTrace();
		}
		finally {
			s.close();
		}
	}

	private void insertData() throws SQLException, UnsupportedEncodingException {
		String sql = "insert into " + testTable
				+ "(charset,testtext) values(?,?)";
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(sql);
			for (String charset : testCharsets) {
				for (String charset2 : testCharsets) {
					ps.setString(1, "insert type:new String(value.getBytes(\""
							+ charset + "\"),\"" + charset2 + "\")");
					if (charset != null && charset2 != null)
						ps.setString(2, new String(testText.getBytes(charset),
								charset2));
					else if (charset2 != null)
						ps.setString(2, new String(testText.getBytes(),
								charset2));
					else
						ps.setString(2, testText);
					ps.addBatch();
				}
			}
			ps.executeBatch();
		} finally {
			ps.close();
		}
	}

	private void createTable() throws SQLException {
		Statement s = connection.createStatement();
		try {
			String sql = "create table " + testTable
					+ "(charset varchar(100),testtext varchar(100))";
			s.executeUpdate(sql);
		} finally {
			s.close();
		}
	}

	public static final void main(String args[]) throws SQLException,
			ClassNotFoundException, UnsupportedEncodingException {
//		String driver = "com.mysql.jdbc.Driver";
//		String url = "jdbc:mysql://localhost:3306/test";
//		String user = "root";
//		String password = "root";
		//com.microsoft.jdbc.sqlserver.SQLServerDriver
		//com.microsoft.sqlserver.jdbc.SQLServerDriver
		String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		String url = "jdbc:sqlserver://localhost:1433;databaseName=dhec_weibo";
		String user = "dhec_weibo";
		String password = "dhec_weibo";
		String[] testCharsets = { null, "GBK", "GB2312", "UTF-8", "ISO-8859-1" };

		DbCharsetChecker dct = new DbCharsetChecker(driver, url, user, password,
				testCharsets);

		System.out.println(dct.runTest());

	}
}
