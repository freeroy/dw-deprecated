package org.developerworld.commons.db.datasource.handler.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.logicalcobwebs.proxool.ProxoolDataSource;

/**
 * proxool数据源处理器
 * 
 * @author Roy Huang
 * @version 20130728
 * @deprecated see org.developerworld.commons.dbsource
 */
public class ProxoolDataSourceHandler extends
		AbstractDataSourceHandler<ProxoolDataSource> {
	
	private final static Log log=LogFactory.getLog(ProxoolDataSourceHandler.class);

	public ProxoolDataSourceHandler(ProxoolDataSource dataSource) {
		super(dataSource);
	}

	public void destroy() {
	}

	public void setDbDriver(String driver) {
		getDataSource().setDriver(driver);
	}

	public void setDbUrl(String url) {
		getDataSource().setDriverUrl(url);
	}

	public void setDbUser(String user) {
		getDataSource().setUser(user);
	}

	public void setDbPassword(String password) {
		getDataSource().setPassword(password);
	}

	public void setDataSourceMinPoolSize(int size) {
		getDataSource().setMinimumConnectionCount(size);
	}

	public void setDataSourceMaxPoolSize(int size) {
		getDataSource().setMaximumConnectionCount(size);
	}

	public void setDataSourceInitPoolSize(int initPoolSize) {
		log.warn("unsupport initPoolSize arg for Proxool Data Source!");
	}

	public void setDataSourceMinIdleSize(int minIdle) {
		getDataSource().setPrototypeCount(minIdle);
	}

	public void setAcquireIncrement(int acquireIncrement) {
		log.warn("unsupport acquireIncrement arg for Proxool Data Source!");
	}

	public void setMaxIdleTime(int maxIdleTime) {
		log.warn("unsupport maxIdleTime arg for Proxool Data Source!");
	}
}