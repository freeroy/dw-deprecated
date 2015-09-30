package org.developerworld.commons.db.transcode;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

/**
 * 查询对象代理
 * 
 * @author Roy Huang
 * @version 20111116
 * @deprecated see org.developerworld.commons.dbtranscode
 */
public class StatementProxy implements InvocationHandler {

	private Statement statement;
	private String appCharset;
	private String dbCharset;

	private final static Set EXECUTE_METHODS = new HashSet();
	private final static Set SET_METHODS = new HashSet();
	private final static Set QUERY_METHODS = new HashSet();
	static {
		EXECUTE_METHODS.add("execute");
		EXECUTE_METHODS.add("executeUpdate");
		SET_METHODS.add("setString");
		SET_METHODS.add("setObject");
		SET_METHODS.add("setClob");
		QUERY_METHODS.add("executeQuery");
	}

	/**
	 * execute
	 */

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
		this.statement = statement;
		this.appCharset = appCharset;
		this.dbCharset = dbCharset;
	}

	/**
	 * 获取代理后的对象
	 * 
	 * @return
	 */
	public Statement getStatement() {
		Class[] interfaces = statement.getClass().getInterfaces();
		if (interfaces == null || interfaces.length == 0)
			interfaces = new Class[] { Statement.class };
		return (Statement) Proxy.newProxyInstance(statement.getClass()
				.getClassLoader(), interfaces, this);
	}

	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		Object rst = null;
		String methodName = method.getName();
		// 直接执行sql的方法
		if (EXECUTE_METHODS.contains(methodName)) {
			// 提取第一个参数（SQL语句），对其进行编码转换
			String sql = (String) args[0];
			sql = transcode(sql, appCharset, dbCharset);
			Object[] _args = new Object[args.length];
			_args[0] = sql;
			for (int i = 1; i < _args.length; i++)
				_args[i] = args[i];
			// 执行原方法
			rst = method.invoke(statement, _args);
		}
		// 执行子类的set***方法
		else if (SET_METHODS.contains(methodName))
			rst = invokeSetMethod(method, args);
		//执行executeQuery方法
		else if (QUERY_METHODS.contains(methodName)) {
			ResultSet resultSet = (ResultSet) method.invoke(statement, args);
			rst = new ResultSetProxy(resultSet, appCharset, dbCharset)
					.getResultSet();
		}
		// 其他方法
		else
			rst = method.invoke(statement, args);
		return rst;
	}
	
	private String transcode(String str, String origCharset,
			String destCharset) throws UnsupportedEncodingException {
		String rst = str;
		if (StringUtils.isNotBlank(rst)
				&& (StringUtils.isNotBlank(origCharset) || StringUtils.isNotBlank(destCharset))) {
			byte[] bytes = null;
			if (StringUtils.isNotBlank(origCharset))
				bytes = rst.getBytes(origCharset);
			else
				bytes = rst.getBytes();
			if (StringUtils.isNotBlank(destCharset))
				rst = new String(bytes, destCharset);
			else
				rst = new String(bytes);
		}
		return rst;
	}

	/**
	 * 处理不同的setXXX方法
	 * 
	 * @param method
	 * @param args
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws SQLException 
	 */
	private Object invokeSetMethod(Method method, Object[] args)
			throws UnsupportedEncodingException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException, SQLException {
		Object data = args[1];
		// setString,setObject;
		if (data instanceof String)
			data = transcode((String) data, appCharset,
					dbCharset);
		// setClob,setObject
		else if(data instanceof Clob){
			Clob clobData=(Clob)data;
			//获取字符串副本
			String strData=clobData.getSubString(1,(int)clobData.length());
			strData=transcode(strData, appCharset, dbCharset);
			//写入新字符串
			clobData.setString(1, strData);
			//截取字符串
			clobData.truncate(strData.length());
		}
		//重新设置参数
		Object[] _args = new Object[args.length];
		_args[0] = args[0];
		_args[1] = data;
		for (int i = 2; i < _args.length; i++)
			_args[i] = args[i];
		// 执行原方法
		return method.invoke(statement, _args);
	}

}
