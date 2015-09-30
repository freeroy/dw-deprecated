package org.developerworld.webui.datagrid;

import org.developerworld.command.PageCommand;

/**
 * 数据网格分页对象
 * @author Roy Huang
 * @version 20110415
 *
 *@deprecated
 *@see org.developerworld.frameworks.webui project
 */
public class DataGridPage {

	private int pageNum;
	private int pageSize;
	private long total;
	
	public DataGridPage(){
		
	}
	
	public DataGridPage(PageCommand pageComand){
		if(pageComand!=null){
			this.pageNum=pageComand.getPageNum();
			this.pageSize=pageComand.getPageSize();
			this.total=pageComand.getTotal();
		}
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}
	
}
