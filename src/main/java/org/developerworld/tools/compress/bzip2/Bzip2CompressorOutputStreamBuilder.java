package org.developerworld.tools.compress.bzip2;

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
public class Bzip2CompressorOutputStreamBuilder implements
		CompressorOutputStreamBuilder {

	public CompressorOutputStream buildCompressorOutputStream(
			OutputStream outputStream) throws CompressorException {
		return new CompressorStreamFactory().createCompressorOutputStream(
				CompressorStreamFactory.BZIP2, outputStream);
	}

}
