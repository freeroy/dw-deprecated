package org.developerworld.frameworks.hibernate3.dynamicsessionfactory.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.developerworld.frameworks.hibernate3.dynamicsessionfactory.DynamicSessionFactoryHolder;
import org.developerworld.servlet.AbstractUrlFilter;

/**
 * 用于设置当前请求应该使用什么sessionFactory的filter
 * 
 * @author Roy Huang
 * @version 20110105
 * @deprecated
 */
public abstract class AbstractDynamicSessionFactoryFilter extends
		AbstractUrlFilter {

	Log log = LogFactory.getLog(AbstractDynamicSessionFactoryFilter.class);

	/**
	 * 要求子类必须实现的方法
	 * 
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @throws ServletException
	 * @throws IOException
	 */
	public void doFilterWhenUrlPass(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) arg0;
		String sessionFactoryKey = getSessionFactoryKey(request);
		log.debug("set sessionFactoryKey=" + sessionFactoryKey);
		DynamicSessionFactoryHolder.setSessionFactoryKey(sessionFactoryKey);
		arg2.doFilter(arg0, arg1);
	}

	/**
	 * 子类需要实现的根据请求上下文设置SessionFactory关键字方法
	 * 
	 * @param request
	 */
	abstract public String getSessionFactoryKey(HttpServletRequest request);

}
