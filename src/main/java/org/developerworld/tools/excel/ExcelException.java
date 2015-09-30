package org.developerworld.tools.excel;

/**
 * excel异常
 * @author Roy Huang
 * @version 20110710
 *
 *@deprecated
 *@see org.developerworld.commons.excel project
 */
public class ExcelException extends Exception {

	private static final long serialVersionUID = 4801764961165959959L;

	public ExcelException() {
		super();
	}

	public ExcelException(String msg) {
		super(msg);
	}

	public ExcelException(String msg, Throwable t) {
		super(msg, t);
	}

	public ExcelException(Throwable t) {
		super(t);
	}

}
