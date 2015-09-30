package org.developerworld.frameworks.lucene;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.developerworld.tools.search.IndexReaderCallback;
import org.developerworld.tools.search.IndexSearcherCallback;
import org.developerworld.tools.search.SortBuilder;

/**
 * 默认搜索服务
 * 
 * @author Roy Huang
 * @version 20120906
 * @deprecated see org.developerworld.frameworks.luncene.impl
 */
public class SimpleSearchService<T> extends AbstractSearchService<T> {

	/**
	 * 初始化参数
	 */
	private void initConfig() {
		if (getVersion() == null)
			throw new IllegalArgumentException("version is null");
		if (getAnalyzer() == null)
			throw new IllegalArgumentException("analyzer is null");
		if (getDocumentConverter() == null)
			throw new IllegalArgumentException("documentConverter is null");
		if (StringUtils.isBlank(getIndexPath()))
			throw new IllegalArgumentException("indexPath is blank");
	}

	/**
	 * 船舰索引读取器
	 * 
	 * @return
	 * @throws IOException
	 * @throws CorruptIndexException
	 */
	private IndexReader buildIndexReader() throws IOException,
			CorruptIndexException {
		// 获取目录
		Directory dir = FSDirectory.open(new File(getIndexPath()));
		// 创建索引阅读器
		IndexReader reader = IndexReader.open(dir);
		return reader;
	}

	/**
	 * 关闭索引读取器
	 * 
	 * @param reader
	 * @throws IOException
	 */
	private void closeIndexReader(IndexReader reader) throws IOException {
		if (reader != null)
			reader.close();
	}

	public int findCount(IndexReaderCallback<Integer> callback)
			throws IOException, ParseException {
		return find(callback);
	}

	public List<T> findList(IndexReaderCallback<List<T>> callback)
			throws IOException, ParseException {
		return find(callback);
	}

	public T findSingle(IndexReaderCallback<T> callback) throws IOException,
			ParseException {
		return find(callback);
	}

