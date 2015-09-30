package org.developerworld.commons.db.datasource.builder.impl;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.sql.DataSource;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.developerworld.commons.db.datasource.builder.DataSourceBuilder;
import org.developerworld.commons.db.datasource.handler.DataSourceHandler;
import org.developerworld.commons.db.datasource.handler.DataSourceHandlerFactory;

/**
 * 抽象数据源创建器
 * 
 * @author Roy Huang
 * @deprecated see org.developerworld.commons.dbsource
 */
public abstract class AbstractDataSourceBuilder implements DataSourceBuilder {

	private final static Log log = LogFactory
			.getLog(AbstractDataSourceBuilder.class);

	/**
	 * 创建空白数据源实例
	 * 
	 * @param params
	 * @return
	 */
	abstract DataSource buildEmptyDataSourceInstance(Map<String, String> params);

	public DataSource buildDataSource(Map<String, String> params) {
		DataSource rst = buildEmptyDataSourceInstance(params);
		DataSourceHandler dataSourceHandler = DataSourceHandlerFactory
				.buildDataSourceHandler(rst);
		Iterator<Entry<String, String>> iterator = params.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, String> entry = iterator.next();
			// 尝试在handler设置参数
			if (!setProperty(dataSourceHandler, entry.getKey(),
					entry.getValue())) {
				// 尝试在datasource设置残数
				if (!setProperty(rst, entry.getKey(), entry.getValue()))
					log.warn("can not set property \"" + entry.getKey()
							+ "\" in dataSourceHandler("
							+ dataSourceHandler.getClass() + ") or dataSource("
							+ rst.getClass() + ")");
			}
		}
		return rst;
	}

	/**
	 * 设置参数值
	 * 
	 * @param object
	 * @param name
	 * @param value
	 * @return
	 */
	private boolean setProperty(Object object, String name, String value) {
		boolean rst = false;
		try {
			if (PropertyUtils.isWriteable(object, name)) {
				// 获取参数类型
				Class[] types = PropertyUtils
						.getPropertyDescriptor(object, name).getWriteMethod()
						.getParameterTypes();
				if (types[0].equals(byte.class)
						|| types[0].equals(Byte.class))
					PropertyUtils
							.setProperty(object, name, Byte.valueOf(value));
				else if (types[0].equals(short.class)
						|| types[0].equals(Short.class))
					PropertyUtils.setProperty(object, name,
							Short.valueOf(value));
				else if (types[0].equals(int.class)
						|| types[0].equals(Integer.class))
					PropertyUtils.setProperty(object, name,
							Integer.valueOf(value));
				else if (types[0].equals(long.class)
						|| types[0].equals(Long.class))
					PropertyUtils
							.setProperty(object, name, Long.valueOf(value));
				else if (types[0].equals(float.class)
						|| types[0].equals(Float.class))
					PropertyUtils.setProperty(object, name,
							Float.valueOf(value));
				else if (types[0].equals(double.class)
						|| types[0].equals(Double.class))
					PropertyUtils.setProperty(object, name,
							Double.valueOf(value));
				else if (types[0].equals(boolean.class)
						|| types[0].equals(Boolean.class))
					PropertyUtils.setProperty(object, name,
							Boolean.valueOf(value));
				else
					PropertyUtils.setProperty(object, name, value);
				rst = true;
			}
		} catch (Exception e) {

		}
		return rst;
	}

}
