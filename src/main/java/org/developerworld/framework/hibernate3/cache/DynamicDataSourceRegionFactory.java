package org.developerworld.framework.hibernate3.cache;

import java.util.Properties;

import org.hibernate.cache.CacheDataDescription;
import org.hibernate.cache.CacheException;
import org.hibernate.cache.CollectionRegion;
import org.hibernate.cache.EntityRegion;
import org.hibernate.cache.QueryResultsRegion;
import org.hibernate.cache.RegionFactory;
import org.hibernate.cache.TimestampsRegion;
import org.hibernate.cache.access.AccessType;
import org.hibernate.cfg.Settings;

/**
 * 支持动态数据源的RegionFactory
 * 
 * @author Roy Huang
 * @version 20111011
 * @deprecated
 * @see org.developerworld.frameworks.hibernate3 project
 * 
 */
public class DynamicDataSourceRegionFactory implements RegionFactory {

	private RegionFactory regionFactory;

	public DynamicDataSourceRegionFactory(RegionFactory regionFactory) {
		this.regionFactory = regionFactory;
	}

	public void start(Settings settings, Properties properties)
			throws CacheException {
		regionFactory.start(settings, properties);
	}

	public void stop() {
		regionFactory.stop();
	}

	public boolean isMinimalPutsEnabledByDefault() {
		return regionFactory.isMinimalPutsEnabledByDefault();
	}

	public AccessType getDefaultAccessType() {
		return regionFactory.getDefaultAccessType();
	}

	public long nextTimestamp() {
		return regionFactory.nextTimestamp();
	}

	public EntityRegion buildEntityRegion(String regionName,
			Properties properties, CacheDataDescription metadata)
			throws CacheException {
		return DynamicDataSourceEntityRegion.wrap(regionFactory
				.buildEntityRegion(regionName, properties, metadata));
	}

	public CollectionRegion buildCollectionRegion(String regionName,
			Properties properties, CacheDataDescription metadata)
			throws CacheException {
		return DynamicDataSourceCollectionRegion.wrap(regionFactory
				.buildCollectionRegion(regionName, properties, metadata));
	}

	public QueryResultsRegion buildQueryResultsRegion(String regionName,
			Properties properties) throws CacheException {
		return DynamicDataSourceQueryResultsRegion.wrap(regionFactory
				.buildQueryResultsRegion(regionName, properties));
	}

	public TimestampsRegion buildTimestampsRegion(String regionName,
			Properties properties) throws CacheException {
		return DynamicDataSourceTimestampsRegion.wrap(regionFactory
				.buildTimestampsRegion(regionName, properties));
	}

}
