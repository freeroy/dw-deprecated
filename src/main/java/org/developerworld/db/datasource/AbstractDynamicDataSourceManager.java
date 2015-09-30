package org.developerworld.db.datasource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 用于分配可使用的源 注意！该类默认不是单例的，因此需要自行进行管理（实现类的时候该为单例模式或利用spring【建议】）
 * 
 * @author Roy Huang
 * @version 20110730
 * @deprecated 
 * @see org.developerworld.commons.db project
 */
public abstract class AbstractDynamicDataSourceManager {

	private static Log log = LogFactory
			.getLog(AbstractDynamicDataSourceManager.class);

	public static final String KEY = "key";

	private boolean lazyInit = false;// 是否延迟加载数据源

	protected Map<String, DataSource> dataSources = new HashMap<String, DataSource>();

	private List<String> configFiles = new ArrayList<String>();

	private List<Properties> properties = new ArrayList<Properties>();
	
	private DataSource defaultDataSource;

	/**
	 * 获取标准数据源
	 * 
	 * @return
	 */
	abstract public DataSource createDataSource();
	
	public void setDefaultDataSource(DataSource defaultDataSource) {
		this.defaultDataSource = defaultDataSource;
	}

	public void setConfigFiles(List<String> configFiles) {
		this.configFiles = configFiles;
	}
	
	public void setLazyInit(boolean lazyInit) {
		this.lazyInit = lazyInit;
	}
	
	/**
	 * 获取连接源
	 * 
	 * @return
	 */
	public DataSource getDataSource() {
		DataSource rst = defaultDataSource;
		String dataSourceKey = DynamicDataSourceHolder.getDataSourceKey();
		log.debug("the DynamicDataSourceHolder.getDataSourceKey is " + dataSourceKey);
		DataSource tmp=getDataSource(dataSourceKey);
		if(tmp!=null)
			rst=tmp;
		log.debug("return dataSource is " + rst);
		return rst;
	}

	/**
	 * 根据key获取指定数据源
	 * 
	 * @param dataSourceKey
	 * @return
	 */
	private DataSource getDataSource(String dataSourceKey) {
		// 若数据源没初始化，就执行初始化
		if (dataSources.containsKey(dataSourceKey)
				&& dataSources.get(dataSourceKey) == null) {
			synchronized (dataSources) {
				synchronized (properties) {
					if (dataSources.get(dataSourceKey) == null) {
						for (Properties p : properties) {
							String key = p.getProperty(KEY, null);
							if (dataSourceKey.equals(key)) {
								dataSources.put(key,
										buildDataSourceByProperties(p));
								break;
							}
						}
					}
				}
			}
		}
		return dataSources.get(dataSourceKey);
	}

	/**
	 * 获取配置文件
	 * 
	 * @return
	 */
	protected List<Properties> getProperties() {
		List<Properties> rst = new ArrayList<Properties>();
		for (String configFile : configFiles) {
			try {
				Properties p = new Properties();
				p.load(this.getClass().getResourceAsStream(configFile));
				rst.add(p);
			} catch (Throwable t) {
				log.error(t);
			}
		}
		return rst;
	}

	/**
	 * 初始化执行，加载配置文件
	 */
	synchronized public void init() {
		log.info("init dynamicDataSource!");
		// 销毁之前的资源
		destroy();
		// 刷新容器
		refresh();
	}

	/**
	 * 刷新加载配置
	 */
	synchronized public void refresh() {
		log.info("refresh dynamicDataSource!");
		// 重新检索配置文件
		List<Properties> properties = getProperties();
		log.info("properties size:" + properties.size());
		// 找出在新配置中不存在旧配置
		List<Properties> oldProperties = notExist(properties,
				this.properties);
		// 找出旧配置中不存在的新配置
		List<Properties> newProperties = notExist(this.properties,
				properties);
		// 清理旧配置对应的数据源
		destoryDataSourceByProperties(oldProperties);
		// 从现有配置中删除旧配置
		this.properties.removeAll(oldProperties);
		// 创建新配置对应的数据源
		initDataSourceByPropertiess(newProperties);
		// 重现有配置中增加新配置
		this.properties.addAll(newProperties);
	}
	
