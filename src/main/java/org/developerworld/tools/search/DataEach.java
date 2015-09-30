package org.developerworld.tools.search;

import java.io.IOException;

/**
 * 数据遍历器
 * @author Roy Huang
 * @version 20120907
 *
 *@deprecated
 *@see org.developerworld.frameworks.lucene project
 * @param <T>
 */
public interface DataEach<T> {

	/**
	 * 遍历数据
	 * 
	 * @param dataIndex
	 * @throws Exception
	 */
	public void each(DataIndex<T> dataIndex) throws Exception;

	/**
	 * 数据索引器
	 * @author Roy Huang
	 * @version 20120907
	 *
	 * @param <T>
	 */
	public interface DataIndex<T> {
		
		/**
		 * 索引数据
		 * 
		 * @param data
		 * @throws IOException
		 */
		public void index(T data) throws IOException;
		
	}
}
