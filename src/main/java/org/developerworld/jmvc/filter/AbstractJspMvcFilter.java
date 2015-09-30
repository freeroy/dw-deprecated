package org.developerworld.jmvc.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.developerworld.jmvc.JMVCContext;
import org.developerworld.jmvc.WebManager;
import org.developerworld.jmvc.utils.WebManagerUtils;
import org.developerworld.servlet.AbstractUrlFilter;

/**
 * 抽象jspmvc过滤器
 * 
 * @author Roy Huang
 * @version 20121218
 * 
 * @deprecated
 * @see org.developerworld.frameworks.jmvc project
 */
public abstract class AbstractJspMvcFilter extends AbstractUrlFilter {

	private JMVCContext jmvcContext;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		super.init(filterConfig);
		jmvcContext = new JMVCContext(filterConfig.getServletContext());
		String tmp = filterConfig.getInitParameter("mappingConfig");
		if (StringUtils.isNotBlank(tmp))
			jmvcContext.setMappingConfig(tmp);
		tmp = filterConfig.getInitParameter("cacheTime");
		if (StringUtils.isNotBlank(tmp))
			jmvcContext.setCacheTime(Long.parseLong(tmp) * 1000);
	}

	@Override
	public void doFilterWhenUrlPass(ServletRequest request,
			ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest _request = (HttpServletRequest) request;
		HttpServletResponse _response = (HttpServletResponse) response;
		// 构造上下文
		WebManagerUtils.setCurrentWebManager(buildWebManager(_request,
				_response));
		try {
			jmvcContext.doFilter(_request, _response,filterChain);
		} finally {
			// 清除上下文
			WebManagerUtils.clear();
		}
	}

	/**
	 * 设置当前的web上下文
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	abstract protected WebManager buildWebManager(HttpServletRequest request,
			HttpServletResponse response);
}
