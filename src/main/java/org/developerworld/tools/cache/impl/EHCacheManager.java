package org.developerworld.tools.cache.impl;

import java.io.InputStream;
import java.net.URL;

import org.developerworld.tools.cache.Cache;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.config.Configuration;

/**
 * EHCache管理类
 * @author Roy Huang
 * @version 20111006
 *
 *@deprecated
 *@see org.developerworld.commons.cache project
 */
public class EHCacheManager implements
		org.developerworld.tools.cache.CacheManager {

	private String configPath;
	private URL configUrl;
	private CacheManager cacheManager;
	private Configuration configuration;
	private InputStream configInputStream;

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

	public void setConfigInputStream(InputStream configInputStream) {
		this.configInputStream = configInputStream;
	}

	public void setConfigUrl(URL configUrl) {
		this.configUrl = configUrl;
	}

	public void setConfigPath(String configPath) {
		this.configPath = configPath;
	}

	public void setCacheManager(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	public void init() {
		if (cacheManager == null) {
			if (configuration != null)
				cacheManager = CacheManager.create(configuration);
			else if (configInputStream != null)
				cacheManager = CacheManager.create(configInputStream);
			else if (configUrl != null)
				cacheManager = CacheManager.create(configUrl);
			else if (configPath != null)
				cacheManager = CacheManager.create(configPath);
			else
				cacheManager = CacheManager.create();
		}
	}

	public void destory() {
		cacheManager.shutdown();
		cacheManager=null;
	}

	public Cache getCache(String cacheName) {
		return new EHCache(cacheManager.getCache(cacheName));
	}

}
