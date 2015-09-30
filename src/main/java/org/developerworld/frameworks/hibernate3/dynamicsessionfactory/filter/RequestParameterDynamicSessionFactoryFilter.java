package org.developerworld.frameworks.hibernate3.dynamicsessionfactory.filter;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

/**
 * 根据请求参数动态切换SessionFactory
 * 
 * @author Roy Huang
 * @version 20110224
 * @deprecated
 */
public class RequestParameterDynamicSessionFactoryFilter extends
		AbstractDynamicSessionFactoryFilter {
	
	private String parameterName;

	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}

	@Override
	public String getSessionFactoryKey(HttpServletRequest request) {
		String rst = null;
		if (parameterName != null)
			rst = request.getParameter(parameterName);
		if(rst!=null)
			rst=rst.trim();
		return StringUtils.isEmpty(rst)?null:rst;
	}

}
