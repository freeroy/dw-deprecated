package org.developerworld.framework.hibernate3;

import java.util.HashMap;
import java.util.Map;

/**
 * hibernate 命名参数类
 * @author Roy Huang
 * @version 20101107
 * @deprecated
 * @see org.developerworld.frameworks.hibernate3 project
 *
 */
public class HibernateParams extends HashMap<String,Object> implements Map<String,Object>{

	public HibernateParams(){
		
	}
	
	public HibernateParams(String name,Object value){
		this.put(name, value);
	}
	
	/**
	 * 设置参数
	 * @param name
	 * @param value
	 * @return
	 */
	public HibernateParams set(String name,Object value){
		put(name,value);
		return this;
	}
}
