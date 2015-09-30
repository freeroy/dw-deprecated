package org.developerworld.tools.search;

import java.io.IOException;

/**
 * 索引服务
 * @author Roy Huang
 * @version 20120822
 *
 *@deprecated
 *@see org.developerworld.frameworks.lucene project
 * @param <T>
 */
public interface IndexService<T> {

	/**
	 * 重建索引
	 * 
	 * @throws Exception 
	 */
	public void createIndexAll() throws Exception;
	
	/**
	 * 创建/追加索引
	 * @param data
	 * @throws IOException
	 */
	public void createIndex(T data) throws IOException;

	/**
	 * 更新所有索引
	 * 
	 * @throws Exception 
	 */
	public void updateIndexAll() throws Exception;

	/**
	 * 更新索引
	 * @param data
	 * @throws IOException
	 */
	public void updateIndex(T data) throws IOException;

	/**
	 * 删除索引
	 * @param data
	 * @throws IOException
	 */
	public void deleteIndex(T data) throws IOException;

	/**
	 * 删除所有索引
	 * 
	 * @throws IOException
	 */
	public void deleteIndexAll() throws IOException;
}
