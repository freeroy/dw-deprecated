package org.developerworld.tools.compress.tar;

import java.io.FileNotFoundException;
import java.io.InputStream;

import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.developerworld.tools.compress.ArchiveInputStreamBuilder;

/**
 * Tag压缩文件输入流
 * @author Roy Huang
 * @version 20121107
 *
 * @deprecated
 * @see org.developerworld.commons.compress project
 */
public class TarArchiveInputStreamBuilder implements ArchiveInputStreamBuilder {

	public ArchiveInputStream buildArchiveInputStream(InputStream inputStream)
			throws FileNotFoundException, ArchiveException {
		return new ArchiveStreamFactory().createArchiveInputStream(
				ArchiveStreamFactory.TAR, inputStream);
	}

}
