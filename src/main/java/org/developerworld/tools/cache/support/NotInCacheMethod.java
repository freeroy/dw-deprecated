package org.developerworld.tools.cache.support;

/**
 * 当缓存数据不存在的时候执行的方法接口
 * @author Roy Huang
 * @version 20111010
 *
 *@deprecated
 *@see org.developerworld.commons.cache project
 * @param <T>
 */
public interface NotInCacheMethod<T> {

	/**
	 * 回调执行方法
	 * @return
	 * @throws Throwable
	 */
	public T invoke() throws Throwable;
	
}
