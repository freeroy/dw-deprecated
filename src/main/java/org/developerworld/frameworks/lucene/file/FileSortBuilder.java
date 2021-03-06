package org.developerworld.frameworks.lucene.file;

import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.developerworld.tools.search.SortBuilder;

/**
 * 文件排序创建器
 * 
 * @author Roy Huang
 * @version 20120823
 * @deprecated see org.developerworld.frameworks.luncene.impl
 */
public class FileSortBuilder implements SortBuilder {

	public Sort buildSort() {
		return new Sort(new SortField("lastModified", SortField.LONG, true));
	}

}
