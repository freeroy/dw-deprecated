package org.developerworld.tools.filereader.txt;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.developerworld.tools.filereader.FileReader;

/**
 * 简单文件读取器
 * 
 * @author Roy Huang
 * @version 20130417
 * 
 *@deprecated
 *@see org.developerworld.commons.filereader project
 */
public class TxtFileReader implements FileReader {

	/**
	 * 获取文件编码，也可通过重构修改获取文件编码方法
	 * @param file
	 * @return
	 * @throws IOException
	 */
	protected String getFileCharset(File file) throws IOException {
		String rst = null;
		BufferedInputStream bis = null;
		try {
			byte[] first3Bytes = new byte[3];
			bis = new BufferedInputStream(new FileInputStream(file));
			bis.mark(0);
			int read = bis.read(first3Bytes, 0, 3);
			if (read == -1) {
				rst = "GBK"; // 文件编码为 ANSI
			} else if (first3Bytes[0] == (byte) 0xFF
					&& first3Bytes[1] == (byte) 0xFE) {
				rst = "UTF-16LE"; // 文件编码为 Unicode
			} else if (first3Bytes[0] == (byte) 0xFE
					&& first3Bytes[1] == (byte) 0xFF) {
				rst = "UTF-16BE"; // 文件编码为 Unicode big endian
			} else if (first3Bytes[0] == (byte) 0xEF
					&& first3Bytes[1] == (byte) 0xBB
					&& first3Bytes[2] == (byte) 0xBF) {
				rst = "UTF-8"; // 文件编码为 UTF-8
			}
			bis.reset();
			if (rst == null) {
				rst = "GBK";
				while ((read = bis.read()) != -1) {
					if (read >= 0xF0)
						break;
					else if (0x80 <= read && read <= 0xBF)// 单独出现BF以下的，也算是GBK
						break;
					if (0xC0 <= read && read <= 0xDF) {
						read = bis.read();
						if (0x80 <= read && read <= 0xBF) // 双字节 (0xC0 - 0xDF)
							// (0x80
							// - 0xBF),也可能在GB编码内
							continue;
						else
							break;
					} else if (0xE0 <= read && read <= 0xEF) {// 也有可能出错，但是几率较小
						read = bis.read();
						if (0x80 <= read && read <= 0xBF) {
							read = bis.read();
							if (0x80 <= read && read <= 0xBF) {
								rst = "UTF-8";
								break;
							} else
								break;
						} else
							break;
					}
				}
			}
		} finally {
			if (bis != null)
				bis.close();
		}
		return rst;
	}

	public String readFileToString(File file) throws IOException {
		String rst = null;
		String charset = getFileCharset(file);
		if (charset != null && charset.trim().length() > 0)
			rst = FileUtils.readFileToString(file, charset);
		else
			rst = FileUtils.readFileToString(file);
		return rst;
	}

}
