package org.developerworld.commons.db.transcode;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;


/**
 * 结果集对象代理
 * 
 * @author Roy Huang
 * @version 20111116
 * @deprecated see org.developerworld.commons.dbtranscode
 */
public class ResultSetProxy implements InvocationHandler {

	private ResultSet resultSet;
	private String appCharset;
	private String dbCharset;
	private final static Set GET_METHODS = new HashSet();
	private final static Set UPDATE_METHODS=new HashSet();
	static {
		UPDATE_METHODS.add("getObject");
		GET_METHODS.add("getString");
		GET_METHODS.add("getClob");
		UPDATE_METHODS.add("updateObject");
		UPDATE_METHODS.add("updateString");
		UPDATE_METHODS.add("updateClob");
	}

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
		this.resultSet = resultSet;
		this.appCharset = appCharset;
		this.dbCharset = dbCharset;
	}

	/**
	 * 返回代理后的对象
	 * 
	 * @return
	 */
	public ResultSet getResultSet() {
		Class[] interfaces = resultSet.getClass().getInterfaces();
		if (interfaces == null || interfaces.length == 0)
			interfaces = new Class[] { ResultSet.class };
		return (ResultSet) Proxy.newProxyInstance(resultSet.getClass()
				.getClassLoader(), interfaces, this);
	}

	/**
	 * 执行代理操作
	 */
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		Object rst = null;
		String methodName = method.getName();
		//获取数据方法
		if (GET_METHODS.contains(methodName))
			return invoteGetMethod(method, args);
		//跟新数据集合方法
		else if(UPDATE_METHODS.contains(methodName))
			return invoteUpdateMethod(method,args);
		//其他方法
		else
			rst=method.invoke(resultSet, args);
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
	 * 执行更新数据的方法
	 * @param method
	 * @param args
	 * @return
	 * @throws UnsupportedEncodingException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws SQLException 
	 */
	private Object invoteUpdateMethod(Method method, Object[] args) throws UnsupportedEncodingException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, SQLException {
		Object data=args[1];
		//updateString,updateObject
		if(data instanceof String)
			data=transcode((String)data, appCharset, dbCharset);
		//updateClob,updateObject
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
		return method.invoke(resultSet, _args);
	}

	/**
	 * 执行获取数据的方法
	 * @param method
	 * @param args
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws SQLException 
	 */
	private Object invoteGetMethod(Method method, Object[] args)
			throws UnsupportedEncodingException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException, SQLException {
		Object rst = method.invoke(resultSet, args);
		//getString,getObject
		if (rst instanceof String)
			rst = transcode((String) rst, dbCharset, appCharset);
		//getClob,getObject
		else if(rst instanceof Clob){
			Clob clobData=(Clob)rst;
			//获取字符串副本
			String strData=clobData.getSubString(1,(int)clobData.length());
			strData=transcode(strData, dbCharset, appCharset);
			//写入新字符串
			clobData.setString(1, strData);
			//截取字符串
			clobData.truncate(strData.length());
		}
		return rst;
	}

}
