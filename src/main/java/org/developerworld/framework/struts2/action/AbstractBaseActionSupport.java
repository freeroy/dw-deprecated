package org.developerworld.framework.struts2.action;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.util.ServletContextAware;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 提供直接国际化支持的ActionSupport
 * 
 * @author Roy.Huang
 * @version 20110227
 * 
 * @deprecated
 * @see org.developerworld.frameworks.struts2 project
 */
public abstract class AbstractBaseActionSupport extends ActionSupport implements
		ServletContextAware, ServletResponseAware, ServletRequestAware,
		SessionAware, DefaultResult {

	private ObjectMapper objectMapper = new ObjectMapper();
	protected ServletContext servletContext, application;
	protected HttpServletResponse httpServletResponse, response;
	protected HttpServletRequest httpServletRequest, request;
	protected Map<String, Object> session;
	private String responseCharacterEncoding;
	
	/**
	 * 设置输出流编码
	 * @param responseCharacterEncoding
	 */
	public void setResponseCharacterEncoding(String responseCharacterEncoding){
		this.responseCharacterEncoding=responseCharacterEncoding;
	}
	
	/**
	 * 获取输出编码
	 * @return
	 */
	public String getResponseCharacterEncoding(){
		if(StringUtils.isNotEmpty(responseCharacterEncoding))
			return responseCharacterEncoding;
		else if(response!=null && StringUtils.isNotEmpty(response.getCharacterEncoding()))
			return response.getCharacterEncoding();
		else if(request!=null && StringUtils.isNotEmpty(request.getCharacterEncoding()))
			return request.getCharacterEncoding();
		else
			return null;
	}

	/**
	 * 把数据写入上下文
	 * 
	 * @param key
	 * @param value
	 */
	protected void putInActionContext(String key, Object value) {
		getActionContext().put(key, value);
	}
	
	/**
	 * 从上下问获取数据
	 * @param key
	 * @return
	 */
	protected Object getInActionContext(String key){
		return getActionContext().get(key);
	}

	/**
	 * 获取ACTION上下文
	 * 
	 * @return
	 */
	protected ActionContext getActionContext() {
		return ActionContext.getContext();
	}

	/**
	 * 添加key所表示的错误信息
	 * 
	 * @param key
	 */
	protected void addActionErrorByKey(String key) {
		this.addActionError(this.getText(key));
	}

	/**
	 * 添加key所表示的错误国际化信息,找不到的用defaultValue代替
	 * 
	 * @param key
	 * @param defaultValue
	 */
	protected void addActionErrorByKey(String key, String defaultValue) {
		this.addActionError(this.getText(key, defaultValue));
	}

	/**
	 * 添加key所表示的错误国际化信息,并传入参数args
	 * 
	 * @param key
	 * @param args
	 */
	protected void addActionErrorByKey(String key, String args[]) {
		this.addActionError(this.getText(key, args));
	}

	/**
	 * 添加key所表示的错误国际化信息,并传入参数args,找不到的用defauleValue代替
	 * 
	 * @param key
	 * @param defaultValue
	 * @param args
	 */
	protected void addActionErrorByKey(String key, String defaultValue,
			String args[]) {
		this.addActionError(this.getText(key, defaultValue, args));
	}

	/**
	 * 添加key所表示的国际化信息
	 * 
	 * @param key
	 */
	protected void addActionMessageByKey(String key) {
		this.addActionMessage(this.getText(key));
	}

	/**
	 * 添加key所表示的国际化信息,找不到的用defaultValue代替
	 * 
	 * @param key
	 * @param defaultValue
	 */
	protected void addActionMessageByKey(String key, String defaultValue) {
		this.addActionMessage(this.getText(key, defaultValue));
	}

	/**
	 * 添加key所表示的国际化信息,并传入参数args
	 * 
	 * @param key
	 * @param args
	 */
	protected void addActionMessageByKey(String key, String args[]) {
		this.addActionMessage(this.getText(key, args));
	}

	/**
	 * 添加key所表示的国际化信息,并传入参数args,找不到的用defaultValue代替
	 * 
	 * @param key
	 * @param defauleValue
	 * @param args
	 */
	protected void addActionMessageByKey(String key, String defauleValue,
			String args[]) {
		this.addActionMessage(this.getText(key, defauleValue, args));
	}

	/**
	 * 添加key所表示的字段fieldName错误国际化信息
	 * 
	 * @param fieldName
	 * @param key
	 */
	protected void addFieldErrorByKey(String fieldName, String key) {
		this.addFieldError(fieldName, this.getText(key));
	}

	/**
	 * 添加key所表示的字段fieldName错误国际化信息,找不到的用defaultValue代替
	 * 
	 * @param fieldName
	 * @param key
	 * @param defaultValue
	 */
	protected void addFieldErrorByKey(String fieldName, String key,
			String defaultValue) {
		this.addFieldError(fieldName, this.getText(key, defaultValue));
	}

	/**
	 * 添加key所表示的字段fieldName错误国际化信息,并传入参数args
	 * 
	 * @param fieldName
	 * @param key
	 * @param args
	 */
	protected void addFieldErrorByKey(String fieldName, String key, String args[]) {
		this.addFieldError(fieldName, this.getText(key, args));
	}

	/**
	 * 添加key所表示的字段fieldName错误国际化信息,并传入参数args,找不到的用defaultValue代替
	 * 
	 * @param fieldName
	 * @param key
	 * @param defaultValue
	 * @param args
	 */
	protected void addFieldErrorByKey(String fieldName, String key,
			String defaultValue, String args[]) {
	}

	public void setServletContext(ServletContext arg0) {
		servletContext = application = arg0;
	}

	public void setServletResponse(HttpServletResponse arg0) {
		httpServletResponse = response = arg0;
	}

	public void setServletRequest(HttpServletRequest arg0) {
		httpServletRequest = request = arg0;
	}

	public void setSession(Map<String, Object> arg0) {
		session = arg0;
	}

	/**
	 * 输出JSON并返回空视图
	 * 
	 * @param object
	 * @return
	 * @throws JSONException
	 * @throws IOException
	 */
	protected String jsonResult(Object object) throws IOException {
		return jsonResult(object,getResponseCharacterEncoding());
	}
	
	/**
	 * 输出JSON并返回空视图
	 * @param object
	 * @param charset
	 * @return
	 * @throws IOException
	 * @throws JSONException
	 */
	protected String jsonResult(Object object,String charset) throws IOException {
		return jsonResult(objectMapper.writeValueAsString(object),charset);
	}

	/**
	 * 输出JSON并返回空视图
	 * 
	 * @param json
	 * @return
	 * @throws IOException
	 */
	protected String jsonResult(String json) throws IOException {
		return jsonResult(json,getResponseCharacterEncoding());
	}
	
	protected String jsonResult(String json,String charset) throws IOException{
		//response.setContentType("html/text;charset="+charset);
		response.setContentType("application/json-rpc;charset="+charset);
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Expires", "0");
		response.setHeader("Pragma", "No-cache");
		return print(json);
	}
	
	/**
	 * 输出信息到响应
	 * @param message
	 * @return
	 * @throws IOException
	 */
	protected String print(String message) throws IOException{
		print(message,getResponseCharacterEncoding());
		return null;
	}
	
	/**
	 * 输出信息到响应
	 * @param message
	 * @param charset
	 * @return
	 * @throws IOException
	 */
	protected String print(String message,String charset) throws IOException{
		response.setCharacterEncoding(charset);
		response.getWriter().print(message);
		return null;
	}

}
