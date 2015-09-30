package org.developerworld.commons.db.datasource.handler;

import java.util.Map;
import javax.sql.DataSource;

/**
 * 数据源处理器
 * 
 * @author Roy Huang
 * @version 20130728
 * 
 * @param <T>
 * @deprecated see org.developerworld.commons.dbsource
 */
public abstract interface DataSourceHandler<T extends DataSource> {

	/**
	 * 获取数据源
	 * 
	 * @return
	 */
	public T getDataSource();

	/**
	 * 设置数据源参数
	 * 
	 * @param paramMap
	 */
	public void setParameters(Map<String, Object> paramMap);

	/**
	 * 设置数据源参数
	 * 
	 * @param paramString
	 * @param paramObject
	 */
	public void setParameter(String paramString, Object paramObject);

	/**
	 * 销毁数据源
	 */
	public void destroy();

	/**
	 * 设置数据源驱动
	 * 
	 * @param driver
	 */
	public void setDbDriver(String driver);

	/**
	 * 设置数据源连接地址
	 * 
	 * @param url
	 */
	public void setDbUrl(String url);

	/**
	 * 设置数据库用户 名
	 * 
	 * @param user
	 */
	public void setDbUser(String user);

	/**
	 * 设置数据库密码
	 * 
	 * @param password
	 */
	public void setDbPassword(String password);

	/**
	 * 设置最小连接数
	 * 
	 * @param minPoolSize
	 */
	public void setDataSourceMinPoolSize(int minPoolSize);

	/**
	 * 设置最大连接数
	 * 
	 * @param maxPoolSize
	 */
	public void setDataSourceMaxPoolSize(int maxPoolSize);
	
	/**
	 * 设置初始连接数
	 * @param initPoolSize
	 */
	public void setDataSourceInitPoolSize(int initPoolSize);
	
	/**
	 * 最少空闲个数
	 * @param minIdle
	 */
	public void setDataSourceMinIdleSize(int minIdle);
	
	/**
	 * 设置增量获取连接数大小
	 * @param acquireIncrement
	 */
	public void setAcquireIncrement(int acquireIncrement);
	
	/**
	 * 设置连接空闲最大时间
	 * @param maxIdleTime
	 */
	public void setMaxIdleTime(int maxIdleTime);
}