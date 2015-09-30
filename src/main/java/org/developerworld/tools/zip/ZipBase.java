package org.developerworld.tools.zip;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 压缩底层类
 * 
 * @version 20110111
 * @author Roy Huang
 * 
 *@deprecated
 *@see org.developerworld.commons.compress project
 */
public abstract class ZipBase {

	protected int bufferedSize = 1024;
	protected int size = 0;
	protected List<String> filePaths = new ArrayList<String>();
	protected List<File> fileFiles = new ArrayList<File>();

	public void setBufferedSize(int bufferedSize) {
		this.bufferedSize = bufferedSize;
	}

	public int getBufferedSize() {
		return bufferedSize;
	}

	public void addFile(String file) {
		addFile(new File(file));
	}

	public void addFile(File file) {
		addFile("", file);
	}

	public void addFile(String zipPath, String file) {
		addFile(zipPath, new File(file));
	}

	public void addFile(String zipPath, File file) {
		if (file.exists()) {
			this.filePaths.add(zipPath);
			this.fileFiles.add(file);
			++size;
		}
	}
}
