package org.developerworld.tools.excel;

import java.util.List;

/**
 * 行对象接口
 * @author Roy Huang
 * @author 20100710
 *
 *@deprecated
 *@see org.developerworld.commons.excel project
 */
public interface Row {

	/**
	 * 创建一个列对象
	 * @return
	 */
	public Cell createCell();
	
	/**
	 * 创建新的列
	 * @param cellIndex
	 * @return
	 */
	public Cell createCell(int cellIndex);
	
	/**
	 * 获取一个列对象
	 * @param cellIndex
	 * @return
	 */
	public Cell getCell(int cellIndex);
	
	/**
	 * 获取列集合
	 * @return
	 */
	public List<Cell> getCells();
	
	/**
	 * 放回行列数
	 * @return
	 */
	public int getCellCount();
	
	/**
	 * 删除指定列
	 * @param cellIndex
	 */
	public void removeCell(int cellIndex);

}
