package org.developerworld.frameworks.hibernate3.support.dynamicdatasource.cache;

import org.hibernate.cache.QueryResultsRegion;

/**
 * 动态数据源查询缓存
 * 
 * @author Roy Huang
 * @version 20111011
 * @deprecated see org.developerworld.frameworks.hibernate3.dbsource
 */
public class DynamicDataSourceQueryResultsRegion extends
		DynamicDataSourceGeneralDataRegion implements QueryResultsRegion {

	private DynamicDataSourceQueryResultsRegion(
			QueryResultsRegion queryResultsRegion) {
		super(queryResultsRegion);
	}

	public static QueryResultsRegion wrap(
			QueryResultsRegion buildQueryResultsRegion) {
		if (!(buildQueryResultsRegion instanceof DynamicDataSourceQueryResultsRegion))
			buildQueryResultsRegion = new DynamicDataSourceQueryResultsRegion(
					buildQueryResultsRegion);
		return buildQueryResultsRegion;
	}

}
