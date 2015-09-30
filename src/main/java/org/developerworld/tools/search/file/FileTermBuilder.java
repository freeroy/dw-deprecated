package org.developerworld.tools.search.file;

import java.io.File;

import org.apache.lucene.index.Term;
import org.developerworld.tools.search.TermBuilder;

/**
 * 文件标识创建器
 * @author Roy Huang
 * @version 2012907
 *
 *@deprecated
 *@see org.developerworld.frameworks.lucene project
 */
public class FileTermBuilder implements TermBuilder<File> {

	public Term buildTerm(File data) {
		return new Term("path", data.getPath());
	}

}
