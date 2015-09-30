package org.developerworld.framework.spring.jpa.aspectj;

import java.util.Collection;

import javax.persistence.Entity;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.developerworld.framework.jpa.JpaUtils;
import org.springframework.core.Ordered;

/**
 * JPA延迟加载过滤拦截器
 * 
 * @author Roy Huang
 * @version 20111215
 * 
 * @deprecated
 * @see org.developerworld.frameworks.spring project
 */
public class JpaLazyDataHandlerAspect implements MethodInterceptor, Ordered {

	private static Log log = LogFactory.getLog(JpaLazyDataHandlerAspect.class);

	private int order;

	public JpaLazyDataHandlerAspect() {
		log.info(getClass() + " new!");
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public int getOrder() {
		return order;
	}

	public Object invoke(final MethodInvocation methodInvocation)
			throws Throwable {
		Object rst = methodInvocation.proceed();
		if (rst != null) {
			try {
				// 判断是否列表
				if (Collection.class.isInstance(rst)) {
					// 提出当中的一个判断其类型
					Collection collection = ((Collection) rst);
					if (collection.size() > 0) {
						Object tmp = collection.iterator().next();
						if (tmp.getClass().isAnnotationPresent(Entity.class))
							rst = JpaUtils.cloneWithoutLazy(collection);
					}
				} else if (rst.getClass().isAnnotationPresent(Entity.class))
					rst = JpaUtils.cloneWithoutLazy(rst);
			} catch (Throwable t) {
				log.warn(t);
			}
		}
		return rst;
	}

}
