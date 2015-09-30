package org.developerworld.tools.compress.zip;

import java.io.FileNotFoundException;
import java.io.InputStream;

import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.developerworld.tools.compress.ArchiveInputStreamBuilder;

/**
 * zip 压缩文件输入流
 * 
 * @author Roy Huang
 * @vesion 20121107
 * 
 * @deprecated
 * @see org.developerworld.commons.compress project
 */
public class ZipArchiveInputStreamBuilder implements ArchiveInputStreamBuilder {

	public ArchiveInputStream buildArchiveInputStream(InputStream inputStream)
			throws FileNotFoundException, ArchiveException {
		return new ArchiveStreamFactory().createArchiveInputStream(
				ArchiveStreamFactory.ZIP,inputStream);
	}
}
