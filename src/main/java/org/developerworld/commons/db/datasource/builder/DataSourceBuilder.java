package org.developerworld.commons.db.datasource.builder;

import java.util.Map;

import javax.sql.DataSource;


/**
 * 用于构建数据源的构建器
 * @author Roy Huang
 *@deprecated see org.developerworld.commons.dbsource
 */
public interface DataSourceBuilder {
	
	/**
	 * 构建数据源
	 * @param params
	 * @return
	 */
	public DataSource buildDataSource(Map<String,String> params);
}
