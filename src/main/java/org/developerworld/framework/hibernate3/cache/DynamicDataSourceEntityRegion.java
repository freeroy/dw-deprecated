package org.developerworld.framework.hibernate3.cache;

import org.hibernate.cache.CacheException;
import org.hibernate.cache.EntityRegion;
import org.hibernate.cache.access.AccessType;
import org.hibernate.cache.access.EntityRegionAccessStrategy;

/**
 * 动态数据源实体缓存
 * 
 * @author Roy Huang
 * @version 20111011
 * @deprecated
 * @see org.developerworld.frameworks.hibernate3 project
 * 
 */
public class DynamicDataSourceEntityRegion extends
		DynamicDataSourceTransactionalDataRegion implements EntityRegion {

	private EntityRegion entityRegion;

	private DynamicDataSourceEntityRegion(EntityRegion entityRegion) {
		super(entityRegion);
		this.entityRegion = entityRegion;
	}

	public static EntityRegion wrap(EntityRegion region) {
		if (!(region instanceof DynamicDataSourceEntityRegion))
			region = new DynamicDataSourceEntityRegion(region);
		return region;
	}

	public EntityRegionAccessStrategy buildAccessStrategy(AccessType accessType)
			throws CacheException {
		return DynamicDataSourceEntityRegionAccessStrategy.wrap(entityRegion
				.buildAccessStrategy(accessType));
	}

}
