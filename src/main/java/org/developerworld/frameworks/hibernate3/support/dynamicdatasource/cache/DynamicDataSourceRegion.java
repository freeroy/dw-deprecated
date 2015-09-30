package org.developerworld.frameworks.hibernate3.support.dynamicdatasource.cache;

import java.util.Map;

import org.hibernate.cache.CacheException;
import org.hibernate.cache.Region;

/**
 * 动态数据源缓存
 * 
 * @author Roy Huang
 * @version 20111011
 * @deprecated see org.developerworld.frameworks.hibernate3.dbsource
 */
public class DynamicDataSourceRegion implements Region {

	private Region region;

	protected DynamicDataSourceRegion(Region region) {
		this.region = region;
	}

	public String getName() {
		return region.getName();
	}

	public void destroy() throws CacheException {
		region.destroy();
	}

	public boolean contains(Object key) {
		return region.contains(DynamicDataSourceCacheKey.wrap(key));
	}

	public long getSizeInMemory() {
		return region.getSizeInMemory();
	}

	public long getElementCountInMemory() {
		return region.getElementCountInMemory();
	}

	public long getElementCountOnDisk() {
		return region.getElementCountOnDisk();
	}

	/**
	 * 这里可能存在问题，需要如下处理（1、根据官方定义可以直接返回empty map；2、对map中的key进行还原）
	 */
	public Map toMap() {
		return region.toMap();
	}

	public long nextTimestamp() {
		return region.nextTimestamp();
	}

	public int getTimeout() {
		return region.getTimeout();
	}

}
