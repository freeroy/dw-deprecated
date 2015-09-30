package org.developerworld.tools.compress.bzip2;

import java.io.FileNotFoundException;
import java.io.InputStream;

import org.apache.commons.compress.compressors.CompressorException;
import org.apache.commons.compress.compressors.CompressorInputStream;
import org.apache.commons.compress.compressors.CompressorStreamFactory;
import org.developerworld.tools.compress.CompressorInputStreamBuilder;
/**
 * 
 * @deprecated
 * @see org.developerworld.commons.compress project
 * @author Roy
 *
 */
public class Bzip2CompressorInputStreamBuilder implements
		CompressorInputStreamBuilder {

	public CompressorInputStream buildCompressorInputStream(
			InputStream inputStream) throws FileNotFoundException,
			CompressorException {
		return new CompressorStreamFactory().createCompressorInputStream(
				CompressorStreamFactory.BZIP2,inputStream);
	}

}
