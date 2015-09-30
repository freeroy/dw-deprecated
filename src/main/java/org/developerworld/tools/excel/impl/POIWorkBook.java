package org.developerworld.tools.excel.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.developerworld.tools.excel.Config;
import org.developerworld.tools.excel.ExcelException;
import org.developerworld.tools.excel.Sheet;
import org.developerworld.tools.excel.WorkBook;

/**
 * POI excel对象实现类
 * 
 * @author Roy Huang
 * @version 20111207
 * 
 *@deprecated
 *@see org.developerworld.commons.excel project
 */
public class POIWorkBook implements WorkBook {

	private HSSFWorkbook workbook;
	private File file;
	private int sheetCount;

	public POIWorkBook(HSSFWorkbook workBook, File file) {
		this.workbook = workBook;
		this.file = file;
		sheetCount = workBook.getNumberOfSheets();
	}

	public Sheet createSheet() {
		++sheetCount;
		HSSFSheet sheet = workbook.createSheet(Config.DEFAULT_SHEET_NAME
				+ sheetCount);
		return new POISheet(sheet);
	}

	public Sheet createSheet(String sheetName) {
		++sheetCount;
		HSSFSheet sheet = workbook.createSheet(sheetName);
		return new POISheet(sheet);
	}

	public Sheet getSheet(int sheetIndex) {
		HSSFSheet sheet = workbook.getSheetAt(sheetIndex);
		return new POISheet(sheet);
	}

	public Sheet getSheet(String sheetName) {
		HSSFSheet sheet = workbook.getSheet(sheetName);
		return new POISheet(sheet);
	}

	public List<Sheet> getSheets() {
		List<Sheet> rst = new ArrayList<Sheet>();
		int len = getSheetCount();
		for (int i = 0; i < len; i++)
			rst.add(getSheet(i));
		return rst;
	}

	public int getSheetCount() {
		return Math.max(sheetCount, workbook.getNumberOfSheets());
	}

	public void removeSheet(int sheetIndex) {
		workbook.removeSheetAt(sheetIndex);
		--sheetCount;
	}

	public void saveAndClose() throws ExcelException {
		try {
			save();
		} finally {
			close();
		}
	}

	public void save() throws ExcelException {
		OutputStream os = null;
		try {
			os = new FileOutputStream(file);
			workbook.write(os);
		} catch (FileNotFoundException e) {
			throw new ExcelException(e);
		} catch (IOException e) {
			throw new ExcelException(e);
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					throw new ExcelException(e);
				}
			}
		}
	}

	public void close() throws ExcelException {
		
	}

}
