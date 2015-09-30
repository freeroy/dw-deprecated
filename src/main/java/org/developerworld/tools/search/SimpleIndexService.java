package org.developerworld.tools.search;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;
import org.developerworld.tools.search.DataEach.DataIndex;

/**
 * 默认索引服务
 * 
 * @author Roy Huang
 * @version 20120906
 * 
 *@deprecated
 *@see org.developerworld.frameworks.lucene project
 */
public class SimpleIndexService<T> extends AbstractIndexService<T> {

	/**
	 * 初始化参数
	 */
	private void initCheck() {
		if (getTermBuilder() == null)
			throw new IllegalArgumentException("termBuilder is null");
		else if (getDocumentConverter() == null)
			throw new IllegalArgumentException("documentConverter is null");
		else if (getDataEach() == null)
			throw new IllegalArgumentException("dataEach is null");
		else if (StringUtils.isBlank(getIndexPath()))
			throw new IllegalArgumentException("indexPath is blank");
	}

	/**
	 * 创建索引写入器
	 * 
	 * @param openMode
	 * @return
	 * @throws IOException
	 * @throws CorruptIndexException
	 * @throws LockObtainFailedException
	 */
	private IndexWriter buildIndexWriter(OpenMode openMode) throws IOException,
			CorruptIndexException, LockObtainFailedException {
		initCheck();
		// 获取目录
		Directory dir = FSDirectory.open(new File(getIndexPath()));
		IndexWriterConfig iwc = new IndexWriterConfig(getVersion(),
				getAnalyzer());
		// 设置打开索引目录模式
		iwc.setOpenMode(openMode);
		IndexWriter writer = new IndexWriter(dir, iwc);
		return writer;
	}

	/**
	 * 提交变更
	 * 
	 * @param indexWriter
	 * @throws CorruptIndexException
	 * @throws IOException
	 */
	private void commitIndexWriter(IndexWriter indexWriter)
			throws CorruptIndexException, IOException {
		if (indexWriter != null) {
			indexWriter.commit();
		}
	}

	/**
	 * 关闭索引
	 * 
	 * @param indexWriter
	 * @throws CorruptIndexException
	 * @throws IOException
	 */
	private void closeIndexWriter(IndexWriter indexWriter)
			throws CorruptIndexException, IOException {
		if (indexWriter != null) {
			indexWriter.close();
		}
	}

	public void createIndexAll() throws Exception {
		initCheck();
		final IndexWriter writer = buildIndexWriter(OpenMode.CREATE);
		try {
			DataEach<T> dataEach = getDataEach();
			dataEach.each(new DataIndex<T>() {
				public void index(T data) throws IOException {
					indexDocs(writer, data);
				}
			});
		} finally {
			// 提交变更
			commitIndexWriter(writer);
			// 关闭索引写入器
			closeIndexWriter(writer);
		}
	}

	public void createIndex(T data) throws IOException {
		initCheck();
		IndexWriter writer = buildIndexWriter(OpenMode.CREATE);
		try {
			indexDocs(writer, data);
		} finally {
			// 提交变更
			commitIndexWriter(writer);
			// 关闭索引写入器
			closeIndexWriter(writer);
		}
	}

	public void updateIndexAll() throws Exception {
		initCheck();
		final IndexWriter writer = buildIndexWriter(OpenMode.CREATE_OR_APPEND);
		try {
			DataEach<T> dataEach = getDataEach();
			dataEach.each(new DataIndex<T>() {
				public void index(T data) throws IOException {
					indexDocs(writer, data);
				}
			});
		} finally {
			// 提交变更
			commitIndexWriter(writer);
			// 关闭索引写入器
			closeIndexWriter(writer);
		}
	}

	public void updateIndex(T object) throws IOException {
		initCheck();
		IndexWriter writer = buildIndexWriter(OpenMode.CREATE_OR_APPEND);
		try {
			indexDocs(writer, object);
		} finally {
			// 提交变更
			commitIndexWriter(writer);
			// 关闭索引写入器
			closeIndexWriter(writer);
		}
	}

	/**
	 * 执行文档索引
	 * 
	 * @param writer
	 * @param data
	 * @throws IOException
	 */
	private void indexDocs(IndexWriter writer, T data) throws IOException {
		Document doc = getDocumentConverter().dataToDocument(data);
		if (doc != null) {
			if (writer.getConfig().getOpenMode().equals(OpenMode.CREATE))
				writer.addDocument(doc);
			else
				writer.updateDocument(getTermBuilder().buildTerm(data), doc);
		}
	}

	public void deleteIndex(T data) throws IOException {
		initCheck();
		IndexWriter writer = buildIndexWriter(OpenMode.CREATE_OR_APPEND);
		try {
			writer.deleteDocuments(getTermBuilder().buildTerm(data));
		} finally {
			// 提交变更
			commitIndexWriter(writer);
			// 关闭索引写入器
			closeIndexWriter(writer);
		}
	}

	public void deleteIndexAll() throws IOException {
		initCheck();
		IndexWriter writer = buildIndexWriter(OpenMode.CREATE_OR_APPEND);
		try {
			writer.deleteAll();
		} finally {
			// 提交变更
			commitIndexWriter(writer);
			// 关闭索引写入器
			closeIndexWriter(writer);
		}
	}
}
