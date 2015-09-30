package org.developerworld.tools.collect.datahandler;

import java.util.List;
import java.util.Map;

import org.developerworld.tools.collect.Collecter;
import org.developerworld.tools.collect.ContentProvider;
import org.developerworld.tools.collect.DataHandler;
import org.developerworld.tools.collect.Finder;
import org.developerworld.tools.collect.collecter.SimpleCollecter;

/**
 * 抽象多重采集数据处理器
 * 
 * @author Roy Huang
 * @version 20121211
 *@deprecated
 *@see org.developerworld.commons.collect project
 * 
 */
public abstract class AbstractMultipleCollecterDataHandler implements
		DataHandler {

	public void handleData(Map<String, List<String>> collectData) {
		List<ContentProvider> contentProviders = getContentProviders(collectData);
		Map<String, Finder> finders = getFinders(collectData);
		if (contentProviders != null && finders != null) {
			for (ContentProvider contentProvider : contentProviders) {
				Collecter collecter = new SimpleCollecter();
				collecter.collect(contentProvider, finders, buildDataHandler());
			}
		}
	}

	/**
	 * 获取内容提供器
	 * 
	 * @param collectData
	 * @return
	 */
	protected abstract List<ContentProvider> getContentProviders(
			Map<String, List<String>> collectData);

	/**
	 * 获取查找器
	 * 
	 * @param collectData
	 * @return
	 */
	protected abstract Map<String, Finder> getFinders(
			Map<String, List<String>> collectData);

	/**
	 * 获取数据处理器
	 * 
	 * @return
	 */
	protected abstract DataHandler buildDataHandler();

}
