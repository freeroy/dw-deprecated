package org.developerworld.commons.db.datasource.handler.impl;

import java.util.Iterator;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.developerworld.commons.db.datasource.handler.DataSourceHandler;

/**
 * 抽象数据源处理器
 * 
 * @author Roy Huang
 * @version 20130728
 * 
 * @param <T>
 * @deprecated see org.developerworld.commons.dbsource
 */
public abstract class AbstractDataSourceHandler<T extends DataSource>
		implements DataSourceHandler<T> {

	private final static Log log = LogFactory
			.getLog(AbstractDataSourceHandler.class);

	private T dataSource;

	public AbstractDataSourceHandler(T dataSource) {
		this.dataSource = dataSource;
	}

	public T getDataSource() {
		return this.dataSource;
	}

	public void setParameters(Map<String, Object> parameters) {
		if (parameters != null) {
			Iterator<Map.Entry<String, Object>> i = parameters.entrySet()
					.iterator();
			while (i.hasNext()) {
				Map.Entry<String, Object> e = i.next();
				setParameter((String) e.getKey(), e.getValue());
			}
		}
	}

	public void setParameter(String name, Object value) {
		try {
			BeanUtils.setProperty(this.dataSource, name, value);
		} catch (Exception e) {
			log.warn("have not property \"" + name + "\" for datasource class:"
					+ this.dataSource.getClass());
			log.error(e);
		}
	}
}