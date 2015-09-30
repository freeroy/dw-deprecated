package org.developerworld.tools.collect.datahandler;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.developerworld.tools.collect.DataHandler;

/**
 * 日志输出采集信息的数据处理器
 * @author Roy Huang
 * @version 20121211
 *@deprecated
 *@see org.developerworld.commons.collect project
 *
 */
public class LogDataHandler implements DataHandler {
	
	private final static Log log=LogFactory.getLog(LogDataHandler.class);

	public void handleData(Map<String, List<String>> data) {
		log.info("");
		log.info("find data!");
		log.info("the data is:"+data);
	}

}
