package org.developerworld.commons.db.datasource.builder.impl;

import java.util.Map;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * @deprecated see org.developerworld.commons.dbsource
 * @author Roy
 *
 */
public class C3p0DataSourceBuilder extends AbstractDataSourceBuilder {

	@Override
	DataSource buildEmptyDataSourceInstance(Map<String, String> params) {
		return new ComboPooledDataSource();
	}

}
