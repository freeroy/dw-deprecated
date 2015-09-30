package org.developerworld.servlet.jsp.tagext;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Stack;
import java.util.Map.Entry;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 标签支持类
 * @author Roy Huang
 * @version 20120905
 *
 * @deprecated
 * @see org.developerworld.commons.servlet project
 */
public abstract class TagSupport extends javax.servlet.jsp.tagext.TagSupport{
	
	private static Log log = LogFactory.getLog(TagSupport.class);

	private Stack<Map<String, Object>> localVariableStack = new Stack<Map<String, Object>>();
	private Stack<Map<String, Object>> pageAttributeStack = new Stack<Map<String, Object>>();
	private Stack<Map<String, Object>> requestAttributeStack = new Stack<Map<String, Object>>();

	public HttpServletRequest getRequest() {
		return (HttpServletRequest) pageContext.getRequest();
	}

	public HttpServletResponse getResponse() {
		return (HttpServletResponse) pageContext.getResponse();
	}

	public HttpSession getSession() {
		return pageContext.getSession();
	}

	public ServletContext getServletContext() {
		return pageContext.getServletContext();
	}

	public Exception getException() {
		return pageContext.getException();
	}

	public ServletConfig getConfig() {
		return pageContext.getServletConfig();
	}
	
	public JspWriter getOut(){
		return pageContext.getOut();
	}
	
	/**
	 * 设置PageContext变量，并提取jsp内置变量
	 */
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);
	}

	@Override
	public int doStartTag() throws JspException {
		localVariableStack.push(new HashMap<String, Object>());
		pageAttributeStack.push(new HashMap<String, Object>());
		requestAttributeStack.push(new HashMap<String, Object>());
		return super.doStartTag();
	}

	@Override
	public int doEndTag() throws JspException {
		localVariableStack.pop();
		clearLocalPageAttributes();
		pageAttributeStack.pop();
		clearLocalRequestAttributes();
		requestAttributeStack.pop();
		return super.doEndTag();
	}

	/**
	 * 获取局部变量值
	 * 
	 * @param key
	 * @param value
	 */
	protected void setLocalVariable(String key, Object value) {
		localVariableStack.peek().put(key, value);
	}

	/**
	 * 设置局部变量值
	 * 
	 * @param key
	 * @return
	 */
	public Object getLocalVariable(String key) {
		return localVariableStack.peek().get(key);
	}

	/**
	 * 设置局部范围变量
	 * 
	 * @param key
	 * @param value
	 */
	protected void setLocalPageAttribute(String key, Object value) {
		pageAttributeStack.peek().put(key, pageContext.getAttribute(key));
		pageContext.setAttribute(key, value, PageContext.PAGE_SCOPE);
	}

	/**
	 * 获取局部范围变量值
	 * 
	 * @param key
	 * @return
	 * @throws JspException
	 */
	public Object getLocalPageAttribute(String key) throws JspException {
		return pageContext.getAttribute(key, PageContext.PAGE_SCOPE);
	}

	/**
	 * 设置局部范围变量
	 * 
	 * @param key
	 */
	protected void removeLocalPageAttribute(String key) {
		pageContext.setAttribute(key, pageAttributeStack.peek().get(key),
				PageContext.PAGE_SCOPE);
		pageAttributeStack.peek().remove(key);
	}

	/**
	 * 清楚所有局部变量
	 */
	protected void clearLocalPageAttributes() {
		Iterator<Entry<String, Object>> entrys = pageAttributeStack.peek()
				.entrySet().iterator();
		while (entrys.hasNext()) {
			Entry<String, Object> e = entrys.next();
			pageContext.setAttribute(e.getKey(), e.getValue(),
					PageContext.PAGE_SCOPE);
		}
		pageAttributeStack.peek().clear();
	}

	/**
	 * 备份request变量
	 * 
	 * @param key
	 * @return
	 */
	protected void setLocalRequestAttribute(String key, Object value) {
		requestAttributeStack.peek().put(key, getRequest().getAttribute(key));
		getRequest().setAttribute(key, value);
	}

	/**
	 * 获取局部范围变量值
	 * 
	 * @param key
	 * @return
	 * @throws JspException
	 */
	public Object getLocalRequestAttribute(String key) throws JspException {
		return getRequest().getAttribute(key);
	}

	/**
	 * 设置局部范围变量
	 * 
	 * @param key
	 */
	protected void removeLocalRequestAttribute(String key) {
		getRequest().setAttribute(key, requestAttributeStack.peek().get(key));
		requestAttributeStack.peek().remove(key);
	}

	/**
	 * 清楚所有局部变量
	 */
	protected void clearLocalRequestAttributes() {
		Iterator<Entry<String, Object>> entrys = requestAttributeStack.peek()
				.entrySet().iterator();
		while (entrys.hasNext()) {
			Entry<String, Object> e = entrys.next();
			getRequest().setAttribute(e.getKey(), e.getValue());
		}
		requestAttributeStack.peek().clear();
	}

	/**
	 * 获取国际化信息
	 * 
	 * @return
	 */
	protected Locale getDefauleLocal() {
		if (getRequest().getLocale() != null)
			return getRequest().getLocale();
		else
			return Locale.getDefault();
	}

	/*
	 * 日志相关操作
	 */
	public void debug(Object message) {
		log.debug(this.getClass() + ":" + message);
	}

	public void error(Object message, Throwable t) {
		log.error(message, t);
	}

	public void error(Object message) {
		log.error(this.getClass() + ":" + message);
	}

	public void info(Object message) {
		log.info(this.getClass() + ":" + message);
	}

	public void fatal(Object message) {
		log.fatal(this.getClass() + ":" + message);
	}

	public void trace(Object message) {
		log.trace(this.getClass() + ":" + message);
	}

	public void warn(Object message) {
		log.warn(this.getClass() + ":" + message);
	}

	public void warn(Object message, Throwable t) {
		log.warn(message, t);
	}

	public void console(Object message) {
		System.out.println(this.getClass() + ":" + message);
	}

	/*
	 * 生成JspException
	 */
	public void throwJspException(Throwable e) throws JspException {
		throwJspException(e.getMessage(), e);
	}

	public void throwJspException(String msg) throws JspException {
		throwJspException(msg, new Exception(msg));
	}

	public void throwJspException(String msg, Throwable e) throws JspException {
		error(msg, e);
		throw new JspException(msg, e);
	}

}
