package org.developerworld.db.transcode;

import java.sql.Connection;

/**
 * 连接对象代理
 * @author Roy Huang
 * @version 20111116
 * @deprecated 
 * @see org.developerworld.commons.db project
 */
public class ConnectionProxy extends org.developerworld.commons.db.transcode.ConnectionProxy {


	/**
	 * 构造函数
	 * @param connection 数据库连接对象
	 * @param appCharset 应用编码
	 * @param dbCharset 数据库编码
	 */
	public ConnectionProxy(Connection connection,String appCharset,String dbCharset) {
		super(connection, appCharset, dbCharset);
	}

}
