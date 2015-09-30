package org.developerworld.db.transcode;

import java.sql.Statement;

/**
 * 查询对象代理
 * 
 * @author Roy Huang
 * @version 20111116
 * @deprecated 
 * @see org.developerworld.commons.db project
 */
public class StatementProxy extends org.developerworld.commons.db.transcode.StatementProxy {



	/**
	 * 构造函数
	 * 
	 * @param statement
	 *            数据库操作源对象
	 * @param appCharset
	 *            应用编码
	 * @param dbCharset
	 *            数据库编码
	 */
	public StatementProxy(Statement statement, String appCharset,
			String dbCharset) {
		super(statement, appCharset, dbCharset);
	}

}
