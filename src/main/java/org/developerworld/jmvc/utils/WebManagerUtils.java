package org.developerworld.jmvc.utils;

import org.developerworld.jmvc.WebManager;

/**
 * web上下文提取工具类
 * 
 * @author Roy Huang
 * @version 20121218
 * 
 * @deprecated
 * @see org.developerworld.frameworks.jmvc project
 */
public class WebManagerUtils {

	private static ThreadLocal<WebManager> webManagerLocal = new ThreadLocal<WebManager>();

	/**
	 * 获取当前线程的web上下文
	 * @return
	 */
	public static WebManager getCurrentWebManager() {
		return webManagerLocal.get();
	}

	/**
	 * 设置当前线程的web上下文
	 * @param webContext
	 */
	public static void setCurrentWebManager(WebManager webContext) {
		webManagerLocal.set(webContext);
	}
	
	/**
	 * 清空当前线程的web上下文
	 */
	public static void clear(){
		webManagerLocal.set(null);
		webManagerLocal.remove();
	}
}
