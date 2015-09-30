package org.developerworld.commons.db.dynamicdatasource.support.web;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.developerworld.commons.db.dynamicdatasource.DynamicDataSourceHolder;
import org.developerworld.servlet.AbstractUrlFilter;

/**
 * 用于设置当前请求应该使用什么dataSource的filter
 * 
 * @author Roy Huang
 * @version 20120709
 * @deprecated see org.developerworld.commons.dbsource
 */
public abstract class AbstractDynamicDataSourceFilter extends AbstractUrlFilter {

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
		String oldKey = DynamicDataSourceHolder.getDataSourceKey();
		try{
			DynamicDataSourceHolder.setDataSourceKey(dataSourceKey);
			arg2.doFilter(arg0, arg1);
		}
		finally{
			DynamicDataSourceHolder.setDataSourceKey(oldKey);
		}
	}

	/**
	 * 子类需要实现的根据请求上下文设置dataSource关键字方法
	 * 
	 * @param request
	 */
	abstract public String getDataSourceKey(HttpServletRequest request);

}
