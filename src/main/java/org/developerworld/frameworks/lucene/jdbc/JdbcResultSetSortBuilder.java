package org.developerworld.frameworks.lucene.jdbc;

import org.developerworld.frameworks.lucene.collection.MapSortBuilder;

/**
 * jdbc Resultset创建器
 * 
 * @author Roy Huang
 * @version 20120907
 *@deprecated see org.developerworld.frameworks.luncene.impl
 */
public class JdbcResultSetSortBuilder extends MapSortBuilder {

	public JdbcResultSetSortBuilder(String column, boolean reverse) {
		super(column, reverse);
	}

}
