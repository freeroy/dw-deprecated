package org.developerworld.jmvc.framework.spring.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.developerworld.jmvc.WebManager;
import org.developerworld.jmvc.filter.AbstractJspMvcFilter;
import org.developerworld.jmvc.framework.spring.SpringWebManager;

/**
 * jspMvc过滤器
 * 
 * @author Roy Huang
 * @version 20121218
 * 
 * @deprecated
 * @see org.developerworld.frameworks.jmvc project
 */
public class SpringJspMvcFilter extends AbstractJspMvcFilter {

	@Override
	protected WebManager buildWebManager(HttpServletRequest request,
			HttpServletResponse response) {
		return new SpringWebManager(request, response);
	}

}
