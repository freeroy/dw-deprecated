package org.developerworld.tools.cache.impl;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

/**
 * Ehcache内存对象
 * 
 * @author Roy Huang
 * @version 20111006
 * 
 *@deprecated
 *@see org.developerworld.commons.cache project
 */
public class EHCache implements org.developerworld.tools.cache.Cache {

	Cache cache;

	public Cache getCache() {
		return cache;
	}

	public EHCache(Cache cache) {
		this.cache = cache;
	}

	public void put(String key, Object value) {
		cache.put(new Element(key, value));
	}

	public Object get(String key) {
		Element e = cache.get(key);
		return e == null ? null : e.getValue();
	}

	public void remove(String key) {
		cache.remove(key);
	}

}
