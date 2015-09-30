package org.developerworld.framework.struts2.interceptor;

import org.developerworld.framework.struts2.action.AbstractCrudActionSupport;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * 自动执行AbstractCrudActionSupport的initParams方法
 * @author Roy Huang
 * @version 20101114
 *
 * @deprecated
 * @see org.developerworld.frameworks.struts2 project
 */
public class CrudActionSupportInterceptor extends AbstractInterceptor{

	@Override
	public String intercept(ActionInvocation arg0) throws Exception {
		if(arg0.getAction() instanceof AbstractCrudActionSupport){
			((AbstractCrudActionSupport)arg0.getAction()).initParams();
		}	
		return arg0.invoke();
	}

}
