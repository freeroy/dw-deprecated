package org.developerworld.db.datasource;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

/**
 * 根据会话参数决定数据源的filter
 * 
 * @author Roy Huang
 * @version 20120709
 * @deprecated 
 * @see org.developerworld.commons.db project
 */
public class SessionAttributeDynamicDataSourceFilter extends
		AbstractDynamicDataSourceFilter {

	private String attributeName;

	@Override
	public void init(FilterConfig config) throws ServletException {
		super.init(config);
		attributeName = config.getInitParameter("attributeName");
	}

	@Override
	public void destroy() {
		attributeName = null;
		super.destroy();
	}

	public String getDataSourceKey(HttpServletRequest request) {
		String rst = null;
		if (attributeName != null)
			rst = (String) request.getSession().getAttribute(attributeName);
		if (rst != null)
			rst = rst.trim();
		return StringUtils.isBlank(rst) ? null : rst;
	}

}
