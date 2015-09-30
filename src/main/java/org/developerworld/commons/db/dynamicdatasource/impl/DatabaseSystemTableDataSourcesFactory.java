package org.developerworld.commons.db.dynamicdatasource.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.developerworld.commons.db.datasource.builder.DataSourceBuilder;
import org.developerworld.commons.db.dynamicdatasource.DataSourcesFactory;

/**
 * 基于数据库系统中的表进行配置的数据源工厂
 * 
 * @author Roy Huang
 * @version 20140605
 * @deprecated see org.developerworld.commons.dbsource
 */
public class DatabaseSystemTableDataSourcesFactory implements
		DataSourcesFactory {

	private String dbDriver;
	private String dbUrl;
	private String dbUser;
	private String dbPassword;
	private String configDataSelectSql;
	private DataSourceBuilder dataSourceBuilder;

	public void setDbDriver(String dbDriver) {
		this.dbDriver = dbDriver;
	}

	public void setDbUrl(String dbUrl) {
		this.dbUrl = dbUrl;
	}

	public void setDbUser(String dbUser) {
		this.dbUser = dbUser;
	}

	public void setDbPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}

	public void setConfigSelectSql(String configSelectSql) {
		this.configDataSelectSql = configSelectSql;
	}

	public void setDataSourceBuilder(DataSourceBuilder dataSourceBuilder) {
		this.dataSourceBuilder = dataSourceBuilder;
	}

	public Map<String, DataSource> buildDataSources() {
		Map<String, DataSource> rst = null;
		try {
			// 获取数据库连接
			Connection connection = buildConnection();
			try {
				// 根据配置文件集合，获取配置信息集合
				List<Map<String, String>> configMaps = getConfigMaps(connection);
				// 根据配置信息，构建数据源集合
				rst = buildDataSourceMap(configMaps);
			} finally {
				connection.close();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return rst;
	}

	/**
	 * 根据配置信息，构造配置源
	 * 
	 * @param configMaps
	 * @return
	 */
	private Map<String, DataSource> buildDataSourceMap(
			List<Map<String, String>> configMaps) {
		Map<String, DataSource> rst = new HashMap<String, DataSource>();
		for (Map<String, String> configMap : configMaps) {
			String key = configMap.get(DATA_SOURCE_KEY);
			if (StringUtils.isNotBlank(key)) {
				DataSource dataSource = dataSourceBuilder
						.buildDataSource(configMap);
				if (dataSource != null)
					rst.put(key, dataSource);
			}
		}
		return rst;
	}

	/**
	 * 获取配置信息集合
	 * 
	 * @param connection
	 * @return
	 * @throws SQLException
	 */
	private List<Map<String, String>> getConfigMaps(Connection connection)
			throws SQLException {
		List<Map<String, String>> rst = new ArrayList<Map<String, String>>();
		Statement statement = connection.createStatement();
		try {
			ResultSet resultSet = statement
					.executeQuery(this.configDataSelectSql);
			try {
				ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
				int columnCount = resultSetMetaData.getColumnCount();
				while (resultSet.next()) {
					Map<String, String> configMap = new HashMap<String, String>();
					for (int i = 1; i <= columnCount; i++)
						configMap.put(resultSetMetaData.getColumnLabel(i),
								resultSet.getString(i));
					rst.add(configMap);
				}
			} finally {
				resultSet.close();
			}
		} finally {
			statement.close();
		}
		return rst;
	}

	/**
	 * 创建连接
	 * 
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	private Connection buildConnection() throws ClassNotFoundException,
			SQLException {
		Class.forName(dbDriver);
		return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
	}

}
