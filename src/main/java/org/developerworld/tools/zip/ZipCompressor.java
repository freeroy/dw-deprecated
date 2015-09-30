package org.developerworld.tools.zip;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.developerworld.io.WithoutFileFilter;
import org.developerworld.tools.zip.ant.ZipEntry;
import org.developerworld.tools.zip.ant.ZipOutputStream;

/**
 * zip文件压缩
 * 
 * @version 20110111
 * @author Roy Huang
 *@deprecated
 *@see org.developerworld.commons.compress project
 */
public class ZipCompressor extends ZipBase {

	protected int pathStartIndex = 0;
	protected ArrayList<FileFilter> fileFilters = new ArrayList<FileFilter>();
	protected ArrayList<FilenameFilter> filenameFilters = new ArrayList<FilenameFilter>();

	/**
	 * 添加文件过滤器
	 * 
	 * @param fileFilter
	 */
	public void addFileFilter(FileFilter fileFilter) {
		this.fileFilters.add(fileFilter);
	}

	/**
	 * 添加文件名过滤器
	 * 
	 * @param filenameFilter
	 */
	public void addFilenameFilter(FilenameFilter filenameFilter) {
		this.filenameFilters.add(filenameFilter);
	}

	/**
	 * 压缩到指定目录
	 * 
	 * @param output
	 * @throws IOException
	 */
	public void compress(String output) throws IOException {
		compress(new File(output));
	}

	/**
	 * 压缩到文件对象指定位置
	 * 
	 * @param output
	 * @throws IOException
	 */
	public void compress(File output) throws IOException {
		FileOutputStream fos = null;
		try {
			// 添加一个目录过滤器
			addFileFilter(new WithoutFileFilter(output));
			fos = new FileOutputStream(output);
			compress(fos);
		} finally {
			if (fos != null)
				fos.close();
		}
	}

	/**
	 * 压缩到输出流
	 * 
	 * @param output
	 * @throws IOException
	 */
	public void compress(OutputStream output) throws IOException {
		ZipOutputStream zos = null;
		try {
			zos = new ZipOutputStream(output);
			compress(zos);
		} finally {
			if (zos != null)
				zos.finish();// zos.close();这样会导致参数流关闭
		}
	}

	/**
	 * 压缩到zip输出流
	 * 
	 * @param output
	 * @throws IOException
	 */
	private void compress(ZipOutputStream output) throws IOException {
		for (int i = 0; i < size; i++) {
			String compressPath = filePaths.get(i);
			File compressFile = fileFiles.get(i);
			String temp = compressFile.getPath();
			temp = temp.substring(0, temp.lastIndexOf(File.separator) + 1);
			pathStartIndex = temp.length();
			while (compressPath.startsWith(File.separator))
				compressPath = compressPath.substring(1);
			compress(compressPath, compressFile, output);
		}
	}

	/**
	 * 进行目录递归压缩
	 * 
	 * @param compressPath
	 * @param compressFile
	 * @param zos
	 * @throws IOException
	 */
	private void compress(String compressPath, File compressFile,
			ZipOutputStream zos) throws IOException {
		if (compressFile.isDirectory()) {
			// 过滤文件目录
			List<File> list = Arrays.asList(compressFile.listFiles());
			for (int i = 0; i < fileFilters.size(); i++) {
				FileFilter ff = (FileFilter) fileFilters.get(i);
				list.retainAll(Arrays.asList(compressFile.listFiles(ff)));
			}
			for (int i = 0; i < filenameFilters.size(); i++) {
				FilenameFilter ff = (FilenameFilter) filenameFilters.get(i);
				list.retainAll(Arrays.asList(compressFile.listFiles(ff)));
			}
			if (list != null) {
				if (list.size() == 0) {
					try {
						zos.putNextEntry(new ZipEntry(compressPath
								+ compressFile.getPath().substring(
										pathStartIndex) + "/"));// zip是以/表示目录
					} finally {
						zos.closeEntry();
					}
				} else {
					for (int i = 0; i < list.size(); i++)
						compress(compressPath, (File) list.get(i), zos);
				}
			}
		} else {
			FileInputStream fis = null;
			try {
				zos.putNextEntry(new ZipEntry(compressPath
						+ compressFile.getPath().substring(this.pathStartIndex)));
				fis = new FileInputStream(compressFile);
				byte read[] = new byte[bufferedSize];
				int length = 0;
				while ((length = fis.read(read)) > 0)
					zos.write(read, 0, length);// zos.write(read);
			} finally {
				zos.closeEntry();
				if (fis != null)
					fis.close();
			}
		}
	}

	public static void main(String args[]) throws IOException {
		String file = "e:/快速入口";
		ZipCompressor zc = new ZipCompressor();
		zc.addFile(file);
		zc.compress("e:/快速入口.zip");
	}
}
