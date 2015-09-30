package org.developerworld.tools.search;

import org.apache.lucene.search.Sort;

/**
 * 排序创建器
 * @author Roy Huang
 * @version 20120823
 *
 *@deprecated
 *@see org.developerworld.frameworks.lucene project
 */
public interface SortBuilder {

	public Sort buildSort();
	
}
