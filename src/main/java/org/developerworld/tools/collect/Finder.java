package org.developerworld.tools.collect;

import java.util.List;

/**
 * 查找器
 * 
 * @author Roy Huang
 * @version 20121210
 *@deprecated
 *@see org.developerworld.commons.collect project
 * 
 */
public interface Finder {
	
	/**
	 * 查找数据
	 * @param content
	 * @return
	 */
	public List<String> find(String content);
	
}
