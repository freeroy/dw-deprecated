package org.developerworld.framework.spring.jdbc.aspectj;

import java.sql.Connection;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.developerworld.db.transcode.ConnectionProxy;

/**
 * 数据库连接转码代理拦截器
 * @author Roy Huang
 * @version 20111117
 *
 * @deprecated
 * @see org.developerworld.frameworks.spring project
 */
public class TranscodeConnectionProxyAspect implements MethodInterceptor{

	private static Log log = LogFactory.getLog(TranscodeConnectionProxyAspect.class);
	
	private String appCharset;
	private String dbCharset;

	public TranscodeConnectionProxyAspect(String appCharset,String dbCharset) {
		this.appCharset=appCharset;
		this.dbCharset=dbCharset;
		log.info(getClass() + " new!");
		log.info("the appCharset is "+appCharset+" and dbCharset is "+dbCharset);
	}

	public Object invoke(final MethodInvocation methodInvocation)
			throws Throwable {
		Object rst =methodInvocation.proceed();
		if(rst instanceof Connection)
			rst=new ConnectionProxy((Connection)rst,appCharset,dbCharset).getConnection();
		return rst;
	}

}
