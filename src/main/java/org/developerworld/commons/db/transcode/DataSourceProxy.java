package org.developerworld.commons.db.transcode;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;

import javax.sql.DataSource;

/**
 * 数据源代理
 * 
 * @author Roy Huang
 * @version 20111116
 * @deprecated see org.developerworld.commons.dbtranscode
 */
public class DataSourceProxy implements InvocationHandler {

	private DataSource dataSource;
	private String appCharset;
	private String dbCharset;
	private final static String CONNECTION_METHOD="getConnection";

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
		this.dataSource = dataSource;
		this.appCharset = appCharset;
		this.dbCharset = dbCharset;
	}

	/**
	 * 获取数据源
	 * @return
	 */
	public DataSource getDataSource() {
		Class[] interfaces = dataSource.getClass().getInterfaces();
		if (interfaces == null || interfaces.length == 0)
			interfaces = new Class[] { DataSource.class };
		return (DataSource) Proxy.newProxyInstance(dataSource.getClass()
				.getClassLoader(), interfaces, this);
	}

	/**
	 * 执行代理操作
	 */
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		Object rst=null;
		String methodName=method.getName();
		if(CONNECTION_METHOD.equals(methodName)){
			rst=method.invoke(dataSource, args);
			rst=new ConnectionProxy((Connection)rst, appCharset, dbCharset).getConnection();
		}
		else
			rst=method.invoke(dataSource, args);
		return rst;
	}

}
