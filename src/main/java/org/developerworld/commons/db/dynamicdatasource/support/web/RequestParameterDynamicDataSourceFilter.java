package org.developerworld.commons.db.dynamicdatasource.support.web;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

/**
 * 根据请求参数决定数据源的filter
 * 
 * @author Roy Huang
 * @version 20120709
 * @deprecated see org.developerworld.commons.dbsource
 */
public class RequestParameterDynamicDataSourceFilter extends
		AbstractDynamicDataSourceFilter {

	private String parameterName;

	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}

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
		if (StringUtils.isNotBlank(parameterName))
			rst = request.getParameter(parameterName);
		if (StringUtils.isNotBlank(rst))
			return rst.trim();
		else
			return null;
	}

}
