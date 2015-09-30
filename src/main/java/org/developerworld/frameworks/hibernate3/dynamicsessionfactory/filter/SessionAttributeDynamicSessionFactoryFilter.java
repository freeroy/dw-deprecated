package org.developerworld.frameworks.hibernate3.dynamicsessionfactory.filter;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

/**
 * 根据会话参数决定SessionFactory
 * 
 * @author Roy Huang
 * @version 20110425
 * @deprecated
 */
public class SessionAttributeDynamicSessionFactoryFilter extends
		AbstractDynamicSessionFactoryFilter {

	private String attributeName;

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	@Override
	public String getSessionFactoryKey(HttpServletRequest request) {
		String rst = null;
		if (attributeName != null)
			rst = (String) request.getSession().getAttribute(attributeName);
		if(rst!=null)
			rst=rst.trim();
		return StringUtils.isEmpty(rst)?null:rst;
	}

}
