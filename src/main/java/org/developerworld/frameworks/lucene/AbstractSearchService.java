package org.developerworld.frameworks.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.util.Version;
import org.developerworld.tools.search.DocumentConverter;
import org.developerworld.tools.search.SearchService;
import org.developerworld.tools.search.SortBuilder;

/**
 * 抽象搜索服务
 * 
 * @author Roy Huang
 * @version 20120822
 * 
 * @param <T>
 * @deprecated see org.developerworld.frameworks.luncene.impl
 */
public abstract class AbstractSearchService<T> implements SearchService<T> {

	// 索引保存路径
	private String indexPath;
	// 建立索引使用的分析系
	private Analyzer analyzer;
	// 建索引所使用的版本
	private Version version;
	// 排序创建器
	private SortBuilder sortBuilder;
	// 文档转换器
	private DocumentConverter<T> documentConverter;

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

	public SortBuilder getSortBuilder() {
		return sortBuilder;
	}

	public void setSortBuilder(SortBuilder sortBuilder) {
		this.sortBuilder = sortBuilder;
	}

}
