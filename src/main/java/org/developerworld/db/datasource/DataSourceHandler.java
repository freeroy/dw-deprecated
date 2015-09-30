package org.developerworld.db.datasource;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.sql.DataSource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * 抽象连接池控制类
 * 
 * @author Roy Huang
 * @version 20110105
 * @deprecated 
 * @see org.developerworld.commons.db project
 */
public abstract class DataSourceHandler {

	public final static String DRIVER = "driver";
	public final static String URL = "url";
	public final static String USER = "user";
	public final static String PASSWORD = "password";

	private static Log log = LogFactory.getLog(DataSourceHandler.class);

	private static Map<Class<? extends DataSource>, Class<? extends DataSourceHandler>> handlers = new HashMap<Class<? extends DataSource>, Class<? extends DataSourceHandler>>();

	static {
		// c3p0
		handlers.put(ComboPooledDataSource.class, C3p0DataSourceHandler.class);
	}

	protected DataSource dataSource;

	public DataSourceHandler(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	/**
	 * 获取数据池
	 * @return
	 */
	public DataSource getDataSource(){
		return dataSource;
	}

	/**
	 * 创建数据源控制器
	 * @param dataSource
	 * @return
	 */
	public static DataSourceHandler buildDataSourceHandler(DataSource dataSource) {
		Class<? extends DataSourceHandler> handlerClass=null;
		//若有完全相等的，就直接获取对应映射对象
		if (handlers.containsKey(dataSource.getClass()))
			handlerClass=handlers.get(dataSource.getClass());
		else{
			//遍历寻找
			for(Class<? extends DataSource> _class:handlers.keySet()){
				if(_class.isInstance(dataSource)){
					handlerClass=handlers.get(_class);
					break;
				}
			}
		}
		if(handlerClass==null)
			throw new RuntimeException("Unknow DataSourceHandler Exception!");
		try {
			return handlerClass.getDeclaredConstructor(DataSource.class)
					.newInstance(dataSource);
		} catch (Throwable t) {
			log.error(t);
			throw new RuntimeException("unknow exception:" + t.getMessage());
		}
	}

	/**
	 * 设置参数
	 * @param parameters
	 */
	public void setParameters(Map<String, Object> parameters) {
		if (parameters != null) {
			Iterator<Entry<String, Object>> i = parameters.entrySet()
					.iterator();
			while (i.hasNext()) {
				Entry<String, Object> e = i.next();
				setParameter(e.getKey(), e.getValue());
			}
		}
	}

	/**
	 * 设置参数
	 * @param name
	 * @param value
	 */
	public void setParameter(String name, Object value) {
		try {
			BeanUtils.setProperty(dataSource, name, value);
		} catch (Throwable t) {
			log.error(t);
		}
	}

	/**
	 * 关闭数据源
	 */
	abstract public void close();

	/**
	 * 设置驱动
	 * @param driver
	 */
	abstract public void setDriver(String driver);

	/**
	 * 设置连接
	 * @param url
	 */
	abstract public void setUrl(String url);

	/**
	 * 设置帐号
	 * @param user
	 */
	abstract public void setUser(String user);

	/**
	 * 设置密码
	 * @param password
	 */
	abstract public void setPassword(String password);

	/**
	 * 设置最小连接数
	 * @param size
	 */
	abstract public void setMinPoolSize(int size);

	/**
	 * 设置最大连接数
	 * @param size
	 */
	abstract public void setMaxPoolSize(int size);
}
