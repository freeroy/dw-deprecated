package org.developerworld.commons.db.transcode.aspectj;

import java.sql.Connection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.developerworld.commons.db.transcode.ConnectionProxy;

/**
 * 数据库连接转码代理拦截器
 * 
 * @author Roy Huang
 * @version 20111117
 * @deprecated see org.developerworld.commons.dbtranscode
 */
public class TranscodeConnectionProxyAspect {

	private static Log log = LogFactory
			.getLog(TranscodeConnectionProxyAspect.class);

	private String appCharset;
	private String dbCharset;

	public TranscodeConnectionProxyAspect(String appCharset, String dbCharset) {
		this.appCharset = appCharset;
		this.dbCharset = dbCharset;
		log.info(getClass() + " new!");
		log.info("the appCharset is " + appCharset + " and dbCharset is "
				+ dbCharset);
	}

	public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
		Object rst = pjp.proceed();
		if (appCharset != null && dbCharset != null
				&& !appCharset.equalsIgnoreCase(dbCharset)) {
			if (rst instanceof Connection)
				rst = new ConnectionProxy((Connection) rst, appCharset,
						dbCharset).getConnection();
		}
		return rst;
	}

}
