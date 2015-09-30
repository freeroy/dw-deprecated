package org.developerworld.tools.search.jdbc;

import org.developerworld.tools.search.collection.MapSortBuilder;

/**
 * jdbc Resultset创建器
 * 
 * @author Roy Huang
 * @version 20120907
 *
 *@deprecated
 *@see org.developerworld.frameworks.lucene project
 */
public class JdbcResultSetSortBuilder extends MapSortBuilder {

	public JdbcResultSetSortBuilder(String column, boolean reverse) {
		super(column, reverse);
	}

}
