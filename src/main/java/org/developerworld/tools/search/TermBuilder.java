package org.developerworld.tools.search;

import org.apache.lucene.index.Term;

/**
 * 
 * 标识生成器
 * @author Roy Huang
 * @version 20130106
 *
 * @param <T>
 *@deprecated
 *@see org.developerworld.frameworks.lucene project
 */
public interface TermBuilder<T> {

	/**
	 * 创建标识
	 * @param data
	 * @return
	 */
	public Term buildTerm(T data);

}
