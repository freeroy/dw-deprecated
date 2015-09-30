package org.developerworld.framework.spring.jdbc.datasource;

import java.util.List;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 用于分配可使用的源 注意！该类默认不是单例的，因此需要自行进行管理（实现类的时候该为单例模式或利用spring【建议】）
 * 
 * @author Roy Huang
 * @version 20111006
 * 
 * @deprecated
 * @see org.developerworld.frameworks.spring project
 */
public abstract class AbstractDynamicDataSourceManager extends
		org.developerworld.db.datasource.AbstractDynamicDataSourceManager implements ApplicationContextAware{

	private static Log log = LogFactory
			.getLog(AbstractDynamicDataSourceManager.class);

	protected ApplicationContext applicationContext;

	private String locationPattern;

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
