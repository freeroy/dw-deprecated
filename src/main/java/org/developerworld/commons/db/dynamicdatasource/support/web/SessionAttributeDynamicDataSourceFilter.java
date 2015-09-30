package org.developerworld.commons.db.dynamicdatasource.support.web;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

/**
 * 根据会话参数决定数据源的filter
 * 
 * @author Roy Huang
 * @version 20120709
 * @deprecated see org.developerworld.commons.dbsource
 */
public class SessionAttributeDynamicDataSourceFilter extends
		AbstractDynamicDataSourceFilter {

	private String attributeName;

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

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
		HttpSession session = request.getSession(false);
		if (StringUtils.isNotBlank(attributeName) && session != null)
			rst = (String) session.getAttribute(attributeName);
		if(StringUtils.isNotBlank(rst))
			return rst.trim();
		else
			return null;
	}

}
