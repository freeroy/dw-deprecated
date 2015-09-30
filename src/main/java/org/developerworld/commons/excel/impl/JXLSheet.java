package org.developerworld.commons.excel.impl;

import java.util.ArrayList;
import java.util.List;

import jxl.write.WritableSheet;

import org.developerworld.commons.excel.Row;
import org.developerworld.commons.excel.Sheet;

/**
 * JXL Sheet对象实现类
 * @author Roy Huang
 * @version 20111109
 *@deprecated 不再维护升级
 */
public class JXLSheet implements Sheet {
	
	private WritableSheet writableSheet;
	private int rowCount;

	public JXLSheet(WritableSheet writableSheet) {
		this.writableSheet=writableSheet;
		rowCount=writableSheet.getRows();
	}

	public Row createRow() {
		++rowCount;
		return new JXLRow(writableSheet,rowCount-1);
	}

	public Row createRow(int rowIndex) {
		rowCount=Math.max(rowIndex+1,rowCount);
		return new JXLRow(writableSheet,rowIndex);
	}

	public Row getRow(int rowIndex) {
		return new JXLRow(writableSheet,rowIndex);
	}

	public List<Row> getRows() {
		List<Row> rst=new ArrayList<Row>();
		int len=getRowCount();
		for(int i=0;i<len;i++)
			rst.add(getRow(i));
		return rst;
	}

	public int getRowCount() {
		return Math.max(rowCount,writableSheet.getRows());
	}

	public void removeRow(int rowIndex) {
		writableSheet.removeRow(rowIndex);
		--rowCount;
	}

	public String getName() {
		return writableSheet.getName();
	}

}
