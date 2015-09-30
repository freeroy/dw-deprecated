package org.developerworld.tools.collect.contentprovider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.developerworld.tools.collect.ContentProvider;

/**
 * 链接地址内容提供器
 * 
 * @author Roy Huang
 * @version 20121210
 *@deprecated
 *@see org.developerworld.commons.collect project
 * 
 */
public class UrlContentProvider implements ContentProvider {

	private final static Log log=LogFactory.getLog(UrlContentProvider.class);
	private String url;
	private String charset;

	public UrlContentProvider(String url) {
		this(url, null);
	}

	public UrlContentProvider(String url, String charset) {
		this.url = url;
		this.charset = charset;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getContent() {
		String rst = null;
		try {
			rst = getUrlContent();
		} catch (UnsupportedEncodingException e) {
			log.error(e);
		} catch (IOException e) {
			log.error(e);
		}
		return rst;
	}

	/**
	 * 获取地址的内容
	 * 
	 * @return
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 */
	private String getUrlContent() throws UnsupportedEncodingException,
			IOException {
		String rst = null;
		if (StringUtils.isBlank(url))
			return "";
		GetMethod method = new GetMethod(url);
		try {
			HttpClient httpClient = new HttpClient();
			int status = httpClient.executeMethod(method);
			if (status == HttpStatus.SC_OK) {
				BufferedReader reader = null;
				try {
					if (StringUtils.isBlank(charset))
						reader = new BufferedReader(new InputStreamReader(
								method.getResponseBodyAsStream()));
					else
						reader = new BufferedReader(new InputStreamReader(
								method.getResponseBodyAsStream(), charset));
					StringBuilder sb = new StringBuilder();
					String lineStr = null;
					while ((lineStr = reader.readLine()) != null)
						sb.append(lineStr);
					rst = sb.toString();
				} finally {
					if (reader != null)
						reader.close();
				}
			}
		} finally {
			if (method != null)
				method.releaseConnection();
		}
		return rst;
	}

}
