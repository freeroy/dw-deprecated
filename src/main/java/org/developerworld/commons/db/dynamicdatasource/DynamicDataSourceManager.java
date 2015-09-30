package org.developerworld.commons.db.dynamicdatasource;

import javax.sql.DataSource;

/**
 * 动态数据源管理器
 * @author Roy Huang
 * @version 20140605
 *@deprecated see org.developerworld.commons.dbsource
 */
public interface DynamicDataSourceManager {
	
	/**
	 * 获取连接源
	 * 
	 * @return
	 */
	public DataSource getDataSource() ;

	/**
	 * 初始化执行，加载配置文件
	 */
	public void init();

	/**
	 * 刷新加载配置
	 */
	public void reload() ;

	/**
	 * 释放所有bean的资源
	 */
	public void destroy();
	
	/**
	 * 增加数据源
	 * @param key
	 * @param dataSource
	 */
	public void addDataSource(String key,DataSource dataSource);
	
	/**
	 * 删除数据源
	 * @param key
	 * @param dataSource
	 */
	public void removeDataSource(String key);
	
	/**
	 * 判断是否存在指定数据源
	 * @param key
	 * @return
	 */
	public boolean containsDataSourceKey(String key);
}
