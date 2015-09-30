package org.developerworld.frameworks.webui.datagrid;

import org.apache.commons.lang.StringUtils;
import org.developerworld.commons.command.OrderCommand;

/**
 * 数据网格排序对象
 * 
 * @author Roy Huang
 * @version 20110415
 * @deprecated 不再维护
 */
public class DataGridSort {

	private String fields;
	private String models;

	public DataGridSort() {

	}

	public DataGridSort(OrderCommand orderCommand) {
		if (orderCommand != null && orderCommand.size() > 0) {
			this.fields = StringUtils.join(orderCommand.getOrderFieldList(),
					",");
			this.models = StringUtils.join(orderCommand.getOrderModelList(),
					",");
		}
	}

	public String getFields() {
		return fields;
	}

	public void setFields(String fields) {
		this.fields = fields;
	}

	public String getModels() {
		return models;
	}

	public void setModels(String models) {
		this.models = models;
	}

}
