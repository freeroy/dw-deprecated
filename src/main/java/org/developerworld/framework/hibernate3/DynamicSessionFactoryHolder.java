package org.developerworld.framework.hibernate3;

/**
 * 由于获取当前应该使用SessionFactory的key的类
 * @author Roy Huang
 * @version 20110105
 * @deprecated
 * @see org.developerworld.frameworks.hibernate3 project
 *
 */
public class DynamicSessionFactoryHolder {
	
	private static ThreadLocal<String> sessionFactoryKey=new ThreadLocal<String>();
	
	/**
	 * 
	 * @return
	 */
	public static String getSessionFactoryKey(){
		return sessionFactoryKey.get();
	}
	
	/**
	 * 
	 * @param key
	 */
	public static void setSessionFactoryKey(String key){
		sessionFactoryKey.set(key);
	}
	
	/**
	 * 
	 */
	public static void removeSessionFactoryKey(){
		sessionFactoryKey.set(null);
		sessionFactoryKey.remove();
	}
}
