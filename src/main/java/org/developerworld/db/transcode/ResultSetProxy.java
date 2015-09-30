package org.developerworld.db.transcode;

import java.sql.ResultSet;

/**
 * 结果集对象代理
 * 
 * @author Roy Huang
 * @version 20111116
 * @deprecated 
 * @see org.developerworld.commons.db project
 */
public class ResultSetProxy extends org.developerworld.commons.db.transcode.ResultSetProxy{

	/**
	 * 构造函数
	 * 
	 * @param resultSet
	 *            数据结果集
	 * @param appCharset
	 *            应用编码
	 * @param dbCharset
	 *            数据库编码
	 */
	public ResultSetProxy(ResultSet resultSet, String appCharset,
			String dbCharset) {
		super(resultSet, appCharset, dbCharset);
	}

}
