package org.developerworld.commons.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * 排序类
 * 
 * @author Roy.Huang
 * @version 20120109
 * @deprecated 不在提供支持
 */
public class OrderCommand {

	private final String ORDER_STR_SPLIT = ",";
	private List<String> orderFieldList = new ArrayList<String>();
	private List<String> orderModelList = new ArrayList<String>();

	public OrderCommand() {

	}

	public OrderCommand(String orderFieldStr, String orderModelStr) {
		addOrders(orderFieldStr, orderModelStr);
	}

	public OrderCommand(String orderFields[], String orderModels[]) {
		addOrders(Arrays.asList(orderFields), Arrays.asList(orderModels));
	}

	public OrderCommand(List<String> orderFields, List<String> orderModels) {
		addOrders(orderFields, orderModels);
	}

	public OrderCommand(Map<String, String> orders) {
		addOrders(orders);
	}

	public OrderCommand(String orderStr) {
		addOrders(orderStr);
	}

	/**
	 * 设置排序方式
	 * 
	 * @param field
	 * @param model
	 */
	public void setOrderModel(String field, String model) {
		int index = indexOf(field);
		if (index > -1 && orderModelList.size()>index) {
			orderModelList.add(index, model.length() <= 0 ? "asc" : model);
			orderModelList.remove(index + 1);
		}
	}

	/**
	 * 设置排序优先
	 * 
	 * @param field
	 * @param orderIndex
	 */
	public void setOrderIndex(String field, int orderIndex) {
		int index = indexOf(field);
		if (index > -1 && orderModelList.size()>index && index != orderIndex) {
			String model = orderModelList.get(index);
			orderFieldList.add(orderIndex, field);
			orderModelList.add(orderIndex, model);
			orderFieldList.remove(index + 1);
			orderModelList.remove(index + 1);
		}
	}

	/**
	 * 设置order字段
	 * 
	 * @param orderFields
	 */
	public void setOrderFields(String orderFields) {
		if (StringUtils.isNotBlank(orderFields)) {
			clearOrder();
			String[] _orderFields = orderFields.split(ORDER_STR_SPLIT);
			for (String orderField : _orderFields)
				addOrder(orderField, "");
		}
	}

	/**
	 * 设置order排序
	 * 
	 * @param orderModels
	 */
	public void setOrderModels(String orderModels) {
		if (StringUtils.isNotBlank(orderModels)) {
			String[] _orderModels = orderModels.split(ORDER_STR_SPLIT);
			orderModelList.clear();
			for (int i = 0; i < orderFieldList.size(); i++) {
				if (_orderModels.length > i)
					orderModelList.add(_orderModels[i]);
				else
					orderModelList.add("");
			}
		}
	}

	/**
	 * 添加排序集合
	 * 
	 * @param orderFields
	 * @param orderModels
	 */
	public void addOrders(String orderFieldStr, String orderModelStr) {
		addOrders(orderFieldStr.trim().split(","),
				orderModelStr.trim().split(","));
	}

	/**
	 * 添加排序集合
	 * 
	 * @param orderFieldList
	 * @param modelList
	 */
	public void addOrders(String orderFields[], String orderModels[]) {
		addOrders(Arrays.asList(orderFields), Arrays.asList(orderModels));
	}

	/**
	 * 添加排序集合
	 * 
	 * @param orderFieldList
	 * @param modelList
	 */
	public void addOrders(List<String> orderFields, List<String> orderModels) {
		int len = Math.min(orderFields.size(), orderModels.size());
		for (int i = 0; i < len; i++)
			addOrder(orderFields.get(i), orderModels.get(i));
	}

