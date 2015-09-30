package org.developerworld.tools.collect.datahandler;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.SystemUtils;
import org.developerworld.tools.collect.DataHandler;

/**
 * 自定义输出采集信息的数据处理器
 * 
 * @author Roy Huang
 * @version 20121211
 *@deprecated
 *@see org.developerworld.commons.collect project
 * 
 */
public class WriterDataHandler implements DataHandler {

	private Writer writer;

	public WriterDataHandler(Writer writer) {
		this.writer = writer;
	}

	public void handleData(Map<String, List<String>> data) {
		try {
			writer.write(SystemUtils.LINE_SEPARATOR);
			writer.write("find data!" + SystemUtils.LINE_SEPARATOR);
			writer.write("the data is:" + data + SystemUtils.LINE_SEPARATOR);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
