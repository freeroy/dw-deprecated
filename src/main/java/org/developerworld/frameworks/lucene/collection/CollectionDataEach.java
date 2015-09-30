package org.developerworld.frameworks.lucene.collection;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.developerworld.tools.search.DataEach;

/**
 * 一般集合数据遍历器
 * 
 * @author Roy Huang
 * @version 20120905
 * @deprecated see org.developerworld.frameworks.luncene.impl
 */
public class CollectionDataEach<T> implements DataEach<T> {

	private final static Log log = LogFactory.getLog(CollectionDataEach.class);

	private Collection<T> datas;

	public CollectionDataEach(Collection<T> datas) {
		datas = Collections.unmodifiableCollection(datas);
	}

	public void each(DataIndex<T> dataIndex) throws IOException {
		Iterator<T> iterator = datas.iterator();
		while (iterator.hasNext()) {
			try {
				dataIndex.index(iterator.next());
			} catch (IOException e) {
				log.error(e);
			}
		}
	}

}
