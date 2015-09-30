package org.developerworld.tools.compress;

import java.util.HashMap;
import java.util.Map;

import org.developerworld.tools.compress.ar.ArArchiveInputStreamBuilder;
import org.developerworld.tools.compress.bzip2.Bzip2CompressorInputStreamBuilder;
import org.developerworld.tools.compress.cpio.CpioArchiveInputStreamBuilder;
import org.developerworld.tools.compress.dump.DumpArchiveInputStreamBuilder;
import org.developerworld.tools.compress.gzip.GzipCompressorInputStreamBuilder;
import org.developerworld.tools.compress.jar.JarArchiveInputStreamBuilder;
import org.developerworld.tools.compress.pack200.Pack200CompressorInputStreamBuilder;
import org.developerworld.tools.compress.tar.TarArchiveInputStreamBuilder;
import org.developerworld.tools.compress.xz.XzCompressorInputStreamBuilder;
import org.developerworld.tools.compress.zip.ZipArchiveInputStreamBuilder;

/**
 * 解压缩器工厂
 * 
 * @author Roy Huang
 * @version 20121109
 * 
 * @deprecated
 * @see org.developerworld.commons.compress project
 */
public class DecompressorFactory {

	private static Map<SupportType, ArchiveInputStreamBuilder> builders = new HashMap<SupportType, ArchiveInputStreamBuilder>();
	static {
		builders.put(SupportType.AR, new ArArchiveInputStreamBuilder());
		builders.put(SupportType.CPIO, new CpioArchiveInputStreamBuilder());
		builders.put(SupportType.DUMP, new DumpArchiveInputStreamBuilder());
		builders.put(SupportType.JAR, new JarArchiveInputStreamBuilder());
		builders.put(SupportType.TAR, new TarArchiveInputStreamBuilder());
		builders.put(SupportType.ZIP, new ZipArchiveInputStreamBuilder());
	}

	/**
	 * 创建压缩器
	 * 
	 * @param type
	 * @return
	 */
	public static Decompressor buildDecompressor(SupportType type) {
		// 特殊类型特殊构造
		if (type.equals(SupportType.TAR_GZIP))
			return new MultipleDecompressor(new TarArchiveInputStreamBuilder(),
					new GzipCompressorInputStreamBuilder());
		else if (type.equals(SupportType.TAR_BZIP2))
			return new MultipleDecompressor(new TarArchiveInputStreamBuilder(),
					new Bzip2CompressorInputStreamBuilder());
		else if (type.equals(SupportType.TAR_XZ))
			return new MultipleDecompressor(new TarArchiveInputStreamBuilder(),
					new XzCompressorInputStreamBuilder());
		else if (type.equals(SupportType.JAR_PACK200))
			return new MultipleDecompressor(new JarArchiveInputStreamBuilder(),
					new Pack200CompressorInputStreamBuilder());
		else if (!builders.containsKey(type))
			throw new IllegalArgumentException("unsupport this type:" + type);
		return new SimpleDecompressor(builders.get(type));
	}
}
