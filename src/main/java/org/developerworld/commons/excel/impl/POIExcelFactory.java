package org.developerworld.commons.excel.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.developerworld.commons.excel.ExcelFactory;
import org.developerworld.commons.excel.WorkBook;

/**
 * POI EXCEL工厂实现类
 * @author Roy Huang
 * @version 20111109
 *@deprecated 不再维护升级
 */
public class POIExcelFactory implements ExcelFactory {

	public WorkBook createWorkBook(String path) throws IOException {
		File file = new File(path);
		if (!file.exists())
			file.createNewFile();
		HSSFWorkbook workBook = new HSSFWorkbook();
		return new POIWorkBook(workBook, file);
	}

	public WorkBook openWorkBook(String path) throws IOException {
		File file = new File(path);
		if (!file.exists())
			throw new FileNotFoundException();
		HSSFWorkbook workBook = new HSSFWorkbook(new FileInputStream(file));
		return new POIWorkBook(workBook, file);
	}

}
