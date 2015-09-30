package org.developerworld.tools.search.file;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.developerworld.tools.search.DataEach;

/**
 * 文件数据提供器
 * 
 * @author Roy Huang
 * @version 20120906
 * 
 *@deprecated
 *@see org.developerworld.frameworks.lucene project
 */
public class FileDataEach implements DataEach<File> {

	private final static Log log = LogFactory.getLog(FileDataEach.class);

	private Set<String> filePaths = new HashSet<String>();
	private FileFilter fileFilter;

	public FileDataEach() {

	}

	public FileDataEach(Set<String> filePaths) {
		this(filePaths, null);
	}

	public FileDataEach(Set<String> filePaths, FileFilter fileFilter) {
		if (filePaths != null)
			this.filePaths.addAll(filePaths);
		this.fileFilter = fileFilter;
	}

	public void setFilePaths(Set<String> filePaths) {
		this.filePaths = filePaths;
	}

	public void setFileFilter(FileFilter fileFilter) {
		this.fileFilter = fileFilter;
	}

	public void each(DataIndex<File> dataIndex) throws Exception {
		for (String filePath : filePaths)
			each(dataIndex, new File(filePath));
	}

	/**
	 * 递归遍历文件索引
	 * 
	 * @param dataIndex
	 * @param file
	 * @throws IOException
	 */
	private void each(DataIndex<File> dataIndex, File file) throws IOException {
		if (!file.exists())
			return;
		else if (!file.canRead())
			return;
		// 若是目录，就递归执行
		if (file.isDirectory()) {
			File[] files = null;
			if (fileFilter != null)
				files = file.listFiles(fileFilter);
			else
				files = file.listFiles();
			if (files != null) {
				for (File _file : files)
					each(dataIndex, _file);
			}
		} else {
			// 若是文件，执行检索
			try {
				dataIndex.index(file);
			} catch (IOException e) {
				log.error(e);
			}
		}
	}

}