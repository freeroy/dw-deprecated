package org.developerworld.frameworks.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.util.Version;
import org.developerworld.tools.search.DataEach;
import org.developerworld.tools.search.DocumentConverter;
import org.developerworld.tools.search.IndexService;
import org.developerworld.tools.search.TermBuilder;

/**
 * 抽象索引服务
 * 
 * @author Roy Huang
 * @version 20120905
 * @deprecated see org.developerworld.frameworks.luncene.impl
 */
public abstract class AbstractIndexService<T> implements IndexService<T> {

	// 索引保存路径
	private String indexPath;
	// 建索引所使用的版本
	private Version version;
	// 建立索引使用的分析系
	private Analyzer analyzer;
	// 标识创建器
	private TermBuilder<T> termBuilder;
	// 文档转换器
	private DocumentConverter<T> documentConverter;
	//数据遍历器
	private DataEach<T> dataEach;

	public void setAnalyzer(Analyzer analyzer) {
		this.analyzer = analyzer;
	}

	public Analyzer getAnalyzer() {
		return this.analyzer;
	}

	public void setVersion(Version version) {
		this.version = version;
	}

	public Version getVersion() {
		return this.version;
	}

	public void setTermBuilder(TermBuilder<T> termBuilder) {
		this.termBuilder = termBuilder;
	}

	public TermBuilder<T> getTermBuilder() {
		return termBuilder;
	}

	public String getIndexPath() {
		return indexPath;
	}

	public void setIndexPath(String indexPath) {
		this.indexPath = indexPath;
	}

	public DocumentConverter<T> getDocumentConverter() {
		return documentConverter;
	}

	public void setDocumentConverter(DocumentConverter<T> documentConverter) {
		this.documentConverter = documentConverter;
	}

	public DataEach<T> getDataEach() {
		return dataEach;
	}

	public void setDataEach(DataEach<T> dataEach) {
		this.dataEach = dataEach;
	}
}
