package org.developerworld.tools.excel.impl;

import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.developerworld.tools.excel.Cell;
import org.developerworld.tools.excel.ExcelException;

/**
 * POI 列对象实现类
 * @author Roy Huang
 * @version 20111109
 *
 *@deprecated
 *@see org.developerworld.commons.excel project
 */
public class POICell implements Cell {
	
	private HSSFCell cell;

	public POICell(HSSFCell cell) {
		this.cell=cell;
	}

	public void writeText(String value) {
		cell.setCellValue(value);
	}

	public void writeNumber(Number value) throws ExcelException {
		cell.setCellValue(value.doubleValue());
	}

	public void writeDate(Date date) throws ExcelException {
		cell.setCellValue(date);
	}

	public String readText() throws ExcelException {
		return cell.getStringCellValue();
	}

	public Number readNumber() throws ExcelException {
		return cell.getNumericCellValue();
	}

	public Date readDate() throws ExcelException {
		return cell.getDateCellValue();
	}

}
