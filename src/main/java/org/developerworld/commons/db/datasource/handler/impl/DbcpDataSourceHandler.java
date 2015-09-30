package org.developerworld.commons.db.datasource.handler.impl;

import java.sql.SQLException;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * DBCP数据源处理器
 * 
 * @author Roy
 * @deprecated see org.developerworld.commons.dbsource
 */
public class DbcpDataSourceHandler extends
		AbstractDataSourceHandler<BasicDataSource> {
	
	private final static Log log=LogFactory.getLog(DbcpDataSourceHandler.class);

	public DbcpDataSourceHandler(BasicDataSource dataSource) {
		super(dataSource);
	}

	public void destroy() {
		try {
			getDataSource().close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void setDbDriver(String driver) {
		getDataSource().setDriverClassName(driver);
	}

	public void setDbUrl(String url) {
		getDataSource().setUrl(url);
	}

	public void setDbUser(String user) {
		getDataSource().setUsername(user);
	}

	public void setDbPassword(String password) {
		getDataSource().setPassword(password);
	}

	public void setDataSourceMinPoolSize(int size) {
		log.warn("unsupport initPoolSize arg for DBCP Data Source!");
	}

	public void setDataSourceMaxPoolSize(int size) {
		getDataSource().setMaxActive(size);
	}

	public void setDataSourceInitPoolSize(int initPoolSize) {
		getDataSource().setInitialSize(initPoolSize);
	}

	public void setDataSourceMinIdleSize(int minIdle) {
		getDataSource().setMinIdle(minIdle);
	}

	public void setAcquireIncrement(int acquireIncrement) {
		log.warn("unsupport acquireIncrement arg for DBCP Data Source!");
	}

	public void setMaxIdleTime(int maxIdleTime) {
		getDataSource().setMinEvictableIdleTimeMillis(maxIdleTime);
	}
}