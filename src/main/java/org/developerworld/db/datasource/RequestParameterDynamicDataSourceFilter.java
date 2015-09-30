package org.developerworld.db.datasource;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

/**
 * 根据请求参数决定数据源的filter
 * 
 * @author Roy Huang
 * @version 20120709
 * @deprecated 
 * @see org.developerworld.commons.db project
 */
public class RequestParameterDynamicDataSourceFilter extends
		AbstractDynamicDataSourceFilter {

	private String parameterName;

	@Override
	public void init(FilterConfig config) throws ServletException {
		super.init(config);
		parameterName = config.getInitParameter("parameterName");
	}

	@Override
	public void destroy() {
		parameterName = null;
		super.destroy();
	}

	public String getDataSourceKey(HttpServletRequest request) {
		String rst = null;
		if (parameterName != null)
			rst = request.getParameter(parameterName);
		if (rst != null)
			rst = rst.trim();
		return StringUtils.isBlank(rst) ? null : rst;
	}

}
