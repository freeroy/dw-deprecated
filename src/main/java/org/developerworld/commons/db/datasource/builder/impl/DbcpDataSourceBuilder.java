package org.developerworld.commons.db.datasource.builder.impl;

import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;

/**
 * @deprecated see org.developerworld.commons.dbsource
 * @author Roy
 *
 */
public class DbcpDataSourceBuilder extends AbstractDataSourceBuilder {

	@Override
	DataSource buildEmptyDataSourceInstance(Map<String, String> params) {
		return new BasicDataSource();
	}

}
