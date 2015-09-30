package org.developerworld.tools.compress.xz;

import java.io.OutputStream;

import org.apache.commons.compress.compressors.CompressorException;
import org.apache.commons.compress.compressors.CompressorOutputStream;
import org.apache.commons.compress.compressors.CompressorStreamFactory;
import org.developerworld.tools.compress.CompressorOutputStreamBuilder;

/**
 * 
 * @author Roy
 *
 * @deprecated
 * @see org.developerworld.commons.compress project
 */
public class XzCompressorOutputStreamBuilder implements
		CompressorOutputStreamBuilder {

	public CompressorOutputStream buildCompressorOutputStream(
			OutputStream outputStream) throws CompressorException {
		return new CompressorStreamFactory().createCompressorOutputStream(
				CompressorStreamFactory.XZ, outputStream);
	}

}
