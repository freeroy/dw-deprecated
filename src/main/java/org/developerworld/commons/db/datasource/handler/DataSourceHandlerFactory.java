package org.developerworld.commons.db.datasource.handler;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.logicalcobwebs.proxool.ProxoolDataSource;
import com.mchange.v2.c3p0.ComboPooledDataSource;

import org.developerworld.commons.db.datasource.handler.impl.C3p0DataSourceHandler;
import org.developerworld.commons.db.datasource.handler.impl.DbcpDataSourceHandler;
import org.developerworld.commons.db.datasource.handler.impl.ProxoolDataSourceHandler;

/**
 * 数据源处理器工厂
 * 
 * @author Roy Huang
 * @version 20130728
 * @deprecated see org.developerworld.commons.dbsource
 */
public class DataSourceHandlerFactory {
	
	private static Map<Class<? extends DataSource>, Class<? extends DataSourceHandler>> handlers = new HashMap<Class<? extends DataSource>, Class<? extends DataSourceHandler>>();

	static {
		handlers.put(ComboPooledDataSource.class, C3p0DataSourceHandler.class);
		handlers.put(BasicDataSource.class, DbcpDataSourceHandler.class);
		handlers.put(ProxoolDataSource.class, ProxoolDataSourceHandler.class);
	}

	/**
	 * 创建数据源处理器
	 * 
	 * @param dataSource
	 * @return
	 */
	public static DataSourceHandler<? extends DataSource> buildDataSourceHandler(
			DataSource dataSource) {
		Class<? extends DataSourceHandler<? extends DataSource>> handlerClass = null;
		if (handlers.containsKey(dataSource.getClass())) {
			handlerClass = (Class) handlers.get(dataSource.getClass());
		} else {
			for (Class _class : handlers.keySet()) {
				if (_class.isInstance(dataSource)) {
					handlerClass = (Class) handlers.get(_class);
					break;
				}
			}
		}
		if (handlerClass == null)
			throw new RuntimeException("Unknow DataSourceHandler Exception!");
		try {
			return (DataSourceHandler) handlerClass.getDeclaredConstructor(
					new Class[] { dataSource.getClass()}).newInstance(
					new Object[] { dataSource });
		} catch (Throwable t) {
			throw new RuntimeException("unknow exception:" + t.getMessage());
		}
	}
}