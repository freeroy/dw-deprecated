package org.developerworld.tools.cache.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 缓存销毁注解集合
 * 
 * @author Roy Huang
 * @version 20111009
 * 
 *@deprecated
 *@see org.developerworld.commons.cache project
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface CacheEvicts {

	CacheEvict[] value();
}
