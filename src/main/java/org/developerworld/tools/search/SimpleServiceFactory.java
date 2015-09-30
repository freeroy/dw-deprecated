package org.developerworld.tools.search;

import org.apache.commons.lang.StringUtils;

/**
 * 默认服务工厂
 * 
 * @author Roy Huang
 * @version 20120906
 * 
 *@deprecated
 *@see org.developerworld.frameworks.lucene project
 */
public class SimpleServiceFactory<T> extends AbstractServiceFactory<T> {

	/**
	 * 初始化
	 */
	private void initCheck() {
		if (getAnalyzer() == null)
			throw new IllegalArgumentException("analyzer is null");
		if (getVersion() == null)
			throw new IllegalArgumentException("version is null");
		if (getDocumentConverter() == null)
			throw new IllegalArgumentException("documentConverter is null");
		else if (getTermBuilder() == null)
			throw new IllegalArgumentException("termBuilder is null");
		else if (getDataEach() == null)
			throw new IllegalArgumentException("dataEach is null");
		if (StringUtils.isBlank(getIndexPath()))
			throw new IllegalArgumentException("indexPath is blank");
	}

	/**
	 * 初始化搜索服务
	 * 
	 * @param searchService
	 */
	private void initSearchService(SimpleSearchService<T> searchService) {
		searchService.setAnalyzer(getAnalyzer());
		searchService.setDocumentConverter(getDocumentConverter());
		searchService.setIndexPath(getIndexPath());
		searchService.setVersion(getVersion());
		searchService.setSortBuilder(getSortBuilder());
	}

	/**
	 * 初始化索引服务
	 * 
	 * @param indexService
	 */
	private void initIndexService(SimpleIndexService<T> indexService) {
		indexService.setAnalyzer(getAnalyzer());
		indexService.setDocumentConverter(getDocumentConverter());
		indexService.setIndexPath(getIndexPath());
		indexService.setVersion(getVersion());
		indexService.setTermBuilder(getTermBuilder());
		indexService.setDataEach(getDataEach());
	}

	public SearchService<T> buildSearchService() {
		initCheck();
		SimpleSearchService<T> searchService = new SimpleSearchService<T>();
		initSearchService(searchService);
		return searchService;
	}

	public SimpleIndexService<T> buildIndexService() {
		initCheck();
		SimpleIndexService<T> indexService = new SimpleIndexService<T>();
		initIndexService(indexService);
		return indexService;
	}

}
