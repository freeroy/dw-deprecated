package org.developerworld.db.datasource;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.developerworld.servlet.AbstractUrlFilter;

/**
 * 用于设置当前请求应该使用什么dataSource的filter
 * 
 * @author Roy Huang
 * @version 20120709
 * @deprecated 
 * @see org.developerworld.commons.db project
 */
public abstract class AbstractDynamicDataSourceFilter extends AbstractUrlFilter {

	private static Log log = LogFactory
			.getLog(AbstractDynamicDataSourceFilter.class);

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
		String dataSourceKey = getDataSourceKey(request);
		log.debug("set dataSourceKey=" + dataSourceKey);
		DynamicDataSourceHolder.setDataSourceKey(dataSourceKey);
		arg2.doFilter(arg0, arg1);
	}

	/**
	 * 子类需要实现的根据请求上下文设置dataSource关键字方法
	 * 
	 * @param request
	 */
	abstract public String getDataSourceKey(HttpServletRequest request);

}
