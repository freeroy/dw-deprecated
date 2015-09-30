package org.developerworld.framework.hibernate3.cache;

import org.hibernate.cache.CacheException;
import org.hibernate.cache.CollectionRegion;
import org.hibernate.cache.access.AccessType;
import org.hibernate.cache.access.CollectionRegionAccessStrategy;

/**
 * 动态数据源集合缓存
 * 
 * @author Roy Huang
 * @version 20111011
 * @deprecated
 * @see org.developerworld.frameworks.hibernate3 project
 * 
 */
public class DynamicDataSourceCollectionRegion extends
		DynamicDataSourceTransactionalDataRegion implements CollectionRegion {

	private CollectionRegion collectionRegion;

	private DynamicDataSourceCollectionRegion(CollectionRegion collectionRegion) {
		super(collectionRegion);
		this.collectionRegion = collectionRegion;
	}

	public static CollectionRegion wrap(CollectionRegion collectionRegion) {
		if (!(collectionRegion instanceof DynamicDataSourceCollectionRegion))
			collectionRegion = new DynamicDataSourceCollectionRegion(
					collectionRegion);
		return collectionRegion;
	}

	public CollectionRegionAccessStrategy buildAccessStrategy(
			AccessType accessType) throws CacheException {
		return DynamicDataSourceCollectionRegionAccessStrategy
				.wrap(collectionRegion.buildAccessStrategy(accessType));
	}

}
