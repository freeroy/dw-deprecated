package org.developerworld.tools.collect.collecter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.developerworld.tools.collect.Collecter;
import org.developerworld.tools.collect.ContentProvider;
import org.developerworld.tools.collect.DataHandler;
import org.developerworld.tools.collect.Finder;

/**
 * 默认采集器
 * 
 * @author Roy Huang
 * @version 20121210
 * 
 *@deprecated
 *@see org.developerworld.commons.collect project
 */
public class SimpleCollecter implements Collecter {

	public void collect(ContentProvider contentProvider,
			Map<String, Finder> finders, DataHandler dataHandler) {
		// 获取需要查找的内容
		String content = contentProvider.getContent();
		if (StringUtils.isNotBlank(content)) {
			// 执行查找器
			if (finders != null) {
				Iterator<Entry<String, Finder>> iterator = finders.entrySet()
						.iterator();
				Map<String, List<String>> data = new HashMap<String, List<String>>();
				while (iterator.hasNext()) {
					Entry<String, Finder> entry = iterator.next();
					String key = entry.getKey();
					Finder finder = entry.getValue();
					if (StringUtils.isNotBlank(key)) {
						List<String> tmp = data.get(key);
						List<String> datas = null;
						if (finder != null)
							datas = finder.find(content);
						else {
							datas = new ArrayList<String>();
							datas.add(content);
						}
						if (tmp != null)
							tmp.addAll(datas);
						else
							data.put(key, datas);
					}
				}
				// 处理找到的数据
				dataHandler.handleData(data);
			}
		}
	}

}
