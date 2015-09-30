package org.developerworld.commons.excel.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.WritableWorkbook;

import org.apache.commons.io.FilenameUtils;
import org.developerworld.commons.excel.ExcelException;
import org.developerworld.commons.excel.ExcelFactory;
import org.developerworld.commons.excel.WorkBook;

/**
 * JXL EXCEL工厂实现类
 * 
 * @author Roy Huang
 * @version 20111207
 * @deprecated 不再维护升级
 */
public class JXLExcelFactory implements ExcelFactory {

	public WorkBook createWorkBook(String path) throws IOException {
		File file = new File(path);
		if (!file.exists())
			file.createNewFile();
		WritableWorkbook work = Workbook.createWorkbook(file);
		return new JXLWorkBook(work);
	}

	public WorkBook openWorkBook(String path) throws IOException, ExcelException {
		File file = new File(path);
		if (!file.exists())
			throw new FileNotFoundException();
		String tmpFileStr=file.getParent()+File.separator+FilenameUtils.getBaseName(file.getName())+"_tmp_"+System.currentTimeMillis()+"."+FilenameUtils.getExtension(file.getName());
		File tmpFile=new File(tmpFileStr);
		if(!tmpFile.exists())
			tmpFile.createNewFile();
		Workbook work=null;
		WritableWorkbook wWork=null;
		try {
			work = Workbook.getWorkbook(file);
			wWork = Workbook.createWorkbook(tmpFile,work);
		} catch (BiffException e) {
			throw new ExcelException(e);
		}
		finally{
			work.close();
		}
		return new JXLWorkBook(wWork,file,tmpFile);
	}
}
