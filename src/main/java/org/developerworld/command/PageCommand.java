package org.developerworld.command;

/**
 * 分页类
 * 
 * @author Roy.Huang
 * @version 20120116
 * @deprecated
 * @see org.developerworld.commons.command project
 */
public class PageCommand extends org.developerworld.commons.command.PageCommand {

	public PageCommand() {
		super();
	}

	public PageCommand(long total, int pageSize) {
		super(total, pageSize);
	}

	public PageCommand(long total, int pageSize, int jumpPageSize, int pageNum) {
		super(total, pageSize, jumpPageSize, pageNum);
	}
}
