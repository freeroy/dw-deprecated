package org.developerworld.tools.excel.impl;

import java.util.ArrayList;
import java.util.List;

import jxl.write.WritableSheet;

import org.developerworld.tools.excel.Cell;
import org.developerworld.tools.excel.Row;

/**
 * JXL 行对象实现类
 * @author Roy Huang
 * @version 20111109
 *
 *@deprecated
 *@see org.developerworld.commons.excel project
 */
public class JXLRow implements Row {

	private WritableSheet writableSheet;
	private int rowIndex;
	private int cellCount;
	
	public JXLRow(WritableSheet writableSheet,int rowIndex) {
		this.writableSheet=writableSheet;
		this.rowIndex=rowIndex;
		cellCount=writableSheet.getRow(rowIndex).length;
	}

	public Cell createCell() {
		++cellCount;
		return new JXLCell(writableSheet,rowIndex,cellCount-1);
	}

	public Cell createCell(int cellIndex) {
		cellCount=Math.max(cellCount, cellIndex+1);
		return new JXLCell(writableSheet,rowIndex,cellIndex);
	}

	public Cell getCell(int cellIndex) {
		return new JXLCell(writableSheet,rowIndex,cellIndex);
	}

	public List<Cell> getCells() {
		List<Cell> rst=new ArrayList<Cell>();
		int len=getCellCount();
		for(int i=0;i<len;i++)
			rst.add(getCell(i));
		return rst;
	}

	public int getCellCount() {
		return Math.max(writableSheet.getColumns(),cellCount);
	}

	public void removeCell(int cellIndex) {
		writableSheet.removeColumn(cellIndex);
		--cellCount;
	}

}
