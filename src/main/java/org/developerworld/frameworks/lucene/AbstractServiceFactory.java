package org.developerworld.frameworks.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.util.Version;
import org.developerworld.tools.search.DataEach;
import org.developerworld.tools.search.DocumentConverter;
import org.developerworld.tools.search.ServiceFactory;
import org.developerworld.tools.search.SortBuilder;
import org.developerworld.tools.search.TermBuilder;

/**
 * 抽象服务工厂
 * 
 * @author Roy Huang
 * @version 20120905
 * @deprecated see org.developerworld.frameworks.luncene.impl
 */
public abstract class AbstractServiceFactory<T> implements ServiceFactory<T> {

	// 索引保存路径
	private String indexPath;
	// 建索引所使用的版本
	private Version version;
	// 建立索引使用的分析系
	private Analyzer analyzer;
	// 文档转换器
	private DocumentConverter<T> documentConverter;
	// 标识创建器
	private TermBuilder<T> termBuilder;
	//数据遍历器
	private DataEach<T> dataEach;
	// 排序创建器
	private SortBuilder sortBuilder;

	public String getIndexPath() {
		return indexPath;
	}

	public void setIndexPath(String indexPath) {
		this.indexPath = indexPath;
	}

	public Analyzer getAnalyzer() {
		return analyzer;
	}

	public void setAnalyzer(Analyzer analyzer) {
		this.analyzer = analyzer;
	}

	public Version getVersion() {
		return version;
	}

	public void setVersion(Version version) {
		this.version = version;
	}

	public DocumentConverter<T> getDocumentConverter() {
		return documentConverter;
	}

	public void setDocumentConverter(DocumentConverter<T> documentConverter) {
		this.documentConverter = documentConverter;
	}

	public TermBuilder<T> getTermBuilder() {
		return termBuilder;
	}

	public void setTermBuilder(TermBuilder<T> termBuilder) {
		this.termBuilder = termBuilder;
	}

	public DataEach<T> getDataEach() {
		return dataEach;
	}

	public void setDataEach(DataEach<T> dataEach) {
		this.dataEach = dataEach;
	}

	public SortBuilder getSortBuilder() {
		return sortBuilder;
	}

	public void setSortBuilder(SortBuilder sortBuilder) {
		this.sortBuilder = sortBuilder;
	}

}
