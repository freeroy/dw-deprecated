package org.developerworld.tools.excel;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Excel 工厂类
 * @author Roy Huang
 * @version 201107010
 *
 *@deprecated
 *@see org.developerworld.commons.excel project
 */
public interface ExcelFactory {

	/**
	 * 创建一个Excel
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public WorkBook createWorkBook(String path) throws IOException;
	
	/**
	 * 打开一个已有的excel
	 * @param path
	 * @return
	 * @throws FileNotFoundException 
	 * @throws IOException 
	 * @throws ExcelException 
	 */
	public WorkBook openWorkBook(String path) throws IOException, ExcelException;
	
}
