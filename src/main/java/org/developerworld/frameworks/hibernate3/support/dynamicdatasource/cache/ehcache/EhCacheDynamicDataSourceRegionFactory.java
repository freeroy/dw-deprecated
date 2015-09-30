package org.developerworld.frameworks.hibernate3.support.dynamicdatasource.cache.ehcache;

import net.sf.ehcache.hibernate.EhCacheRegionFactory;

import org.developerworld.frameworks.hibernate3.support.dynamicdatasource.cache.DynamicDataSourceRegionFactory;

/**
 * 针对ehcache的动态数据源工厂
 * @author Roy Huang
 * @version 20140219
 * @deprecated see org.developerworld.frameworks.hibernate3.dbsource
 */
public class EhCacheDynamicDataSourceRegionFactory extends DynamicDataSourceRegionFactory{
	
	public EhCacheDynamicDataSourceRegionFactory(){
		super(new EhCacheRegionFactory(null));
	}
}
