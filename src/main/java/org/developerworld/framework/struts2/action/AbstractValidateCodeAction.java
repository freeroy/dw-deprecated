package org.developerworld.framework.struts2.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.developerworld.tools.gvc.HttpValidateCodeBuilder;
import org.developerworld.tools.gvc.ValidateBackground;
import org.developerworld.tools.gvc.ValidateCode;

import com.opensymphony.xwork2.Action;

/**
 * 验证码struts2支持类
 * 
 * @author Roy Huang
 * @version 20111019
 * 
 * @deprecated
 * @see org.developerworld.frameworks.struts2 project
 */
public abstract class AbstractValidateCodeAction implements Action {
	
	private static Log log=LogFactory.getLog(AbstractValidateCodeAction.class);

	private String outputImageType = "JPEG";
	private String sessionKey = this.getClass().getName();
	private int codeCount = 4;
	private int backgroundType = ValidateBackground.LINE_BACKGROUND;
	private int width = 100;
	private int height = 50;
	private String codeType = ValidateCode.CODE_TYPE_NUMBER_AND_ENGLISH;
	private String contentType = "image/jpeg";
	private HttpValidateCodeBuilder builder;

	public AbstractValidateCodeAction() {
		super();
		setParams();
		init();
	}

	public void init() {
		builder = new HttpValidateCodeBuilder();
		builder.setOutputImageType(outputImageType);
		builder.setCodeCount(codeCount);
		builder.setWidth(width);
		builder.setHeight(height);
		builder.setCodeType(codeType);
		builder.setBackgroundType(backgroundType);
		builder.setContentType(contentType);
	}

	public void setOutputImageType(String outputImageType) {
		this.outputImageType = outputImageType;
	}

	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}

	public void setCodeCount(int codeCount) {
		this.codeCount = codeCount;
	}

	public void setBackgroundType(int backgroundType) {
		this.backgroundType = backgroundType;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	/**
	 * 设置参数方法
	 */
	public abstract void setParams();

	/**
	 * 验证码生成
	 * 
	 * @return
	 */
	private String validateImageResult() {
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			builder.build(request, response, sessionKey);
		} catch (IOException e) {
			log.error(e);
		}
		return null;
	}

	public String execute() {
		return validateImageResult();
	}
}
