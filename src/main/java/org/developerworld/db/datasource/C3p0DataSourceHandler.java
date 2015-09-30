package org.developerworld.db.datasource;

import java.beans.PropertyVetoException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * c3P0数据源控制类
 * @author Roy Huang
 * @version 20110105
 * @deprecated 
 * @see org.developerworld.commons.db project
 */
public class C3p0DataSourceHandler extends DataSourceHandler{
	
	protected ComboPooledDataSource comboPooledDataSource;

	public C3p0DataSourceHandler(DataSource dataSource) {
		super(dataSource);
		comboPooledDataSource=(ComboPooledDataSource)dataSource;
	}

	@Override
	public void close() {
		comboPooledDataSource.close();
	}

	@Override
	public void setDriver(String driver) {
		try {
			comboPooledDataSource.setDriverClass(driver);
		} catch (PropertyVetoException e) {
			throw new RuntimeException(e.getMessage(),e);
		}
	}

	@Override
	public void setUrl(String url) {
		comboPooledDataSource.setJdbcUrl(url);
	}

	@Override
	public void setUser(String user) {
		comboPooledDataSource.setUser(user);
	}

	@Override
	public void setPassword(String password) {
		comboPooledDataSource.setPassword(password);
	}

	@Override
	public void setMinPoolSize(int size) {
		comboPooledDataSource.setMinPoolSize(size);
	}

	@Override
	public void setMaxPoolSize(int size) {
		comboPooledDataSource.setMaxPoolSize(size);
	}

}
