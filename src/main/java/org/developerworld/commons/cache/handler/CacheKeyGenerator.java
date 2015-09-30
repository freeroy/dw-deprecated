package org.developerworld.commons.cache.handler;

/**
 * 缓存key创建器
 * @author Roy Huang
 * @version 20111010
 * @deprecated
 * @see org.developerworld.commons.cache project
 */
public interface CacheKeyGenerator {

	/**
	 * 根据传入的缓存名、缓存节点、参数生成key
	 * @param cacheName
	 * @param cacheNodes
	 * @param keyArgs
	 * @return
	 */
	public String generate(String cacheName,String[] cacheNodes, Object[] keyArgs);
}
