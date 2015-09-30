package org.developerworld.tools.cache.support;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.developerworld.db.datasource.DynamicDataSourceHolder;

/**
 * 增加动态数据源支持的缓存控制器
 * 
 * @author Roy Huang
 * @version 20111010
 * 
 *@deprecated
 *@see org.developerworld.commons.cache project
 */
public class DynamicDataSourceCacheHandler implements CacheHandler {

	private static Log log = LogFactory
			.getLog(DynamicDataSourceCacheHandler.class);
	private final static String ERROR_MESSAGE = "\""
			+ DynamicDataSourceCacheHandler.class
			+ "\" unsupport this method!!!";

	private CacheHandler cacheHandler;

	public DynamicDataSourceCacheHandler(CacheHandler cacheHandler) {
		this.cacheHandler = cacheHandler;
	}

	/**
	 * 获取新的缓存节点列表
	 * 
	 * @param cacheNodes
	 * @return
	 */
	private String[] getNewCacheNodes(String[] cacheNodes) {
		String dataourceKey = DynamicDataSourceHolder.getDataSourceKey();
		String[] newCacheNodes = null;
		if (cacheNodes == null)
			newCacheNodes = new String[] { dataourceKey };
		else {
			newCacheNodes = new String[cacheNodes.length + 1];
			newCacheNodes[0] = dataourceKey;
			for (int i = 0; i < cacheNodes.length; i++)
				newCacheNodes[i + 1] = cacheNodes[i];
		}
		return newCacheNodes;
	}

	/**
	 * 获取新的键参数
	 * 
	 * @param keyArgs
	 * @return
	 */
	private Object[] getNewKeyArgs(Object[] keyArgs) {
		String dataourceKey = DynamicDataSourceHolder.getDataSourceKey();
		Object[] newArgs = null;
		if (keyArgs == null)
			newArgs = new Object[] { dataourceKey };
		else {
			newArgs = new Object[keyArgs.length + 1];
			newArgs[0] = dataourceKey;
			for (int i = 0; i < keyArgs.length; i++)
				newArgs[i + 1] = keyArgs[i];
		}
		return newArgs;
	}

	private <T> T unsupported() {
		log.error(ERROR_MESSAGE);
		throw new UnsupportedOperationException(ERROR_MESSAGE);
	}

	@Deprecated
	public <T> void putInCache(String cacheName, String cacheKey, T data) {
		unsupported();
	}

	@Deprecated
	public <T> void putInCache(String cacheName, String[] cacheNodes,
			String cacheKey, T data) {
		unsupported();
	}

	public <T> void putInCache(String cacheName, Object[] keyArgs, T data) {
		putInCache(cacheName, null, keyArgs, data);
	}

	public <T> void putInCache(String cacheName, String[] cacheNodes,
			Object[] keyArgs, T data) {
		cacheHandler.putInCache(cacheName, getNewCacheNodes(cacheNodes),
				getNewKeyArgs(keyArgs), data);
	}

	@Deprecated
	public <T> T getFromCache(String cacheName, String cacheKey) {
		return (T)unsupported();
	}

	@Deprecated
	public <T> T getFromCache(String cacheName, String[] cacheNodes,
			String cacheKey) {
		return (T)unsupported();
	}

	@Deprecated
	public <T> T getFromCache(String cacheName, String[] cacheNodes,
			String cacheKey, NotInCacheMethod<T> notInCacheMethod)
			throws Throwable {
		return (T)unsupported();
	}

	public <T> T getFromCache(String cacheName, Object[] keyArgs) {
		return (T)getFromCache(cacheName, null, keyArgs);
	}

	public <T> T getFromCache(String cacheName, String[] cacheNodes,
			Object[] keyArgs) {
		return (T)cacheHandler.getFromCache(cacheName,
				getNewCacheNodes(cacheNodes), getNewKeyArgs(keyArgs));
	}

	public <T> T getFromCache(String cacheName, String[] cacheNodes,
			Object[] keyArgs, NotInCacheMethod<T> notInCacheMethod)
			throws Throwable {
		return cacheHandler.getFromCache(cacheName,
				getNewCacheNodes(cacheNodes), getNewKeyArgs(keyArgs),
				notInCacheMethod);
	}

	@Deprecated
	public void removeCache(String cacheName, String[] cacheNodes,
			String cacheKey, boolean cascadeCacheNode) {
		unsupported();
	}

	public void removeCache(String cacheName, String[] cacheNodes,
			boolean cascadeCacheNode) {
		cacheHandler.removeCache(cacheName, getNewCacheNodes(cacheNodes),
				cascadeCacheNode);
	}

	public void removeCache(String cacheName, String[] cacheNodes,
			Object[] args, boolean cascadeCacheNode) {
		cacheHandler.removeCache(cacheName, getNewCacheNodes(cacheNodes),
				getNewKeyArgs(args), cascadeCacheNode);
	}

	public void removeCache(String cacheName) {
		cacheHandler.removeCache(cacheName);
	}

}
