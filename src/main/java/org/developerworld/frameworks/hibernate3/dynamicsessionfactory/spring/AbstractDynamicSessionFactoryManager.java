package org.developerworld.frameworks.hibernate3.dynamicsessionfactory.spring;

import java.util.List;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 动态sessionFactory管理类
 * 
 * @author Roy Huang
 * @version 20110422
 * @deprecated
 */
public abstract class AbstractDynamicSessionFactoryManager
		extends
		org.developerworld.frameworks.hibernate3.dynamicsessionfactory.AbstractDynamicSessionFactoryManager
		implements ApplicationContextAware {

	private static Log log = LogFactory
			.getLog(AbstractDynamicSessionFactoryManager.class);

	private ApplicationContext applicationContext;

	private String locationPattern;

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	public void setLocationPattern(String locationPattern) {
		this.locationPattern = locationPattern;
	}

	/**
	 * 重构获取配置文件方法
	 */
	@Override
	protected List<Properties> getProperties() {
		List<Properties> rst = super.getProperties();
		try {
			if (locationPattern != null) {
				org.springframework.core.io.Resource[] resources = applicationContext
						.getResources(locationPattern);
				for (org.springframework.core.io.Resource resource : resources) {
					try {
						Properties p = new Properties();
						p.load(resource.getInputStream());
						rst.add(p);
					} catch (Throwable t) {
						log.error(t);
					}
				}
			}
		} catch (Throwable t) {
			log.error(t);
		}
		return rst;
	}
}
