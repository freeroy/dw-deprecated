package org.developerworld.commons.excel.impl;

import java.util.Date;

import jxl.DateCell;
import jxl.NumberCell;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.developerworld.commons.excel.Cell;
import org.developerworld.commons.excel.ExcelException;

/**
 * jxl 列对象实现类
 * 
 * @author Roy Huang
 * @version 20111109
 * @deprecated 不再维护升级
 */
public class JXLCell implements Cell {

	private WritableSheet writableSheet;
	private int rowIndex;
	private int cellIndex;

	public JXLCell(WritableSheet writableSheet, int rowIndex, int cellIndex) {
		this.writableSheet = writableSheet;
		this.rowIndex = rowIndex;
		this.cellIndex = cellIndex;
	}

	public void writeText(String value) throws ExcelException {
		try {
			Label labelC = new Label(cellIndex, rowIndex, value);
			writableSheet.addCell(labelC);
		} catch (RowsExceededException e) {
			throw new ExcelException(e);
		} catch (WriteException e) {
			throw new ExcelException(e);
		}
	}

	public void writeNumber(Number value) throws ExcelException {
		try {
			jxl.write.Number n = new jxl.write.Number(cellIndex, rowIndex,
					value.doubleValue());
			writableSheet.addCell(n);
		} catch (RowsExceededException e) {
			throw new ExcelException(e);
		} catch (WriteException e) {
			throw new ExcelException(e);
		}
	}

	public void writeDate(Date date) throws ExcelException {
		try {
			jxl.write.DateTime dt = new jxl.write.DateTime(cellIndex, rowIndex,
					date);
			writableSheet.addCell(dt);
		} catch (RowsExceededException e) {
			throw new ExcelException(e);
		} catch (WriteException e) {
			throw new ExcelException(e);
		}
	}

	public String readText() throws ExcelException {
		return writableSheet.getCell(cellIndex, rowIndex).getContents();
	}

	public Number readNumber() throws ExcelException {
		jxl.Cell cell = getCell();
		return ((NumberCell) cell).getValue();
	}

	public Date readDate() throws ExcelException {
		jxl.Cell cell = getCell();
		return ((DateCell) cell).getDate();
	}

	/**
	 * 获取列对象
	 * 
	 * @return
	 */
	private jxl.Cell getCell() {
		return writableSheet.getCell(cellIndex, rowIndex);
	}

}
