package org.developerworld.commons.db.dynamicdatasource;

import java.util.Map;

import javax.sql.DataSource;

/**
 * 构建数据源
 * @author Roy Huang
 * @version 20140605
 * @deprecated see org.developerworld.commons.dbsource
 */
public interface DataSourcesFactory {
	
	public static final String DATA_SOURCE_KEY = "dataSourceKey";

	/**
	 * 构建数据源
	 * @return
	 */
	public Map<String,DataSource> buildDataSources();
	
}
