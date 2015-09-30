package org.developerworld.tools.compress.ar;

import java.io.FileNotFoundException;
import java.io.InputStream;

import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.developerworld.tools.compress.ArchiveInputStreamBuilder;

/**
 * ar 压缩输入流创建器
 * @author Roy Huang
 * @version 20121109
 *
 * @deprecated
 * @see org.developerworld.commons.compress project
 */
public class ArArchiveInputStreamBuilder implements ArchiveInputStreamBuilder{

	public ArchiveInputStream buildArchiveInputStream(InputStream inputStream)
			throws FileNotFoundException, ArchiveException {
		return new ArchiveStreamFactory().createArchiveInputStream(
				ArchiveStreamFactory.AR, inputStream);
	}

}
