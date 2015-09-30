package org.developerworld.tools.excel.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.developerworld.tools.excel.Row;
import org.developerworld.tools.excel.Sheet;

/**
 * POI Sheet对象实现类
 * @author Roy Huang
 * @version 20111109
 *
 *@deprecated
 *@see org.developerworld.commons.excel project
 */
public class POISheet implements Sheet {
	
	private int rowCount;
	private HSSFSheet sheet;

	public POISheet(HSSFSheet sheet) {
		this.sheet=sheet;
		rowCount=sheet.getPhysicalNumberOfRows();
	}

	public Row createRow() {
		++rowCount;
		HSSFRow row=sheet.createRow(rowCount-1);
		return new POIRow(row);
	}

	public Row createRow(int rowIndex) {
		HSSFRow row=sheet.createRow(rowIndex);
		return new POIRow(row);
	}

	public Row getRow(int rowIndex) {
		HSSFRow row=sheet.getRow(rowIndex);
		return new POIRow(row);
	}

	public List<Row> getRows() {
		List<Row> rst=new ArrayList<Row>();
		int len=getRowCount();
		for(int i=0;i<len;i++)
			rst.add(getRow(i));
		return rst;
	}

	public int getRowCount() {
		return Math.max(rowCount,sheet.getPhysicalNumberOfRows());
	}

	public void removeRow(int rowIndex) {
		sheet.removeRow(sheet.getRow(rowIndex));
		--rowCount;
	}

	public String getName() {
		return sheet.getSheetName();
	}

}
