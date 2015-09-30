package org.developerworld.tools.search.jdbc;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.developerworld.tools.search.DataEach;

/**
 * JDBC ResultSet数据遍历器
 * 
 * @author Roy Huang
 * @version 20120907
 * 
 *@deprecated
 *@see org.developerworld.frameworks.lucene project
 */
public class JdbcResultSetDataEach implements DataEach<Map<String,String>> {

	private final static Log log = LogFactory
			.getLog(JdbcResultSetDataEach.class);

	private ResultSet resultSet;
	private ResultSetMetaData resultSetMetaData;
	private int columnCount;

	public JdbcResultSetDataEach(ResultSet resultSet) throws SQLException {
		this.resultSet = resultSet;
		resultSetMetaData = resultSet.getMetaData();
		columnCount = resultSetMetaData.getColumnCount();
	}

	public void each(DataIndex<Map<String,String>> dataIndex) throws Exception {
		while (resultSet.next()) {
			try {
				dataIndex.index(resultSetToMap(resultSet));
			} catch (IOException e) {
				log.error(e);
			}
		}
	}

	/**
	 * 记录集转换map
	 * 
	 * @param resultSet
	 * @return
	 * @throws SQLException
	 */
	private Map<String, String> resultSetToMap(ResultSet resultSet)
			throws SQLException {
		Map<String, String> rst = new HashMap<String, String>();
		for (int i = 1; i < columnCount; i++) {
			rst.put(resultSetMetaData.getColumnName(i),
					String.valueOf(resultSet.getObject(i)));
		}
		return rst;
	}

}
