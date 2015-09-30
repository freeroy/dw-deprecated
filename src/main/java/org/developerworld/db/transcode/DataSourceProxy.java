package org.developerworld.db.transcode;

import javax.sql.DataSource;

/**
 * 数据源代理
 * 
 * @author Roy Huang
 * @version 20111116
 * @deprecated 
 * @see org.developerworld.commons.db project
 */
public class DataSourceProxy extends org.developerworld.commons.db.transcode.DataSourceProxy {

	/**
	 * 构造函数
	 * 
	 * @param dataSource
	 *            数据源对象
	 * @param appCharset
	 *            应用编码
	 * @param dbCharset
	 *            数据库编码
	 */
	public DataSourceProxy(DataSource dataSource, String appCharset,
			String dbCharset) {
		super(dataSource, appCharset, dbCharset);
	}

}
