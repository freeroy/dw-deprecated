package org.developerworld.framework.hibernate3.cache;

import org.hibernate.cache.CacheException;
import org.hibernate.cache.EntityRegion;
import org.hibernate.cache.access.EntityRegionAccessStrategy;
import org.hibernate.cache.access.SoftLock;

/**
 * 动态数据源实体访问策略
 * 
 * @author Roy Huang
 * @version 20111011
 * @deprecated
 * @see org.developerworld.frameworks.hibernate3 project
 * 
 */
public class DynamicDataSourceEntityRegionAccessStrategy implements
		EntityRegionAccessStrategy {

	private EntityRegionAccessStrategy entityRegionAccessStrategy;

	private DynamicDataSourceEntityRegionAccessStrategy(
			EntityRegionAccessStrategy entityRegionAccessStrategy) {
		this.entityRegionAccessStrategy = entityRegionAccessStrategy;
	}

	public static EntityRegionAccessStrategy wrap(
			EntityRegionAccessStrategy buildAccessStrategy) {
		if (!(buildAccessStrategy instanceof DynamicDataSourceEntityRegionAccessStrategy))
			buildAccessStrategy = new DynamicDataSourceEntityRegionAccessStrategy(
					buildAccessStrategy);
		return buildAccessStrategy;
	}

	public EntityRegion getRegion() {
		return DynamicDataSourceEntityRegion.wrap(entityRegionAccessStrategy
				.getRegion());
	}

	public Object get(Object key, long txTimestamp) throws CacheException {
		return entityRegionAccessStrategy.get(
				DynamicDataSourceCacheKey.wrap(key), txTimestamp);
	}

	public boolean putFromLoad(Object key, Object value, long txTimestamp,
			Object version) throws CacheException {
		return entityRegionAccessStrategy.putFromLoad(
				DynamicDataSourceCacheKey.wrap(key), value, txTimestamp,
				version);
	}

	public boolean putFromLoad(Object key, Object value, long txTimestamp,
			Object version, boolean minimalPutOverride) throws CacheException {
		return entityRegionAccessStrategy.putFromLoad(
				DynamicDataSourceCacheKey.wrap(key), value, txTimestamp,
				version, minimalPutOverride);
	}

	public SoftLock lockItem(Object key, Object version) throws CacheException {
		return entityRegionAccessStrategy.lockItem(
				DynamicDataSourceCacheKey.wrap(key), version);
	}

	public SoftLock lockRegion() throws CacheException {
		return entityRegionAccessStrategy.lockRegion();
	}

	public void unlockItem(Object key, SoftLock lock) throws CacheException {
		entityRegionAccessStrategy.unlockItem(
				DynamicDataSourceCacheKey.wrap(key), lock);
	}

	public void unlockRegion(SoftLock lock) throws CacheException {
		entityRegionAccessStrategy.unlockRegion(lock);
	}

	public boolean insert(Object key, Object value, Object version)
			throws CacheException {
		return entityRegionAccessStrategy.insert(
				DynamicDataSourceCacheKey.wrap(key), value, version);
	}

	public boolean afterInsert(Object key, Object value, Object version)
			throws CacheException {
		return entityRegionAccessStrategy.afterInsert(
				DynamicDataSourceCacheKey.wrap(key), value, version);
	}

	public boolean update(Object key, Object value, Object currentVersion,
			Object previousVersion) throws CacheException {
		return entityRegionAccessStrategy.update(
				DynamicDataSourceCacheKey.wrap(key), value, currentVersion,
				previousVersion);
	}

	public boolean afterUpdate(Object key, Object value, Object currentVersion,
			Object previousVersion, SoftLock lock) throws CacheException {
		return entityRegionAccessStrategy.afterUpdate(
				DynamicDataSourceCacheKey.wrap(key), value, currentVersion,
				previousVersion, lock);
	}

	public void remove(Object key) throws CacheException {
		entityRegionAccessStrategy.remove(DynamicDataSourceCacheKey.wrap(key));
	}

	public void removeAll() throws CacheException {
		entityRegionAccessStrategy.removeAll();
	}

	public void evict(Object key) throws CacheException {
		entityRegionAccessStrategy.evict(DynamicDataSourceCacheKey.wrap(key));
	}

	public void evictAll() throws CacheException {
		entityRegionAccessStrategy.evictAll();
	}

}
