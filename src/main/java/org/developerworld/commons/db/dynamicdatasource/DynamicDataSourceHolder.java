package org.developerworld.commons.db.dynamicdatasource;

/**
 * 用于管理单一线程中所使用的数据库标识
 * 
 * @author Roy Huang
 * @version 20110105
 * @deprecated see org.developerworld.commons.dbsource
 */
public class DynamicDataSourceHolder {

	private static ThreadLocal<String> dataSourceKey = new ThreadLocal<String>();

	/**
	 * 
	 * @return
	 */
	public static String getDataSourceKey() {
		return dataSourceKey.get();
	}

	/**
	 * 
	 * @param key
	 */
	public static void setDataSourceKey(String key) {
		dataSourceKey.set(key);
	}

	/**
	 * 
	 */
	public static void removeDataSourceKey() {
		dataSourceKey.set(null);
		dataSourceKey.remove();
	}
}
