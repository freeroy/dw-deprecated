package org.developerworld.tools.collect.datahandler;

import java.util.List;
import java.util.Map;

import org.developerworld.tools.collect.DataHandler;

/**
 * 控制台输出采集信息的数据处理器
 * @author Roy Huang
 * @version 20121211
 *@deprecated
 *@see org.developerworld.commons.collect project
 *
 */
public class ConsoleDataHandler implements DataHandler{

	public void handleData(Map<String, List<String>> data) {
		System.out.println("");
		System.out.println("find data!");
		System.out.println("the data is:"+data);
	}

}
