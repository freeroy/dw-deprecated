package org.developerworld.commons.db.dynamicdatasource.impl;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.developerworld.commons.db.datasource.handler.DataSourceHandler;
import org.developerworld.commons.db.datasource.handler.DataSourceHandlerFactory;
import org.developerworld.commons.db.dynamicdatasource.DataSourcesFactory;
import org.developerworld.commons.db.dynamicdatasource.DynamicDataSourceHolder;
import org.developerworld.commons.db.dynamicdatasource.DynamicDataSourceManager;

/**
 * 用于分配可使用的源 注意！该类默认不是单例的，因此需要自行进行管理（实现类的时候该为单例模式或利用spring【建议】）
 * 
 * @author Roy Huang
 * @version 20110730
 * @deprecated see org.developerworld.commons.dbsource
 */
public class StandardDynamicDataSourceManager implements
		DynamicDataSourceManager {

	private static Log log = LogFactory
			.getLog(StandardDynamicDataSourceManager.class);

	private Map<String, DataSource> dataSources = new HashMap<String, DataSource>();

	private DataSource defaultDataSource;

	private boolean isInit = false;

	private DataSourcesFactory dataSourcesFactory;

	public void setDataSourcesFactory(DataSourcesFactory dataSourcesFactory) {
		this.dataSourcesFactory = dataSourcesFactory;
	}

	/**
	 * 设置默认数据源
	 * 
	 * @param defaultDataSource
	 */
	public void setDefaultDataSource(DataSource defaultDataSource) {
		this.defaultDataSource = defaultDataSource;
	}

	public DataSource getDataSource() {
		DataSource rst = null;
		String dataSourceKey = DynamicDataSourceHolder.getDataSourceKey();
		if (StringUtils.isBlank(dataSourceKey))
			rst = defaultDataSource;
		else {
			rst = dataSources.get(dataSourceKey);
			if (rst == null) {
				log.error("can not find datasource by data source key \""
						+ dataSourceKey + "\"");
				throw new RuntimeException(
						"can not find datasource by data source key \""
								+ dataSourceKey + "\"");
			}
		}
		return rst;
	}

	synchronized public void init() {
		if (isInit)
			return;
		if (dataSourcesFactory != null) {
			Map<String, DataSource> dataSources = dataSourcesFactory
					.buildDataSources();
			if (dataSources != null && dataSources.size() > 0)
				this.dataSources = dataSources;
			else
				log.info("no datasources has set in dynamic data source!");
		}
		isInit = true;
		log.info("init dynamicDataSource!");
	}

	synchronized public void reload() {
		if (!isInit)
			return;
		destroy();
		init();
		log.info("reload dynamicDataSource!");
	}

	synchronized public void destroy() {
		if (!isInit)
			return;
		for (DataSource dataSource : dataSources.values()) {
			try {
				if (dataSource == null)
					continue;
				DataSourceHandler dsh = DataSourceHandlerFactory
						.buildDataSourceHandler(dataSource);
				dsh.destroy();
			} catch (Exception e) {
				log.error(e);
			}
		}
		dataSources.clear();
		isInit = false;
		log.info("destroy dynamicDataSource!");
	}

	synchronized public void addDataSource(String key, DataSource dataSource) {
		if (dataSources.containsKey(dataSource))
			throw new RuntimeException("the datasource is exists!");
		dataSources.put(key, dataSource);
	}

	synchronized public void removeDataSource(String key) {
		if (!dataSources.containsKey(key))
			throw new RuntimeException("the datasource key is not found!");
		try {
			DataSource dataSource = dataSources.get(key);
			DataSourceHandler handler = DataSourceHandlerFactory
					.buildDataSourceHandler(dataSource);
			handler.destroy();
		} catch (Exception e) {
			log.error(e);
		}
		dataSources.remove(key);
	}

	synchronized public boolean containsDataSourceKey(String key) {
		return dataSources.containsKey(key);
	}

}