	/**
	 * 查找数据
	 * 
	 * @param callback
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	private <J> J find(IndexReaderCallback<J> callback) throws IOException,
			ParseException {
		IndexReader reader = null;
		try {
			initConfig();
			reader = buildIndexReader();
			return callback.doInIndexReader(reader);
		} finally {
			// 关闭索引读取器
			closeIndexReader(reader);
		}
	}

	public int findCount(IndexSearcherCallback<Integer> callback)
			throws IOException, ParseException {
		return find(callback);
	}

	public List<T> findList(IndexSearcherCallback<List<T>> callback)
			throws IOException, ParseException {
		return find(callback);
	}

	public T findSingle(IndexSearcherCallback<T> callback) throws IOException,
			ParseException {
		return find(callback);
	}

	/**
	 * 查找数据
	 * 
	 * @param callback
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	private <J> J find(IndexSearcherCallback<J> callback) throws IOException,
			ParseException {
		IndexReader reader = null;
		IndexSearcher searcher = null;
		try {
			initConfig();
			reader = buildIndexReader();
			// 创建索引搜索器
			searcher = new IndexSearcher(reader);
			return callback.doInIndexSearcher(searcher);
		} finally {
			if (searcher != null)
				searcher.close();
			// 关闭索引读取器
			closeIndexReader(reader);
		}
	}

	public int findCount(final String queryField, final String queryValue)
			throws IOException, ParseException {
		if (StringUtils.isBlank(queryField))
			throw new IllegalArgumentException(
					"the queryField can not be blank");
		return findCount(new IndexSearcherCallback<Integer>() {
			public Integer doInIndexSearcher(IndexSearcher indexSearcher)
					throws IOException, ParseException {
				// 创建查询分析器
				QueryParser parser = new QueryParser(getVersion(), queryField,
						getAnalyzer());
				// 若无检索条件则调整表达式
				String _queryValue = StringUtils.isBlank(queryValue) ? "*:*"
						: queryValue;
				// 创建查询对象
				Query query = parser.parse(_queryValue);
				return findCount(query);
			}
		});
	}

	public int findCount(final String[] queryFields, final String queryValue,
			final Occur[] occurs) throws IOException, ParseException {
		if (ArrayUtils.isEmpty(queryFields))
			throw new IllegalArgumentException(
					"the queryFields can not be empty");
		else if (occurs != null && occurs.length != queryFields.length)
			throw new IllegalArgumentException(
					"the occurs length must equals queryFields length");
		return findCount(new IndexSearcherCallback<Integer>() {
			public Integer doInIndexSearcher(IndexSearcher indexSearcher)
					throws IOException, ParseException {
				// 若无检索条件则调整表达式
				String _queryValue = StringUtils.isBlank(queryValue) ? "*:*"
						: queryValue;
				// 创建查询对象
				Query query = MultiFieldQueryParser.parse(getVersion(),
						_queryValue, queryFields, occurs, getAnalyzer());
				return findCount(query);
			}
		});
	}

	public int findCount(final String[] queryFields, final String[] queryValues)
			throws IOException, ParseException {
		return findCount(queryFields, queryValues, null);
	}

	public int findCount(final String[] queryFields,
			final String[] queryValues, final Occur[] occurs)
			throws IOException, ParseException {
		if (ArrayUtils.isEmpty(queryFields))
			throw new IllegalArgumentException(
					"the queryFields can not be empty");
		else if (ArrayUtils.isEmpty(queryValues))
			throw new IllegalArgumentException(
					"the queryValues can not be empty");
		else if (queryFields.length != queryValues.length)
			throw new IllegalArgumentException(
					"the queryValues length must equals queryFields length");
		else if (occurs != null && occurs.length != queryFields.length)
			throw new IllegalArgumentException(
					"the occurs length must equals queryFields length");
		return findCount(new IndexSearcherCallback<Integer>() {
			public Integer doInIndexSearcher(IndexSearcher indexSearcher)
					throws IOException, ParseException {
				// 若无检索条件则调整表达式
				for (int i = 0; i < queryValues.length; i++)
					if (StringUtils.isBlank(queryValues[i]))
						queryValues[i] = "*:*";
				// 创建查询对象
				Query query = null;
				if (occurs != null)
					query = MultiFieldQueryParser.parse(getVersion(),
							queryValues, queryFields, occurs, getAnalyzer());
				else
					query = MultiFieldQueryParser.parse(getVersion(),
							queryValues, queryFields, getAnalyzer());
				return findCount(query);
			}
		});
	}

	public int findCount(final Query query) throws IOException, ParseException {
		return findCount(new IndexSearcherCallback<Integer>() {
			public Integer doInIndexSearcher(IndexSearcher indexSearcher)
					throws IOException, ParseException {
				// 进行检索
				TopDocs results = indexSearcher.search(query, 1);
				return results.totalHits;
			}
		});
	}

	public List<T> findList(final String queryField, final String queryValue,
			final int pageNum, final int pageSize) throws IOException,
			ParseException {
		if (StringUtils.isBlank(queryField))
			throw new IllegalArgumentException(
					"the queryField can not be blank");
		return findList(new IndexSearcherCallback<List<T>>() {
			public List<T> doInIndexSearcher(IndexSearcher indexSearcher)
					throws IOException, ParseException {
				// 创建查询分析器
				QueryParser parser = new QueryParser(getVersion(), queryField,
						getAnalyzer());
				// 若无检索条件则调整表达式
				String _queryValue = StringUtils.isBlank(queryValue) ? "*:*"
						: queryValue;
				// 创建查询对象
				Query query = parser.parse(_queryValue);
				return findList(query, pageNum, pageSize);
			}
		});
	}

	public List<T> findList(final String[] queryFields,
			final String queryValue, final Occur[] occurs,
			final Integer pageNum, final Integer pageSize) throws IOException,
			ParseException {
		if (ArrayUtils.isEmpty(queryFields))
			throw new IllegalArgumentException(
					"the queryFields can not be empty");
		else if (occurs != null && occurs.length != queryFields.length)
			throw new IllegalArgumentException(
					"the occurs length must equals queryFields length");
		return findList(new IndexSearcherCallback<List<T>>() {
			public List<T> doInIndexSearcher(IndexSearcher indexSearcher)
					throws IOException, ParseException {
				// 若无检索条件则调整表达式
				String _queryValue = StringUtils.isBlank(queryValue) ? "*:*"
						: queryValue;
				// 创建查询对象
				Query query = MultiFieldQueryParser.parse(getVersion(),
						_queryValue, queryFields, occurs, getAnalyzer());
				return findList(query, pageNum, pageSize);
			}
		});
	}

	public List<T> findList(final String[] queryFields,
			final String[] queryValues, final Integer pageNum,
			final Integer pageSize) throws IOException, ParseException {
		return findList(queryFields, queryValues, null, pageNum, pageSize);
	}

	public List<T> findList(final String[] queryFields,
			final String[] queryValues, final Occur[] occurs,
			final Integer pageNum, final Integer pageSize) throws IOException,
			ParseException {
		if (ArrayUtils.isEmpty(queryFields))
			throw new IllegalArgumentException(
					"the queryFields can not be empty");
		else if (ArrayUtils.isEmpty(queryValues))
			throw new IllegalArgumentException(
					"the queryValues can not be empty");
		else if (queryFields.length != queryValues.length)
			throw new IllegalArgumentException(
					"the queryValues length must equals queryFields length");
		else if (occurs != null && occurs.length != queryFields.length)
			throw new IllegalArgumentException(
					"the occurs length must equals queryFields length");
		return findList(new IndexSearcherCallback<List<T>>() {
			public List<T> doInIndexSearcher(IndexSearcher indexSearcher)
					throws IOException, ParseException {
				// 若无检索条件则调整表达式
				for (int i = 0; i < queryValues.length; i++)
					if (StringUtils.isBlank(queryValues[i]))
						queryValues[i] = "*:*";
				// 创建查询对象
				Query query = null;
				if (occurs != null)
					query = MultiFieldQueryParser.parse(getVersion(),
							queryValues, queryFields, occurs, getAnalyzer());
				else
					query = MultiFieldQueryParser.parse(getVersion(),
							queryValues, queryFields, getAnalyzer());
				return findList(query, pageNum, pageSize);
			}
		});
	}

	public List<T> findList(final Query query, final Integer pageNum,
			final Integer pageSize) throws IOException, ParseException {
		if (pageNum <= 0)
			throw new IllegalArgumentException("pageNum must >=1");
		else if (pageSize <= 0)
			throw new IllegalArgumentException("pageSize must >=1");
		return findList(new IndexSearcherCallback<List<T>>() {
			public List<T> doInIndexSearcher(IndexSearcher indexSearcher)
					throws IOException, ParseException {
				List<T> rst = new ArrayList<T>();
				// 进行检索
				TopDocs results = null;
				Sort sort = null;
				SortBuilder sortBuilder = getSortBuilder();
				if (sortBuilder != null)
					sort = sortBuilder.buildSort();
				if (sort == null)
					results = indexSearcher.search(query, pageNum * pageSize);
				else
					results = indexSearcher.search(query, pageNum * pageSize,
							sort);
				int total = results.totalHits;
				ScoreDoc[] scoreDocs = results.scoreDocs;
				int begin = (pageNum - 1) * pageSize;
				if (begin < total) {
					int end = Math.min(pageNum * pageSize, total);
					for (int i = begin; i < end; i++) {
						ScoreDoc scoreDoc = scoreDocs[i];
						Document doc = indexSearcher.doc(scoreDoc.doc);
						T object = getDocumentConverter().documentToData(doc);
						rst.add(object);
					}
				}
				return rst;
			}
		});
	}

	public T findSingle(String queryField, String queryValue)
			throws IOException, ParseException {
		List<T> rst = findList(queryField, queryValue, 1, 1);
		return rst.size() > 0 ? rst.get(0) : null;
	}

	public T findSingle(String[] queryFields, String queryValue, Occur[] occurs)
			throws IOException, ParseException {
		List<T> rst = findList(queryFields, queryValue, occurs, 1, 1);
		return rst.size() > 0 ? rst.get(0) : null;
	}

	public T findSingle(String[] queryFields, String[] queryValues)
			throws IOException, ParseException {
		return findSingle(queryFields, queryValues, null);
	}

	public T findSingle(String[] queryFields, String[] queryValues,
			Occur[] occurs) throws IOException, ParseException {
		List<T> rst = findList(queryFields, queryValues, occurs, 1, 1);
		return rst.size() > 0 ? rst.get(0) : null;
	}

	public T findSingle(Query query) throws IOException, ParseException {
		List<T> rst = findList(query, 1, 1);
		return rst.size() > 0 ? rst.get(0) : null;
	}
}
