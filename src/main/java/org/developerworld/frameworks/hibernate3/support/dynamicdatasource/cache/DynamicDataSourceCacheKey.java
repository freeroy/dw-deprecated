package org.developerworld.frameworks.hibernate3.support.dynamicdatasource.cache;

import org.developerworld.commons.db.dynamicdatasource.DynamicDataSourceHolder;


/**
 * 动态数据源的缓存Key
 * 
 * @author Roy Huang
 * @version 20111011
 * @deprecated see org.developerworld.frameworks.hibernate3.dbsource
 */
public class DynamicDataSourceCacheKey {

	private String dynamicDataSourceKey;

	private Object key;

	private DynamicDataSourceCacheKey(Object key) {
		this.dynamicDataSourceKey = DynamicDataSourceHolder.getDataSourceKey();
		this.key = key;
	}

	/**
	 * 生成缓存key
	 * 
	 * @param key
	 * @return
	 */
	public static Object wrap(Object key) {
		if (!(key instanceof DynamicDataSourceCacheKey))
			key = new DynamicDataSourceCacheKey(key);
		return key;
	}

	@Override
	public String toString() {
		return "DynamicDataSourceCacheKey [dynamicDataSourceKey="
				+ dynamicDataSourceKey + ", key=" + key + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((dynamicDataSourceKey == null) ? 0 : dynamicDataSourceKey
						.hashCode());
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DynamicDataSourceCacheKey other = (DynamicDataSourceCacheKey) obj;
		if (dynamicDataSourceKey == null) {
			if (other.dynamicDataSourceKey != null)
				return false;
		} else if (!dynamicDataSourceKey.equals(other.dynamicDataSourceKey))
			return false;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		return true;
	}

}
