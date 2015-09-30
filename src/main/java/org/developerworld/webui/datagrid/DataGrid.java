package org.developerworld.webui.datagrid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.developerworld.command.OrderCommand;
import org.developerworld.command.PageCommand;

/**
 * 数据网格控件辅助类
 * 
 * @author Roy Huang
 * @version 20111026
 * 
 *@deprecated
 *@see org.developerworld.frameworks.webui project
 * @param <T>
 */
public class DataGrid<T> {

	private PageCommand pageCommand;
	private OrderCommand orderCommand;
	private List<T> datas;
	private List<DataGridTitle> titles=new ArrayList<DataGridTitle>();

	public void setPageCommand(PageCommand pageCommand) {
		this.pageCommand = pageCommand;
	}
	
	public DataGrid<T> pageCommand(PageCommand pageCommand){
		setPageCommand(pageCommand);
		return this;
	}

	public void setOrderCommand(OrderCommand orderCommand) {
		this.orderCommand = orderCommand;
	}
	
	public DataGrid<T> orderCommand(OrderCommand orderCommand){
		setOrderCommand(orderCommand);
		return this;
	}

	public void setDatas(List<T> datas) {
		this.datas = datas;
	}
	
	public DataGrid<T> datas(List<T> datas) {
		setDatas(datas);
		return this;
	}

	public void setTitles(List<DataGridTitle> titles) {
		this.titles = titles;
	}
	
	public DataGrid<T> titles(List<DataGridTitle> titles) {
		setTitles(titles);
		return this;
	}

	/**
	 * 为Gird添加一个新Title
	 */
	public DataGridTitle addTitle() {
		DataGridTitle rst=new DataGridTitle();
		titles.add(rst);
		return rst;
	}

	/**
	 * 生成Map数据
	 * 
	 * @return
	 */
	public Map<String, Object> toMap() {
		Map<String, Object> rst = new HashMap<String, Object>();
		// 生成sort段
		rst.put("sort", new DataGridSort(orderCommand));
		// 生成page段
		rst.put("page", new DataGridPage(pageCommand));
		// 生成title段
		rst.put("title", titles);
		// 生成data段
		rst.put("datas", datas);
		return rst;
	}
}
