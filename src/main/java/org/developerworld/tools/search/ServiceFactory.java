package org.developerworld.tools.search;

/**
 * 
 * 服务工厂
 * 
 * @author Roy Huang
 * @version 20120822
 *
 *@deprecated
 *@see org.developerworld.frameworks.lucene project
 * @param <T>
 */
public interface ServiceFactory<T> {

	/**
	 * 创建搜索服务
	 * 
	 * @return
	 */
	SearchService<T> buildSearchService();

	/**
	 * 创建索引服务
	 * 
	 * @return
	 */
	IndexService<T> buildIndexService();

}
