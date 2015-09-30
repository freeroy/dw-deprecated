package org.developerworld.commons.cache.oscache;

import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.oscache.base.Cache;
import com.opensymphony.oscache.base.NeedsRefreshException;

/**
 * OSCache缓存类
 * @author Roy Huang
 * @version 20111007
 * @deprecated
 * @see org.developerworld.commons.cache project
 */
public class OSCache implements org.developerworld.commons.cache.Cache {

	private static Log log = LogFactory.getLog(OSCache.class);

	private Cache cache;
	private String keyPrefix;

	/**
	 * 获取keys缓存的key
	 * 
	 * @return
	 */
	private String getKeysKey() {
		return this.getClass().getName() + "_" + keyPrefix + "_keys";
	}

	/**
	 * 更新key
	 * 
	 * @param key
	 */
	private void updateKeys(String key, boolean isRemove) {
		String keysKey = getKeysKey();
		Set<String> keys = null;
		try {
			keys = (Set<String>) cache.getFromCache(keysKey);
		} catch (NeedsRefreshException e) {
			log.error(e);
		}
		if (keys == null) {
			keys = new LinkedHashSet<String>();
			cache.putInCache(keysKey, keys);
		}
		if (isRemove && keys.contains(key)) {
			keys.remove(key);
			cache.putInCache(keysKey, keys);
		} else if (!keys.contains(key)) {
			keys.add(key);
			cache.putInCache(keysKey, keys);
		}
	}

	public OSCache(String keyPrefix, Cache cache) {
		this.keyPrefix = keyPrefix;
		this.cache = cache;
	}

	public void put(String key, Object value) {
		cache.putInCache(keyPrefix + "_" + key, value);
		updateKeys(key, false);
	}

	public Object get(String key) {
		Object rst = null;
		try {
			rst = cache.getFromCache(keyPrefix + "_" + key);
		} catch (NeedsRefreshException e) {
			log.error(e);
		}
		return rst;
	}

	public void remove(String key) {
		cache.removeEntry(keyPrefix + "_" + key);
		updateKeys(key, true);
	}

	public void removeAll() {
		Set<String> keys = getKeys();
		for (String key : keys)
			remove(key);
	}

	public int size() {
		return getKeys().size();
	}

	public Set<String> getKeys() {
		Set<String> rst = null;
		try {
			String keysKey = getKeysKey();
			rst = (Set<String>) cache.getFromCache(keysKey);
			if (rst == null)
				rst = new LinkedHashSet<String>();
		} catch (NeedsRefreshException e) {
			log.error(e);
		}
		return rst;
	}
}
