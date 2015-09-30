package org.developerworld.tools.search;

import java.io.IOException;
import java.util.List;

import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.Query;

/**
 * 
 * 搜索服务
 * 
 * @author Roy Huang
 * @version 20130110
 * 
 *@deprecated
 *@see org.developerworld.frameworks.lucene project
 * @param <T>
 */
public interface SearchService<T> {

	/**
	 * 获取条目数
	 * 
	 * @param callback
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	public int findCount(IndexReaderCallback<Integer> callback)
			throws IOException, ParseException;

	/**
	 * 获取列表
	 * 
	 * @param callback
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	public List<T> findList(IndexReaderCallback<List<T>> callback)
			throws IOException, ParseException;

	/**
	 * 获取一个数据
	 * 
	 * @param callback
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	public T findSingle(IndexReaderCallback<T> callback) throws IOException,
			ParseException;

	/**
	 * 获取条目数
	 * 
	 * @param callback
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	public int findCount(IndexSearcherCallback<Integer> callback)
			throws IOException, ParseException;

	/**
	 * 获取列表
	 * 
	 * @param callback
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	public List<T> findList(IndexSearcherCallback<List<T>> callback)
			throws IOException, ParseException;

	/**
	 * 获取一个数据
	 * 
	 * @param callback
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	public T findSingle(IndexSearcherCallback<T> callback) throws IOException,
			ParseException;

	/**
	 * 获取搜索条目数
	 * 
	 * @param queryField
	 * @param queryValue
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	public int findCount(String queryField, String queryValue)
			throws IOException, ParseException;

	/**
	 * 获取搜索条目数
	 * 
	 * @param queryFields
	 * @param queryValue
	 * @param occurs
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	public int findCount(String[] queryFields, String queryValue, Occur[] occurs)
			throws IOException, ParseException;

	/**
	 * 获取搜索条目数
	 * 
	 * @param queryFields
	 * @param queryValues
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	public int findCount(String[] queryFields, String[] queryValues)
			throws IOException, ParseException;

	/**
	 * 获取搜索条目数
	 * 
	 * @param queryFields
	 * @param queryValues
	 * @param occurs
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	public int findCount(String[] queryFields, String[] queryValues,
			Occur[] occurs) throws IOException, ParseException;

	/**
	 * 获取搜索条目数
	 * 
	 * @param query
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	public int findCount(Query query) throws IOException, ParseException;

	/**
	 * 获取搜索记录
	 * 
	 * @param queryField
	 * @param queryValue
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	public List<T> findList(String queryField, String queryValue, int pageNum,
			int pageSize) throws IOException, ParseException;

	/**
	 * 查找数据列表
	 * 
	 * @param queryFields
	 * @param queryValue
	 * @param occurs
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	public List<T> findList(String[] queryFields, String queryValue,
			Occur[] occurs, Integer pageNum, Integer pageSize)
			throws IOException, ParseException;

	/**
	 * 查找数据列表
	 * 
	 * @param queryFields
	 * @param queryValues
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	public List<T> findList(String[] queryFields, String[] queryValues,
			Integer pageNum, Integer pageSize) throws IOException,
			ParseException;

	/**
	 * 查找数据列表
	 * 
	 * @param queryFields
	 * @param queryValues
	 * @param occurs
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	public List<T> findList(String[] queryFields, String[] queryValues,
			Occur[] occurs, Integer pageNum, Integer pageSize)
			throws IOException, ParseException;

	/**
	 * 查找数据列表
	 * 
	 * @param query
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	public List<T> findList(Query query, Integer pageNum, Integer pageSize)
			throws IOException, ParseException;

	/**
	 * 查找一个数据
	 * 
	 * @param queryField
	 * @param queryValue
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	public T findSingle(String queryField, String queryValue)
			throws IOException, ParseException;

	/**
	 * 查找一个数据
	 * 
	 * @param queryFields
	 * @param queryValue
	 * @param occurs
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	public T findSingle(String[] queryFields, String queryValue, Occur[] occurs)
			throws IOException, ParseException;

	/**
	 * 查找一个数据
	 * 
	 * @param queryFields
	 * @param queryValues
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	public T findSingle(String[] queryFields, String[] queryValues)
			throws IOException, ParseException;

	/**
	 * 查找一个数据
	 * 
	 * @param queryFields
	 * @param queryValues
	 * @param occurs
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	public T findSingle(String[] queryFields, String[] queryValues,
			Occur[] occurs) throws IOException, ParseException;

	/**
	 * 查找一个数据
	 * 
	 * @param query
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	public T findSingle(Query query) throws IOException, ParseException;
}