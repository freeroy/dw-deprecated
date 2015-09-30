package org.developerworld.commons.servlet.http;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Session 监听器
 * 
 * @author Roy Huang
 * @version 20110226
 * @deprecated 
 * @see org.developerworld.commons.httpsessionmanager project
 */
public class HttpSessionManagerListener implements HttpSessionListener,ServletContextListener {

	private static Log log = LogFactory.getLog(HttpSessionManagerListener.class);

	private HttpSessionManager manager;

	public HttpSessionManagerListener() {
		manager = HttpSessionManager.getInstance();
		log.info(getClass() + " NEW");
	}

	/**
	 * 会话创建执行
	 */
	public void sessionCreated(HttpSessionEvent event) {
		HttpSession session = event.getSession();
		manager.add(session);
		log.debug("session created:" + session);
	}

	/**
	 * 会话注销执行
	 */
	public void sessionDestroyed(HttpSessionEvent event) {
		HttpSession session = event.getSession();
		manager.remove(session);
		log.debug("session destroyed:" + session);
	}

	/**
	 * 容器关闭
	 */
	public void contextDestroyed(ServletContextEvent arg0) {
		manager.clear();
		log.info(getClass()+" - contextDestroyed");
	}

	/**
	 * 容器启动
	 */
	public void contextInitialized(ServletContextEvent arg0) {
		log.info(getClass()+" - contextInitialized");
	}

}
