package org.developerworld.command;

import java.util.List;
import java.util.Map;


/**
 * 排序类
 * 
 * @author Roy.Huang
 * @version 20120109
 * @deprecated
 * @see org.developerworld.commons.command project
 */
public class OrderCommand extends org.developerworld.commons.command.OrderCommand {

	public OrderCommand() {
		super();
	}

	public OrderCommand(String orderFieldStr, String orderModelStr) {
		super(orderFieldStr, orderModelStr);
	}

	public OrderCommand(String orderFields[], String orderModels[]) {
		super(orderFields,orderModels);
	}

	public OrderCommand(List<String> orderFields, List<String> orderModels) {
		super(orderFields, orderModels);
	}

	public OrderCommand(Map<String, String> orders) {
		super(orders);
	}

	public OrderCommand(String orderStr) {
		super(orderStr);
	}
}
