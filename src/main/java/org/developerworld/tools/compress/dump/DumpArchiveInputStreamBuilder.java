package org.developerworld.tools.compress.dump;

import java.io.FileNotFoundException;
import java.io.InputStream;

import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.developerworld.tools.compress.ArchiveInputStreamBuilder;

/**
 * dump 解压缩文件输入流创建器
 * @author Roy Huang
 * @version 20121108
 *
 * @deprecated
 * @see org.developerworld.commons.compress project
 */
public class DumpArchiveInputStreamBuilder implements ArchiveInputStreamBuilder {

	public ArchiveInputStream buildArchiveInputStream(InputStream inputStream)
			throws FileNotFoundException, ArchiveException {
		return new ArchiveStreamFactory().createArchiveInputStream(
				ArchiveStreamFactory.DUMP,inputStream);
	}

}
