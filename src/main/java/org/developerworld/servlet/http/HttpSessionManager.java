package org.developerworld.servlet.http;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Session 管理器
 * 
 * @author Roy Huang
 * @version 20120419
 * 
 * @deprecated
 * @see org.developerworld.commons.servlet project
 */
public class HttpSessionManager {

	private static Log log = LogFactory.getLog(HttpSessionManager.class);

	private static HttpSessionManager manager = new HttpSessionManager();

	private Map<String, HttpSession> sessions = new HashMap<String, HttpSession>();

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
		synchronized (sessions) {
			sessions.put(session.getId(), session);
		}
	}

	/**
	 * 删除session
	 * 
	 * @param session
	 */
	public void remove(HttpSession session) {
		synchronized (sessions) {
			sessions.remove(session.getId());
		}
	}

	/**
	 * 清空池
	 */
	public void clear() {
		synchronized (sessions) {
			sessions.clear();
		}
	}

	/**
	 * 通过过滤器获取会话
	 * 
	 * @param filter
	 * @return
	 */
	public List<HttpSession> getHttpSessions(HttpSessionFilter filter) {
		List<HttpSession> rst = new ArrayList<HttpSession>();
		synchronized (sessions) {
			Collection<HttpSession> _sessions = sessions.values();
			for (HttpSession session : _sessions) {
				if (filter.match(session))
					rst.add(session);
			}
		}
		return rst;
	}

}
