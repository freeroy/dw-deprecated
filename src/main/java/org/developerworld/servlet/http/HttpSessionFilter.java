package org.developerworld.servlet.http;

import javax.servlet.http.HttpSession;

/**
 * 会话过滤器
 * @author Roy Huang
 * @version 20110226
 *
 * @deprecated
 * @see org.developerworld.commons.servlet project
 */
public interface HttpSessionFilter {

	/**
	 * 回调执行方法，符合的返回true，否则false
	 * @param session
	 * @return
	 */
	public boolean match(HttpSession session);

}
