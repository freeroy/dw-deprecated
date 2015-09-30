package org.developerworld.frameworks.hibernate3.dynamicsessionfactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;

/**
 * 用于分配可使用的源 注意！该类默认不是单例的，因此需要自行进行管理（实现类的时候改为单例模式或利用spring【建议】）
 * 
 * @author Roy Huang
 * @version 20110728
 * @deprecated
 */
public abstract class AbstractDynamicSessionFactoryManager {

	private static Log log = LogFactory
			.getLog(AbstractDynamicSessionFactoryManager.class);

	public final static String KEY = "key";

	private boolean lazyInit = false;// 是否延迟加载数据源

	protected Map<String, SessionFactory> sessionFactorys = new HashMap<String, SessionFactory>();

	private List<String> configFiles = new ArrayList<String>();

	private List<Properties> properties = new ArrayList<Properties>();

	private SessionFactory defaultSessionFactory;

	public void setDefaultSessionFactory(SessionFactory defaultSessionFactory) {
		this.defaultSessionFactory = defaultSessionFactory;
	}

	public void setConfigFiles(List<String> configFiles) {
		this.configFiles = configFiles;
	}

	public void setLazyInit(boolean lazyInit) {
		this.lazyInit = lazyInit;
	}

	/**
	 * 获取标准数据源
	 * 
	 * @return
	 */
	abstract public SessionFactory createSessionFactory();

	/**
	 * 获取动态SessionFactory
	 * 
	 * @return
	 */
	public SessionFactory getSessionFactory() {
		SessionFactory rst = defaultSessionFactory;
		String sessionFactoryKey = DynamicSessionFactoryHolder
				.getSessionFactoryKey();
		log.debug("the DynamicSessionFactoryHolder.getDataSourceKey is "
				+ sessionFactoryKey);
		SessionFactory tmp = getSessionFactory(sessionFactoryKey);
		if (tmp != null)
			rst = tmp;
		log.debug("return SessionFactory is " + rst);
		return rst;
	}

	/**
	 * 获取指定名称的SessionFactory
	 * 
	 * @param sessionFactoryKey
	 * @return
	 */
	private SessionFactory getSessionFactory(String sessionFactoryKey) {
		// 若数据源没初始化，就执行初始化
		if (sessionFactorys.containsKey(sessionFactoryKey)
				&& sessionFactorys.get(sessionFactoryKey) == null) {
			synchronized (sessionFactorys) {
				synchronized (properties) {
					if (sessionFactorys.get(sessionFactoryKey) == null) {
						for (Properties p : properties) {
							String key = p.getProperty(KEY, null);
							if (sessionFactoryKey.equals(key)) {
								sessionFactorys.put(key,
										buildSessionFactoryByProperties(p));
								break;
							}
						}
					}
				}
			}
		}
		return sessionFactorys.get(sessionFactoryKey);
	}

	private SessionFactory buildSessionFactoryByProperties(Properties p) {
		SessionFactory rst = null;
		try {
			// 获取数据源实例
			rst = createSessionFactory();
		} catch (Throwable t) {
			log.error(t);
		}
		return rst;
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
		log.info("init dynamicSessionFactory!");
		// 销毁之前的资源
		destroy();
		// 刷新容器
		refresh();
	}

	/**
	 * 刷新加载配置
	 */
	synchronized public void refresh() {
		log.info("refresh dynamicSessionFactory!");
		List<Properties> properties = getProperties();
		log.info("properties size:" + properties.size());
		// 找出在新配置中不存在旧配置
		List<Properties> oldProperties = notExist(properties,
				this.properties);
		// 找出旧配置中不存在的新配置
		List<Properties> newProperties = notExist(this.properties,
				properties);
		// 清理旧配置对应的SessionFactory
		destorySessionFactoryByProperties(oldProperties);
		// 从现有配置中删除旧配置
		this.properties.removeAll(oldProperties);
		// 创建新配置对应的SessionFactory
		initSessionFactoryByPropertiess(newProperties);
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
	 * 创建配置文件集合的SessionFactory
	 * 
	 * @param properties
	 */
	private void initSessionFactoryByPropertiess(List<Properties> properties) {
		for (Properties pro : properties)
			initDataSourceByProperties(pro);
	}

	/**
	 * 创建配置文件的SessionFactory
	 * 
	 * @param p
	 */
	private void initDataSourceByProperties(Properties p) {
		String key = p.getProperty(KEY, null);
		// 检查参数是否正确
		if (StringUtils.isEmpty(key)) {
			log.warn("the SessionFactory " + KEY + " is null in property!");
			return;
		}
		if (this.lazyInit) {
			sessionFactorys.put(key, null);
			log.info("lazy init sessionFactory " + key);
		} else {
			sessionFactorys.put(key, buildSessionFactoryByProperties(p));
			log.info("init sessionFactory " + key);
		}
	}

	/**
	 * 释放配置文件集合的SessionFactory
	 * 
	 * @param properties
	 */
	private void destorySessionFactoryByProperties(List<Properties> properties) {
		for (Properties p : properties)
			destorySessionFactoryByProperties(p);
	}

	/**
	 * 释放配置文件的SessionFactory
	 * 
	 * @param pro
	 */
	private void destorySessionFactoryByProperties(Properties pro) {
		// 销毁对应的数据源
		String key = pro.getProperty(KEY);
		if (sessionFactorys.containsKey(key)) {
			SessionFactory sf = sessionFactorys.get(key);
			if (sf != null) {
				try {
					if (!sf.isClosed())
						sf.close();
				} catch (Throwable t) {
					log.error(t);
				}
			}
			sessionFactorys.remove(key);
		}
		log.info("destory dataSource " + key);
	}

	/**
	 * 释放所有bean的资源
	 */
	synchronized public void destroy() {
		log.info("destroy dynamicSessionFactory!");
		for (SessionFactory sessionFactory : sessionFactorys.values()) {
			try {
				if (sessionFactory == null)
					continue;
				if (!sessionFactory.isClosed())
					sessionFactory.close();
			} catch (Throwable t) {
				log.error(t);
			}
		}
		sessionFactorys.clear();
		properties.clear();
	}
}
