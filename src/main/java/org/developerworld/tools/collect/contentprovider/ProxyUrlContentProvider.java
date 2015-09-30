package org.developerworld.tools.collect.contentprovider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.developerworld.tools.collect.ContentProvider;

/**
 * 代理地址内容提供器
 * @author Roy Huang
 * @version 20121210
 *@deprecated
 *@see org.developerworld.commons.collect project
 *
 */
public class ProxyUrlContentProvider implements ContentProvider {

	private final static Log log=LogFactory.getLog(ProxyUrlContentProvider.class);
	private String url;
	private String charset;
	private String proxyHost;
	private int proxyPort = -1;
	private String proxyUsername;
	private String proxyPassword;

	public ProxyUrlContentProvider(String url) {
		this(url, null);
	}

	public ProxyUrlContentProvider(String url, String charset) {
		this.url = url;
		this.charset = charset;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public void setProxyHost(String proxyHost) {
		this.proxyHost = proxyHost;
	}

	public void setProxyPort(int proxyPort) {
		this.proxyPort = proxyPort;
	}

	public void setProxyUsername(String proxyUsername) {
		this.proxyUsername = proxyUsername;
	}

	public void setProxyPassword(String proxyPassword) {
		this.proxyPassword = proxyPassword;
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
			if (StringUtils.isNotBlank(proxyHost) && proxyPort >= 0) {
				// 设置代理服务器的ip地址和端口
				httpClient.getHostConfiguration()
						.setProxy(proxyHost, proxyPort);
				// 使用抢先认证
				httpClient.getParams().setAuthenticationPreemptive(true);
				// 如果代理需要密码验证，这里设置用户名密码
				if (StringUtils.isNotBlank(proxyUsername)
						&& StringUtils.isNotBlank(proxyPassword))
					httpClient.getState().setProxyCredentials(
							AuthScope.ANY,
							new UsernamePasswordCredentials(proxyUsername,
									proxyPassword));
			}
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
