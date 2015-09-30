package org.developerworld.tools.cache.impl;

import java.util.Properties;

import org.developerworld.tools.cache.Cache;
import org.developerworld.tools.cache.CacheManager;

import com.opensymphony.oscache.general.GeneralCacheAdministrator;

/**
 * OSCache 管理类
 * @author Roy Huang
 * @version 20111007
 *
 *@deprecated
 *@see org.developerworld.commons.cache project
 */
public class OSCacheManager implements CacheManager {
	
	private GeneralCacheAdministrator admin;
	private Properties properties;
	
	public void setConfigProperties(Properties properties){
		this.properties=properties;
	}

	public void init() {
		if(properties!=null)
			admin=new GeneralCacheAdministrator(properties);
		else
			admin=new GeneralCacheAdministrator();
	}

	public void destory() {
		admin.destroy();
		admin=null;
	}

	public Cache getCache(String cacheName) {
		return new OSCache(cacheName,admin.getCache());
	}

}
