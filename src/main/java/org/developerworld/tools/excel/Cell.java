package org.developerworld.tools.excel;

import java.util.Date;

/**
 * 列对象接口
 * @author Roy Huang
 * @version 20111109
 *
 *@deprecated
 *@see org.developerworld.commons.excel project
 */
public interface Cell {

	/**
	 * 写入字符数据
	 * @param value
	 * @throws ExcelException 
	 */
	public void writeText(String value) throws ExcelException;
	
	/**
	 * 写入数值型数据
	 * @param value
	 * @throws ExcelException
	 */
	public void writeNumber(Number value) throws ExcelException;
	
	/**
	 * 写入日期型数据
	 * @param date
	 * @throws ExcelException
	 */
	public void writeDate(Date date) throws ExcelException;
	
	/**
	 * 读取字符
	 * @return
	 * @throws ExcelException
	 */
	public String readText() throws ExcelException;
	
	/**
	 * 读取数字
	 * @return
	 * @throws ExcelException
	 */
	public Number readNumber() throws ExcelException;
	
	/**
	 * 读取日期
	 * @return
	 * @throws ExcelException
	 */
	public Date readDate() throws ExcelException;

}
