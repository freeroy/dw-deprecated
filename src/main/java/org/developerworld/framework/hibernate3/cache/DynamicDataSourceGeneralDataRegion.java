package org.developerworld.framework.hibernate3.cache;

import org.hibernate.cache.CacheException;
import org.hibernate.cache.GeneralDataRegion;

/**
 * 动态数据源普通数据缓存
 * 
 * @author Roy Huang
 * @version 20111011
 * @deprecated
 * @see org.developerworld.frameworks.hibernate3 project
 * 
 */
public class DynamicDataSourceGeneralDataRegion extends DynamicDataSourceRegion
		implements GeneralDataRegion {

	private GeneralDataRegion generalDataRegion;

	protected DynamicDataSourceGeneralDataRegion(
			GeneralDataRegion generalDataRegion) {
		super(generalDataRegion);
		this.generalDataRegion = generalDataRegion;
	}

	public Object get(Object key) throws CacheException {
		return generalDataRegion.get(DynamicDataSourceCacheKey.wrap(key));
	}

	public void put(Object key, Object value) throws CacheException {
		generalDataRegion.put(DynamicDataSourceCacheKey.wrap(key), value);

	}

	public void evict(Object key) throws CacheException {
		generalDataRegion.evict(DynamicDataSourceCacheKey.wrap(key));
	}

	public void evictAll() throws CacheException {
		generalDataRegion.evictAll();
	}

}
