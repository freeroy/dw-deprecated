package org.developerworld.jmvc.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.developerworld.jmvc.WebManager;

/**
 * jspMvc过滤器
 * 
 * @author Roy Huang
 * @version 20121218
 * 
 * @deprecated
 * @see org.developerworld.frameworks.jmvc project
 */
public class JspMvcFilter extends AbstractJspMvcFilter {

	@Override
	protected WebManager buildWebManager(HttpServletRequest request,
			HttpServletResponse response) {
		return new WebManager(request,response);
	}

}
