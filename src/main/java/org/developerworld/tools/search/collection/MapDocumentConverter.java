package org.developerworld.tools.search.collection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.Field.TermVector;
import org.apache.lucene.document.Fieldable;
import org.developerworld.tools.search.DocumentConverter;

/**
 * Map对象文档转换器
 * 
 * @author Roy Huang
 * @version 20120907
 * 
 *@deprecated
 *@see org.developerworld.frameworks.lucene project
 */
public class MapDocumentConverter implements
		DocumentConverter<Map<String, String>> {

	List<Object[]> fields = new ArrayList<Object[]>();

	public MapDocumentConverter addField(String name, Store store, Index index) {
		return addField(name, true, store, index, TermVector.NO);
	}

	public MapDocumentConverter addField(String name, Store store, Index index,
			TermVector termVector) {
		return addField(name, true, store, index, termVector);
	}

	public MapDocumentConverter addField(String name, boolean internName,
			Store store, Index index, TermVector termVector) {
		fields.add(new Object[] { name, internName, store, index, termVector });
		return this;
	}

	public Map<String, String> documentToData(Document doc) {
		Map<String, String> rst = new HashMap<String, String>();
		List<Fieldable> fields = doc.getFields();
		for (Fieldable field : fields)
			rst.put(field.name(), field.stringValue());
		return rst;
	}

	public Document dataToDocument(Map<String, String> data) {
		Document rst = new Document();
		for (Object[] fieldConfig : fields) {
			rst.add(new Field((String) fieldConfig[0],
					(Boolean) fieldConfig[1], (String) data
							.get((String) fieldConfig[0]),
					(Store) fieldConfig[2], (Index) fieldConfig[3],
					(TermVector) fieldConfig[4]));
		}
		return rst;
	}

}
