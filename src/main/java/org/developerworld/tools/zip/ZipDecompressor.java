package org.developerworld.tools.zip;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;

import org.developerworld.tools.zip.ant.ZipEntry;
import org.developerworld.tools.zip.ant.ZipFile;

/**
 * 解压缩类
 * 
 * @version 20110111
 * @author Roy Huang
 *@deprecated
 *@see org.developerworld.commons.compress project
 */
public class ZipDecompressor extends ZipBase {

	/**
	 * 将压缩文件解压缩到文件对象指定目录
	 * 
	 * @param output
	 * @throws IOException
	 */
	public void decompress(File output) throws IOException {
		decompress(output.getPath());
	}

	/**
	 * 将压缩文件解压缩到指定目录路径
	 * 
	 * @param output
	 * @throws IOException
	 */
	public void decompress(String output) throws IOException {
		while (output.endsWith(File.separator))
			output = output.substring(0, output.length() - 1);
		for (int i = 0; i < size; i++) {
			String compressPath = filePaths.get(i);
			File compressFile = fileFiles.get(i);
			while (compressPath.startsWith(File.separator))
				compressPath = compressPath.substring(1);
			while(compressPath.endsWith(File.separator))
				compressPath=compressPath.substring(0,compressPath.length()-1);
			compressPath = output + File.separator + compressPath;
			ZipFile zf = null;
			try {
				zf = new ZipFile(compressFile);
				decompress(zf, compressPath);
			} finally {
				if (zf != null)
					zf.close();
			}
		}
	}

	/**
	 * 将zip文件输出
	 * 
	 * @param zipFile
	 * @param output
	 * @throws IOException
	 */
	private void decompress(ZipFile zipFile, String output) throws IOException {
		Enumeration e = zipFile.getEntries();
		while (e.hasMoreElements()) {
			ZipEntry entry = (ZipEntry) e.nextElement();
			File file = new File(output + File.separator + entry.getName());
			if (entry.isDirectory())
				file.mkdirs();
			else {
				if (file.getParentFile() != null
						&& !file.getParentFile().exists())
					file.getParentFile().mkdirs();
				if (!file.exists())
					file.createNewFile();
				FileOutputStream fos = null;
				InputStream is = null;
				try {
					fos = new FileOutputStream(file);
					is = zipFile.getInputStream(entry);
					int length = 0;
					byte b[] = new byte[bufferedSize];
					while ((length = is.read(b)) > 0)
						fos.write(b, 0, length);// fos.write(b);//这样会导致部份文件无法执行
				} finally {
					if (fos != null)
						fos.close();
					if (is != null)
						is.close();
				}
			}
		}
	}

}
