package org.developerworld.io;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

/**
 * 获取文件目录过滤器
 * 
 * @version 20110111
 * @author Roy Huang
 * 
 * @deprecated
 */
public class WithoutFileFilter implements FileFilter {

	private List<File> files = new ArrayList<File>();
	
	public WithoutFileFilter(){
		
	}

	public WithoutFileFilter(String file) {
		this(new File(file));
	}

	public WithoutFileFilter(File file) {
		addFile(file);
	}

	public void addFile(String file) {
		addFile(new File(file));
	}

	public void addFile(File file) {
		files.add(file);
	}

	public boolean accept(File file) {
		for (File _file : files) {
			if (_file.compareTo(file) == 0)
				return false;
		}
		return true;
	}
}
