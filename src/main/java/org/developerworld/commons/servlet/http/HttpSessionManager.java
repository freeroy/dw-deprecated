package org.developerworld.commons.servlet.http;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Session 管理器
 * 
 * @author Roy Huang
 * @version 20120419
 * @deprecated 
 * @see org.developerworld.commons.httpsessionmanager project
 */
public class HttpSessionManager {

	private static Log log = LogFactory.getLog(HttpSessionManager.class);

	private static HttpSessionManager manager = new HttpSessionManager();

	private ConcurrentMap<String, HttpSession> sessions = new ConcurrentHashMap<String, HttpSession>();

	private HttpSessionManager() {
		log.info(getClass() + " NEW");
	}

	public static HttpSessionManager getInstance() {
		return manager;
	}

	/**
	 * 添加session到池
	 * 
	 * @param session
	 */
	public void add(HttpSession session) {
		sessions.put(session.getId(), session);
	}

	/**
	 * 删除session
	 * 
	 * @param session
	 */
	public void remove(HttpSession session) {
		sessions.remove(session.getId());
	}

	/**
	 * 清空池
	 */
	public void clear() {
		sessions.clear();
	}

	/**
	 * 通过过滤器获取会话
	 * 
	 * @param filter
	 * @return
	 */
	public List<HttpSession> getHttpSessions(HttpSessionFilter filter) {
		List<HttpSession> rst = new ArrayList<HttpSession>();
		Collection<HttpSession> _sessions = sessions.values();
		for (HttpSession session : _sessions) {
			if (filter.match(session))
				rst.add(session);
		}
		return rst;
	}

}
