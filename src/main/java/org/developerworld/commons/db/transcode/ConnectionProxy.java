package org.developerworld.commons.db.transcode;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.Statement;

/**
 * 连接对象代理
 * 
 * @author Roy Huang
 * @version 20111116
 * @deprecated see org.developerworld.commons.dbtranscode
 */
public class ConnectionProxy implements InvocationHandler {

	Connection connection;
	private String appCharset;
	private String dbCharset;

	/**
	 * 构造函数
	 * 
	 * @param connection
	 *            数据库连接对象
	 * @param appCharset
	 *            应用编码
	 * @param dbCharset
	 *            数据库编码
	 */
	public ConnectionProxy(Connection connection, String appCharset,
			String dbCharset) {
		this.connection = connection;
		this.appCharset = appCharset;
		this.dbCharset = dbCharset;
	}

	/**
	 * 获取代理后的连接对象
	 * 
	 * @return
	 */
	public Connection getConnection() {
		Class[] interfaces = connection.getClass().getInterfaces();
		if (interfaces == null || interfaces.length == 0)
			interfaces = new Class[] { Connection.class };
		return (Connection) Proxy.newProxyInstance(connection.getClass()
				.getClassLoader(), interfaces, this);
	}

	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		Object rst = method.invoke(connection, args);
		if (rst instanceof Statement)
			rst = new StatementProxy((Statement) rst, appCharset, dbCharset)
					.getStatement();
		return rst;
	}

}
