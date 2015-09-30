package org.developerworld.tools.search.file;

import java.io.File;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.NumericField;
import org.apache.lucene.index.FieldInfo.IndexOptions;
import org.developerworld.tools.filereader.FileReaderFactory;
import org.developerworld.tools.search.DocumentConverter;

/**
 * 文件文档转换器
 * 
 * @author Roy Huang
 * @version 20120822
 * 
 *@deprecated
 *@see org.developerworld.frameworks.lucene project
 */
public class FileDocumentConverter implements DocumentConverter<File> {

	private final static Log log = LogFactory
			.getLog(FileDocumentConverter.class);

	public File documentToData(Document doc) {
		File rst = null;
		try {
			rst = new File(doc.get("path"));
		} catch (Throwable t) {
			log.error(t);
		}
		return rst;
	}

	public Document dataToDocument(File data) {
		Document rst = null;
		try {
			// 创建文档对象
			Document doc = new Document();
			// 创建文档位置字段
			Field pathfield = new Field("path", data.getPath(),
					Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS);
			pathfield.setIndexOptions(IndexOptions.DOCS_ONLY);
			doc.add(pathfield);
			// 创建文档名称字段
			Field namefield = new Field("name", data.getName(),
					Field.Store.YES, Field.Index.ANALYZED);
			doc.add(namefield);
			// 创建文件最后修改时间字段,带索引功能，用于排序
			NumericField lastModifiedField = new NumericField("lastModified");
			lastModifiedField.setLongValue(data.lastModified());
			doc.add(lastModifiedField);
			// 创建文件占用空间字段,带索引功能，用于排序
			NumericField lengthField = new NumericField("lengthField");
			lengthField.setLongValue(data.length());
			doc.add(lengthField);
			// 获取文件扩展名
			org.developerworld.tools.filereader.FileReader fileReader = FileReaderFactory
					.getFileReader(data);
			if (fileReader != null) {
				try {
					String fileContent = fileReader.readFileToString(data);
					if (StringUtils.isNotBlank(fileContent))
						doc.add(new Field("content", fileContent,
								Field.Store.NO, Field.Index.ANALYZED));
				} catch (Exception e) {
					log.error(e);
				}
			}
			rst = doc;
		} catch (Exception e) {
			log.error(e);
		}
		return rst;
	}

}
