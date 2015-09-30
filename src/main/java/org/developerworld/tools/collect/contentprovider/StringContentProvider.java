package org.developerworld.tools.collect.contentprovider;

import org.developerworld.tools.collect.ContentProvider;

/**
 * 简单字符串内容提供器
 * @author Roy Huang
 * @version 20121210
 *@deprecated
 *@see org.developerworld.commons.collect project
 *
 */
public class StringContentProvider implements ContentProvider{
	
	private String content;
	
	public StringContentProvider(){
		
	}
	
	public StringContentProvider(String content){
		this.content=content;
	}
	
	public void setContent(String content){
		this.content=content;
	}

	public String getContent() {
		return content;
	}

}
