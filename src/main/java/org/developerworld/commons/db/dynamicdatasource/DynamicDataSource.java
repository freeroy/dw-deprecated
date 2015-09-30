package org.developerworld.commons.db.dynamicdatasource;

import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 动态数据源适配器
 * 
 * @author Roy Huang
 * @version 20130624
 * @deprecated see org.developerworld.commons.dbsource
 */
public class DynamicDataSource implements DataSource {

	private static Log log = LogFactory.getLog(DynamicDataSource.class);
	
	private DynamicDataSourceManager dynamicDataSourceManager;
	
	public DynamicDataSource(DynamicDataSourceManager dynamicDataSourceManager){
		this.dynamicDataSourceManager=dynamicDataSourceManager;
	}

	/**
	 * 获取连接源
	 * 
	 * @return
	 */
	private DataSource getDataSource() {
		return dynamicDataSourceManager.getDataSource();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.sql.CommonDataSource#getLogWriter()
	 */
	public PrintWriter getLogWriter() throws SQLException {
		return getDataSource().getLogWriter();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.sql.CommonDataSource#setLogWriter(java.io.PrintWriter)
	 */
	public void setLogWriter(PrintWriter out) throws SQLException {
		getDataSource().setLogWriter(out);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.sql.CommonDataSource#setLoginTimeout(int)
	 */
	public void setLoginTimeout(int seconds) throws SQLException {
		getDataSource().setLoginTimeout(seconds);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.sql.CommonDataSource#getLoginTimeout()
	 */
	public int getLoginTimeout() throws SQLException {
		return getDataSource().getLoginTimeout();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.sql.DataSource#getConnection()
	 */
	public Connection getConnection() throws SQLException {
		return getDataSource().getConnection();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.sql.DataSource#getConnection(java.lang.String,
	 * java.lang.String)
	 */
	public Connection getConnection(String username, String password)
			throws SQLException {
		return getDataSource().getConnection(username, password);
	}

	/* 以下方法，在jdk1.6后新增，为避免与1.5版本方法存在冲突，所以使用反射方法执行 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.Wrapper#unwrap(java.lang.Class)
	 */
	public <T> T unwrap(Class<T> iface) throws SQLException {
		try {
			Method method = DataSource.class.getMethod("unwrap",
					iface.getClasses());
			return (T) method.invoke(getDataSource(), iface);
		} catch (SecurityException e) {
			log.error(e);
		} catch (NoSuchMethodException e) {
			log.error(e);
		} catch (IllegalArgumentException e) {
			log.error(e);
		} catch (IllegalAccessException e) {
			log.error(e);
		} catch (InvocationTargetException e) {
			log.error(e);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.Wrapper#isWrapperFor(java.lang.Class)
	 */
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		try {
			Method method = DataSource.class.getMethod("isWrapperFor",
					iface.getClasses());
			return (Boolean) method.invoke(getDataSource(), iface);
		} catch (SecurityException e) {
			log.error(e);
		} catch (NoSuchMethodException e) {
			log.error(e);
		} catch (IllegalArgumentException e) {
			log.error(e);
		} catch (IllegalAccessException e) {
			log.error(e);
		} catch (InvocationTargetException e) {
			log.error(e);
		}
		return false;
	}
}
