package org.developerworld.tools.excel.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.apache.commons.io.FileUtils;
import org.developerworld.tools.excel.Config;
import org.developerworld.tools.excel.ExcelException;
import org.developerworld.tools.excel.Sheet;
import org.developerworld.tools.excel.WorkBook;

/**
 * JXL excel对象实现类
 * @author Roy Huang
 * @version 20111207
 *
 *@deprecated
 *@see org.developerworld.commons.excel project
 */
public class JXLWorkBook implements WorkBook {

	private WritableWorkbook workBook;
	private int sheetCount;
	private File file;
	private File tmpFile;

	public JXLWorkBook(WritableWorkbook workBook) {
		this.workBook = workBook;
		sheetCount = workBook.getNumberOfSheets();
	}

	public JXLWorkBook(WritableWorkbook workBook, File file, File tmpFile) {
		this(workBook);
		this.file=file;
		this.tmpFile=tmpFile;
	}

	public Sheet createSheet() {
		++sheetCount;
		WritableSheet sheet = workBook.createSheet(Config.DEFAULT_SHEET_NAME
				+ sheetCount, sheetCount - 1);
		return new JXLSheet(sheet);
	}

	public Sheet createSheet(String sheetName) {
		++sheetCount;
		WritableSheet sheet = workBook.createSheet(sheetName, sheetCount - 1);
		return new JXLSheet(sheet);
	}

	public Sheet getSheet(int sheetIndex) {
		WritableSheet sheet = workBook.getSheet(sheetIndex);
		return new JXLSheet(sheet);
	}

	public Sheet getSheet(String sheetName) {
		WritableSheet sheet = workBook.getSheet(sheetName);
		return new JXLSheet(sheet);
	}

	public List<Sheet> getSheets() {
		List<Sheet> rst=new ArrayList<Sheet>();
		int len=getSheetCount();
		for(int i=0;i<len;i++)
			rst.add(getSheet(i));
		return rst;
	}

	public int getSheetCount() {
		return Math.max(sheetCount,workBook.getNumberOfSheets());
	}

	public void removeSheet(int sheetIndex) {
		workBook.removeSheet(sheetIndex);
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
		try {
			workBook.write();
		} catch (IOException e) {
			throw new ExcelException(e);
		}
		//替换原文
		if(file!=null && file.exists() && tmpFile!=null && tmpFile.exists()){
			try {
				workBook.close();
				FileUtils.copyFile(tmpFile, file);
				Workbook _workBook=Workbook.getWorkbook(tmpFile);
				try{
					workBook=Workbook.createWorkbook(tmpFile,_workBook);
				}
				finally{
					_workBook.close();
				}
			} catch (IOException e) {
				throw new ExcelException(e);
			} catch (WriteException e) {
				throw new ExcelException(e);
			} catch (BiffException e) {
				throw new ExcelException(e);
			}
		}
	}

	public void close() throws ExcelException {
		try {
			workBook.close();
		} catch (IOException e) {
			throw new ExcelException(e);
		} catch (WriteException e) {
			throw new ExcelException(e);
		}
		//删除临时文件
		if(tmpFile!=null && tmpFile.exists())
			tmpFile.delete();
	}
}
