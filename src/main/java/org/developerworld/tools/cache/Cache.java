package org.developerworld.tools.cache;

/**
 * 缓存处理对象
 * @author Roy Huang
 * @version 20111006
 *
 *@deprecated
 *@see org.developerworld.commons.cache project
 */
public interface Cache {

	public void put(String key,Object value);
	
	public Object get(String key);
	
	public void remove(String key);

}
