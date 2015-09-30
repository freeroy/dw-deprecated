package org.developerworld.tools.compress.gzip;

import java.io.FileNotFoundException;
import java.io.InputStream;

import org.apache.commons.compress.compressors.CompressorException;
import org.apache.commons.compress.compressors.CompressorInputStream;
import org.apache.commons.compress.compressors.CompressorStreamFactory;
import org.developerworld.tools.compress.CompressorInputStreamBuilder;
/**
 * 
 * @author Roy
 *
 * @deprecated
 * @see org.developerworld.commons.compress project
 */
public class GzipCompressorInputStreamBuilder implements
		CompressorInputStreamBuilder {

	public CompressorInputStream buildCompressorInputStream(
			InputStream inputStream) throws FileNotFoundException,
			CompressorException {
		return new CompressorStreamFactory().createCompressorInputStream(
				CompressorStreamFactory.GZIP, inputStream);
	}

}
