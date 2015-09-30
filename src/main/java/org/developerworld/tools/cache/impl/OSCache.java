package org.developerworld.tools.cache.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.oscache.base.Cache;
import com.opensymphony.oscache.base.NeedsRefreshException;

/**
 * OSCache缓存类
 * @author Roy Huang
 * @version 20111007
 *
 *@deprecated
 *@see org.developerworld.commons.cache project
 */
public class OSCache implements org.developerworld.tools.cache.Cache {

	private static Log log = LogFactory.getLog(OSCache.class);

	private Cache cache;
	private String keyPrefix;

	public OSCache(String keyPrefix, Cache cache) {
		this.keyPrefix = keyPrefix;
		this.cache = cache;
	}

	public void put(String key, Object value) {
		cache.putInCache(keyPrefix + "_" + key, value);
	}

	public Object get(String key) {
		Object rst = null;
		try {
			rst = cache.getFromCache(keyPrefix + "_" + key);
		} catch (NeedsRefreshException e) {
			cache.cancelUpdate(keyPrefix + "_" + key);
			log.error(e);
		}
		return rst;
	}

	public void remove(String key) {
		cache.removeEntry(keyPrefix + "_" + key);
	}

}
