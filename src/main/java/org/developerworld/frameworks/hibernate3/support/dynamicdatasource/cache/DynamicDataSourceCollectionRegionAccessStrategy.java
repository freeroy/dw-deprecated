package org.developerworld.frameworks.hibernate3.support.dynamicdatasource.cache;

import org.hibernate.cache.CacheException;
import org.hibernate.cache.CollectionRegion;
import org.hibernate.cache.access.CollectionRegionAccessStrategy;
import org.hibernate.cache.access.SoftLock;

/**
 * 动态数据源集和缓存访问策略
 * 
 * @author Roy Huang
 * @version 20111011
 * @deprecated see org.developerworld.frameworks.hibernate3.dbsource
 */
public class DynamicDataSourceCollectionRegionAccessStrategy implements
		CollectionRegionAccessStrategy {

	private CollectionRegionAccessStrategy collectionRegionAccessStrategy;

	private DynamicDataSourceCollectionRegionAccessStrategy(
			CollectionRegionAccessStrategy collectionRegionAccessStrategy) {
		this.collectionRegionAccessStrategy = collectionRegionAccessStrategy;
	}

	public static CollectionRegionAccessStrategy wrap(
			CollectionRegionAccessStrategy buildAccessStrategy) {
		if (!(buildAccessStrategy instanceof DynamicDataSourceCollectionRegionAccessStrategy))
			buildAccessStrategy = new DynamicDataSourceCollectionRegionAccessStrategy(
					buildAccessStrategy);
		return buildAccessStrategy;
	}

	public CollectionRegion getRegion() {
		return DynamicDataSourceCollectionRegion
				.wrap(collectionRegionAccessStrategy.getRegion());
	}

	public Object get(Object key, long txTimestamp) throws CacheException {
		return collectionRegionAccessStrategy.get(
				DynamicDataSourceCacheKey.wrap(key), txTimestamp);
	}

	public boolean putFromLoad(Object key, Object value, long txTimestamp,
			Object version) throws CacheException {
		return collectionRegionAccessStrategy.putFromLoad(
				DynamicDataSourceCacheKey.wrap(key), value, txTimestamp,
				version);
	}

	public boolean putFromLoad(Object key, Object value, long txTimestamp,
			Object version, boolean minimalPutOverride) throws CacheException {
		return collectionRegionAccessStrategy.putFromLoad(
				DynamicDataSourceCacheKey.wrap(key), value, txTimestamp,
				version, minimalPutOverride);
	}

	public SoftLock lockItem(Object key, Object version) throws CacheException {
		return collectionRegionAccessStrategy.lockItem(
				DynamicDataSourceCacheKey.wrap(key), version);
	}

	public SoftLock lockRegion() throws CacheException {
		return collectionRegionAccessStrategy.lockRegion();
	}

	public void unlockItem(Object key, SoftLock lock) throws CacheException {
		collectionRegionAccessStrategy.unlockItem(
				DynamicDataSourceCacheKey.wrap(key), lock);
	}

	public void unlockRegion(SoftLock lock) throws CacheException {
		collectionRegionAccessStrategy.unlockRegion(lock);
	}

	public void remove(Object key) throws CacheException {
		collectionRegionAccessStrategy.remove(DynamicDataSourceCacheKey
				.wrap(key));
	}

	public void removeAll() throws CacheException {
		collectionRegionAccessStrategy.removeAll();
	}

	public void evict(Object key) throws CacheException {
		collectionRegionAccessStrategy.evict(DynamicDataSourceCacheKey
				.wrap(key));
	}

	public void evictAll() throws CacheException {
		collectionRegionAccessStrategy.evictAll();
	}

}