	/**
	 * 获取在指定集合中不存在的另一集合数据
	 * 
	 * @param source
	 * @param comparison
	 */
	private List notExist(List source, List comparison) {
		List rst = new ArrayList(comparison);
		rst.removeAll(source);
		return rst;
	}

	/**
	 * 根据配置文件配置数据源
	 * 
	 * @param properties
	 */
	private void initDataSourceByPropertiess(List<Properties> properties) {
		for (Properties pro : properties)
			initDataSourceByProperties(pro);
	}

	/**
	 * 根据Properties文件设置数据源
	 * 
	 * @param p
	 * @throws Exception
	 */
	private void initDataSourceByProperties(Properties p) {
		String key = p.getProperty(KEY, null);
		// 检查参数是否正确
		if (StringUtils.isBlank(key)) {
			log.warn("the datasource " + KEY + " is null in property!");
			return;
		}
		if (this.lazyInit) {
			dataSources.put(key, null);
			log.info("lazy init dataSource " + key);
		} else {
			dataSources.put(key, buildDataSourceByProperties(p));
			log.info("init dataSource " + key);
		}
	}

	/**
	 * 根据配置文件创建数据源
	 * 
	 * @param p
	 * @return
	 */
	private DataSource buildDataSourceByProperties(Properties p) {
		DataSource rst = null;
		try {
			// 获取数据源实例
			rst = createDataSource();
			// 获取数据源的控制器
			DataSourceHandler dataSourceHandler = DataSourceHandler
					.buildDataSourceHandler(rst);
			Iterator<Entry<Object, Object>> i = p.entrySet().iterator();
			while (i.hasNext()) {
				try {
					Entry<Object, Object> e = i.next();
					if (e.getKey().equals(KEY))
						continue;
					else if (e.getKey().equals(DataSourceHandler.DRIVER))
						dataSourceHandler.setDriver((String) e.getValue());
					else if (e.getKey().equals(DataSourceHandler.URL))
						dataSourceHandler.setUrl((String) e.getValue());
					else if (e.getKey().equals(DataSourceHandler.USER))
						dataSourceHandler.setUser((String) e.getValue());
					else if (e.getKey().equals(DataSourceHandler.PASSWORD))
						dataSourceHandler.setPassword((String) e.getValue());
					else
						dataSourceHandler.setParameter((String) e.getKey(),
								e.getValue());
				} catch (Throwable t) {
					log.error(t);
				}
			}
		} catch (Throwable t) {
			log.error(t);
		}
		return rst;
	}

	/**
	 * 根据配置文件，清理数据源
	 * 
	 * @param oldProperties
	 */
	private void destoryDataSourceByProperties(List<Properties> properties) {
		for (Properties p : properties)
			destoryDataSourceByProperties(p);
	}

	/**
	 * 根据配置文件销毁对象并删除配置
	 * 
	 * @param pro
	 */
	private void destoryDataSourceByProperties(Properties pro) {
		// 销毁对应的数据源
		String key = pro.getProperty(KEY);
		if (dataSources.containsKey(key)) {
			DataSource ds = dataSources.get(key);
			if (ds != null) {
				try {
					DataSourceHandler dsh = DataSourceHandler
							.buildDataSourceHandler(ds);
					dsh.close();
				} catch (Throwable t) {
					log.error(t);
				}
			}
			dataSources.remove(key);
		}
		log.info("destory dataSource " + key);
	}

	/**
	 * 释放所有bean的资源
	 */
	synchronized public void destroy() {
		log.info("destroy dynamicDataSource!");
		for (DataSource dataSource : dataSources.values()) {
			try {
				if(dataSource==null)
					continue;
				DataSourceHandler dsh = DataSourceHandler
						.buildDataSourceHandler(dataSource);
				dsh.close();
			} catch (Throwable t) {
				log.error(t);
			}
		}
		dataSources.clear();
		properties.clear();
	}
}
