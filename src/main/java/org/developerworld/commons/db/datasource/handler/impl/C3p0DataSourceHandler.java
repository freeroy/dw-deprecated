package org.developerworld.commons.db.datasource.handler.impl;

import java.beans.PropertyVetoException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * C3P0数据源处理器
 * 
 * @author Roy Huang
 * @version 20130728
 * @deprecated see org.developerworld.commons.dbsource
 */
public class C3p0DataSourceHandler extends
		AbstractDataSourceHandler<ComboPooledDataSource> {
	
	private final static Log log=LogFactory.getLog(C3p0DataSourceHandler.class);

	public C3p0DataSourceHandler(ComboPooledDataSource dataSource) {
		super(dataSource);
	}

	public void destroy() {
		getDataSource().close();
	}

	public void setDbDriver(String driver) {
		try {
			getDataSource().setDriverClass(driver);
		} catch (PropertyVetoException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public void setDbUrl(String url) {
		getDataSource().setJdbcUrl(url);
	}

	public void setDbUser(String user) {
		getDataSource().setUser(user);
	}

	public void setDbPassword(String password) {
		getDataSource().setPassword(password);
	}

	public void setDataSourceMinPoolSize(int size) {
		getDataSource().setMinPoolSize(size);
	}

	public void setDataSourceMaxPoolSize(int size) {
		getDataSource().setMaxPoolSize(size);
	}

	public void setDataSourceInitPoolSize(int initPoolSize) {
		getDataSource().setInitialPoolSize(initPoolSize);
	}

	public void setDataSourceMinIdleSize(int minIdle) {
		log.warn("unsupport minIdleSize arg for C3p0 Data Source!");
	}

	public void setAcquireIncrement(int acquireIncrement) {
		getDataSource().setAcquireIncrement(acquireIncrement);
	}

	public void setMaxIdleTime(int maxIdleTime) {
		getDataSource().setMaxIdleTime(maxIdleTime);
	}
}