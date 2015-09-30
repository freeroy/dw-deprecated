package org.developerworld.commons.command;

/**
 * 分页类
 * 
 * @author Roy.Huang
 * @version 20120116
 *  @deprecated 不在提供支持
 */
public class PageCommand {

	private int pageNum = 1;
	private int pageSize = 1;
	private long total=Integer.MAX_VALUE;
	private int jumpPageSize = 1;

	public PageCommand() {
		
	}

	public PageCommand(long total, int pageSize) {
		this(total, pageSize, 1, 1);
	}

	public PageCommand(long total, int pageSize, int jumpPageSize, int pageNum) {
		this.total = total;
		this.pageSize = pageSize;
		this.jumpPageSize = jumpPageSize;
		this.pageNum = pageNum;
		reload();
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public PageCommand pageNum(int pageNum) {
		setPageNum(pageNum);
		return this;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public PageCommand pageSize(int pageSize) {
		setPageSize(pageSize);
		return this;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public PageCommand total(long total) {
		setTotal(total);
		return this;
	}

	public long getTotal() {
		return total;
	}

	public int getTotalPage() {
		return (int) Math.ceil((double) total / pageSize);
	}

	public void setJumpPageSize(int jumpPageSize) {
		this.jumpPageSize = jumpPageSize;
	}

	public PageCommand jumpPageSize(int jumpPageSize) {
		setJumpPageSize(jumpPageSize);
		return this;
	}

	public int getJumpPageSize() {
		return jumpPageSize;
	}

	public int getStartIndex() {
		return (int) Math.min(total, Math.max(0, pageSize * (pageNum - 1)));
	}

	public int getEndIndex() {
		return (int) Math.min(total, Math.max(pageSize * pageNum, getStartIndex()));
	}

	/**
	 * 第一页
	 * 
	 * @return
	 */
	public int firstPage() {
		return gotoPage(1);
	}

	/**
	 * 最后一页
	 * 
	 * @return
	 */
	public int lastPage() {
		return gotoPage(getTotalPage());
	}

	/**
	 * 下一页
	 * 
	 * @return
	 */
	public int nextPage() {
		return gotoPage(pageNum + 1);
	}

	/**
	 * 上一页
	 * 
	 * @return
	 */
	public int prevPage() {
		return gotoPage(pageNum - 1);
	}

	/**
	 * 向前跳页
	 * 
	 * @return
	 */
	public int prevJumpPage() {
		return gotoPage(pageNum - jumpPageSize);
	}

	/**
	 * 向后跳页
	 * 
	 * @return
	 */
	public int nextJumpPage() {
		return gotoPage(pageNum + jumpPageSize);
	}

	/**
	 * 跳到指定页码
	 * 
	 * @param pageNum
	 * @return
	 */
	public int gotoPage(int pageNum) {
		pageNum = Math.max(pageNum, 1);
		pageNum = Math.min(pageNum, getTotalPage());
		this.pageNum = pageNum;
		return pageNum;
	}

	/**
	 * 刷新分页
	 */
	public void reload() {
		total = Math.max(0, total);
		pageSize = Math.max(1, pageSize);
		jumpPageSize = Math.max(1, jumpPageSize);
		pageNum = Math.min(pageNum, getTotalPage());
		pageNum = Math.max(pageNum, 1);
	}

	@Override
	public String toString() {
		return "PageCommand [pageNum=" + pageNum + ", pageSize=" + pageSize
				+ ", total=" + total + ", jumpPageSize=" + jumpPageSize + "]";
	}
}
