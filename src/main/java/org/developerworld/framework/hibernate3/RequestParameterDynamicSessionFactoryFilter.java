package org.developerworld.framework.hibernate3;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

/**
 * 根据请求参数动态切换SessionFactory
 * 
 * @author Roy Huang
 * @version 20110224
 * @deprecated
 * @see org.developerworld.frameworks.hibernate3 project
 * 
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
