package org.developerworld.commons.excel.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.developerworld.commons.excel.Cell;
import org.developerworld.commons.excel.Row;

/**
 * POI 行对象实现类
 * @author Roy Huang
 * @version 20111109
 *@deprecated 不再维护升级
 */
public class POIRow implements Row {
	
	private HSSFRow row;
	private int cellCount;

	public POIRow(HSSFRow row) { 
		this.row=row;
		cellCount=row.getPhysicalNumberOfCells();
	}

	public Cell createCell() {
		++cellCount;
		HSSFCell cell=row.createCell(cellCount-1);
		return new POICell(cell);
	}

	public Cell createCell(int cellIndex) {
		HSSFCell cell=row.createCell(cellIndex);
		return new POICell(cell);
	}

	public Cell getCell(int cellIndex) {
		HSSFCell cell=row.getCell(cellIndex);
		return new POICell(cell);
	}

	public List<Cell> getCells() {
		List<Cell> rst=new ArrayList<Cell>();
		int len=getCellCount();
		for(int i=0;i<len;i++)
			rst.add(getCell(i));
		return rst;
	}

	public int getCellCount() {
		return Math.max(cellCount, row.getPhysicalNumberOfCells());
	}

	public void removeCell(int cellIndex) {
		row.removeCell(row.getCell(cellIndex));
		--cellCount;
	}

}
