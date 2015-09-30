package org.developerworld.tools.collect.datahandler;

import java.util.List;
import java.util.Map;

import org.developerworld.tools.collect.ContentProvider;
import org.developerworld.tools.collect.DataHandler;
import org.developerworld.tools.collect.Finder;

/**
 * 多重采集数据处理器
 * 
 * @author Roy Huang
 * @version 20121211
 *@deprecated
 *@see org.developerworld.commons.collect project
 * 
 */
public class SimpleMultipleCollecterDataHandler extends
		AbstractMultipleCollecterDataHandler {

	private ContentProviderBuilder contentProviderBuilder;
	private FindersBuilder findersBuilder;
	private DataHandler dataHandler;

	public SimpleMultipleCollecterDataHandler(
			ContentProviderBuilder contentProviderBuilder,
			FindersBuilder finderBuilder, DataHandler dataHandler) {
		this.contentProviderBuilder = contentProviderBuilder;
		this.findersBuilder = finderBuilder;
		this.dataHandler = dataHandler;
	}

	@Override
	protected List<ContentProvider> getContentProviders(
			Map<String, List<String>> collectData) {
		return contentProviderBuilder.build(collectData);
	}

	@Override
	protected Map<String, Finder> getFinders(
			Map<String, List<String>> collectData) {
		return findersBuilder.build(collectData);
	}

	@Override
	protected DataHandler buildDataHandler() {
		return dataHandler;
	}

	/**
	 * 内容提供器创建器
	 * 
	 * @author Roy Huang
	 * @version 20121211
	 * 
	 */
	public static interface ContentProviderBuilder {

		/**
		 * 创建内容提供者
		 * 
		 * @param collectData
		 * @return
		 */
		public List<ContentProvider> build(Map<String, List<String>> collectData);
	}

	/**
	 * 查找器创建器
	 * 
	 * @author Roy Huang
	 * @version 20121211
	 * 
	 */
	public static interface FindersBuilder {

		/**
		 * 创建查找器集合
		 * 
		 * @param collectData
		 * @return
		 */
		public Map<String, Finder> build(Map<String, List<String>> collectData);
	}

}