	/**
	 * 添加排序集合
	 * 
	 * @param orders
	 */
	public void addOrders(Map<String, String> orders) {
		Iterator<Map.Entry<String, String>> iterator = orders.entrySet()
				.iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, String> order = iterator.next();
			addOrder(order.getKey(), order.getValue());
		}
	}

	/**
	 * 添加排序集合
	 * 
	 * @param orderStr
	 */
	public void addOrders(String orderStr) {
		String orders[] = orderStr.trim().split(",");
		for (int i = 0; i < orders.length; i++)
			addOrder(orders[i]);
	}

	/**
	 * 添加排序
	 * 
	 * @param orderStr
	 */
	public void addOrder(String orderStr) {
		String order[] = orderStr.trim().split(" ");
		addOrder(order[0], order[1]);
	}

	/**
	 * 添加排序
	 * 
	 * @param field
	 * @param model
	 */
	public void addOrder(String field, String model) {
		addOrder(field, model, orderFieldList.size());
	}

	/**
	 * 添加排序
	 * 
	 * @param field
	 * @param model
	 * @param orderIndex
	 */
	public void addOrder(String field, String model, int orderIndex) {
		model = model.trim().length() <= 0 ? "asc" : model;
		if (contains(field)) {
			setOrderModel(field, model);
			setOrderIndex(field, orderIndex);
		} else {
			orderFieldList.add(field);
			orderModelList.add(model);
		}
	}

	/**
	 * 删除一个排序条件
	 * 
	 * @param field
	 */
	public void deleteOrder(String field) {
		int index = indexOf(field);
		if (index > -1) {
			orderFieldList.remove(index);
			orderModelList.remove(index);
		}
	}

	/**
	 * 清空所有order信息
	 */
	public void clearOrder() {
		orderFieldList.clear();
		orderModelList.clear();
	}

	/**
	 * 获取排序条件数目
	 * 
	 * @return
	 */
	public int size() {
		return orderFieldList.size();
	}

	/**
	 * 获取排序的优先顺序
	 * 
	 * @param orderField
	 * @return
	 */
	public int indexOf(String orderField) {
		return orderFieldList.indexOf(orderField);
	}

	/**
	 * 判断是否存在排序
	 * 
	 * @param orderField
	 * @return
	 */
	public boolean contains(String orderField) {
		return orderFieldList.contains(orderField);
	}

	/**
	 * 获取排序字段字符串
	 * @return
	 */
	public String getOrderFields() {
		return StringUtils.join(orderFieldList, ORDER_STR_SPLIT);
	}

	/**
	 * 获取排序方式字符串
	 * @return
	 */
	public String getOrderModels() {
		return StringUtils.join(orderModelList, ORDER_STR_SPLIT);
	}

	/**
	 * 获取排序字段列表
	 * @return
	 */
	public List<String> getOrderFieldList() {
		return orderFieldList;
	}

	/**
	 * 获取排序方式列表
	 * @return
	 */
	public List<String> getOrderModelList() {
		return orderModelList;
	}

	/**
	 * 获取排序集合（数组）
	 * 
	 * @return
	 */
	public List<String[]> getOrderArrayList() {
		List<String[]> rst = new ArrayList<String[]>();
		for (int i = 0; i < orderFieldList.size(); i++)
			rst.add(new String[] { orderFieldList.get(i), orderModelList.get(i) });
		return rst;
	}

	/**
	 * 获取排序集合(字符)
	 * 
	 * @return
	 */
	public List<String> getOrderStringList() {
		List<String> rst = new ArrayList<String>();
		for (int i = 0; i < orderFieldList.size(); i++)
			rst.add(orderFieldList.get(i) + " " + orderModelList.get(i));
		return rst;
	}

	/**
	 * 返回sql语句
	 * 
	 * @return
	 */
	public String toSqlOrderString() {
		StringBuffer rst = new StringBuffer();
		for (int i = 0; i < orderFieldList.size(); i++) {
			rst.append(orderFieldList.get(i)).append(" ")
					.append(orderModelList.get(i)).append(",");
		}
		if (rst.length() > 0)
			rst.delete(rst.length() - 1, rst.length());
		return rst.toString();
	}

	/**
	 * 为排序条件添加别名引用
	 * 
	 * @param alias
	 * @return
	 */
	public String toSqlOrderString(String alias) {
		StringBuffer rst = new StringBuffer();
		for (int i = 0; i < orderFieldList.size(); i++) {
			rst.append(alias + "." + orderFieldList.get(i)).append(" ")
					.append(orderModelList.get(i)).append(",");
		}
		if (rst.length() > 0)
			rst.delete(rst.length() - 1, rst.length());
		return rst.toString();
	}

	public static void main(String args[]) {

	}
}
