package org.developerworld.tools.excel;

import java.io.IOException;
import java.util.List;

/**
 * excel接口对象
 * @author Roy Huang
 * @version 20111207
 *
 *@deprecated
 *@see org.developerworld.commons.excel project
 */
public interface WorkBook {

	/**
	 * 创建一份Sheet
	 * @return
	 */
	public Sheet createSheet();
	
	/**
	 * 创建指定名称的Excel
	 * @param sheetName
	 * @return
	 */
	public Sheet createSheet(String sheetName);
	
	/**
	 * 根据索引找sheet
	 * @param sheetIndex
	 * @return
	 */
	public Sheet getSheet(int sheetIndex);
	
	/**
	 * 根据名称找sheet
	 * @param sheetName
	 * @return
	 */
	public Sheet getSheet(String sheetName);
	
	/**
	 * 返回Sheet集合
	 * @return
	 */
	public List<Sheet> getSheets();
	
	/**
	 * 返回sheet个数
	 * @return
	 */
	public int getSheetCount();
	
	/**
	 * 删除指定的sheet
	 * @param sheetIndex
	 */
	public void removeSheet(int sheetIndex);
	
	/**
	 * 保存文件
	 * @throws ExcelException 
	 */
	public void save() throws ExcelException;
	
	/**
	 * 关闭文件
	 * @throws ExcelException 
	 */
	public void close() throws ExcelException;

	/**
	 * 保存文件
	 * @throws IOException 
	 * @throws ExcelException 
	 */
	public void saveAndClose() throws ExcelException;
	
}
