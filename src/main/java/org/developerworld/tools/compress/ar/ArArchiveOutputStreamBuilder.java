package org.developerworld.tools.compress.ar;

import java.io.OutputStream;

import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.developerworld.tools.compress.ArchiveOutputStreamBuilder;

/**
 * ar压缩输出流创建器
 * @author Roy Huang
 * @version 20121109
 *
 * @deprecated
 * @see org.developerworld.commons.compress project
 */
public class ArArchiveOutputStreamBuilder implements ArchiveOutputStreamBuilder {

	public ArchiveOutputStream buildArchiveOutputStream(
			OutputStream outputStream) throws ArchiveException {
		return new ArchiveStreamFactory().createArchiveOutputStream(
				ArchiveStreamFactory.AR, outputStream);
	}

}
