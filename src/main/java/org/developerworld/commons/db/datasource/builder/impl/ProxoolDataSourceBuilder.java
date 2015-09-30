package org.developerworld.commons.db.datasource.builder.impl;

import java.util.Map;

import javax.sql.DataSource;

import org.logicalcobwebs.proxool.ProxoolDataSource;

/**
 * @deprecated see org.developerworld.commons.dbsource
 * @author Roy
 *
 */
public class ProxoolDataSourceBuilder extends AbstractDataSourceBuilder {

	@Override
	DataSource buildEmptyDataSourceInstance(Map<String, String> params) {
		return new ProxoolDataSource();
	}

}
