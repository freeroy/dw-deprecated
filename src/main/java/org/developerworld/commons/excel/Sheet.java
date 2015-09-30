package org.developerworld.commons.excel;

import java.util.List;

/**
 * sheet对象接口
 * @author Roy Huang
 * @version 20110710
 *@deprecated 不再维护升级
 */
public interface Sheet {

	/**
	 * 创建新行
	 * @return
	 */
	public Row createRow();
	
	/**
	 * 创建新行
	 * @param rowIndex
	 * @return
	 */
	public Row createRow(int rowIndex);
	
	/**
	 * 获取一行
	 * @param rowIndex
	 * @return
	 */
	public Row getRow(int rowIndex);
	
	/**
	 * 返回所有行
	 * @return
	 */
	public List<Row> getRows();
	
	/**
	 * 放回sheet函数
	 * @return
	 */
	public int getRowCount();
	
	/**
	 * 删除行
	 * @param rowIndex
	 */
	public void removeRow(int rowIndex);
	
	/**
	 * 获取sheet的名称
	 * @return
	 */
	public String getName();

}
