package org.developerworld.tools.compress;

import java.io.OutputStream;

import org.apache.commons.compress.compressors.CompressorException;
import org.apache.commons.compress.compressors.CompressorOutputStream;

/**
 * 压缩文件输出流创建器
 * @author Roy Huang
 * @version 20121109
 *
 * @deprecated
 * @see org.developerworld.commons.compress project
 */
public interface CompressorOutputStreamBuilder {

	/**
	 * 创建输出流
	 * @param outputStream
	 * @return
	 * @throws CompressorException 
	 */
	CompressorOutputStream buildCompressorOutputStream(
			OutputStream outputStream) throws CompressorException;
}
