package org.developerworld.framework.jpa;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 * JPA工具类
 * 
 * @author Roy Huang
 * @version 20111215
 * @deprecated
 */
public class JpaUtils {

	private static Map<String, Boolean> lazySettings = new HashMap<String, Boolean>();

	/**
	 * 克隆排除延迟加载字段的数据集合
	 * 
	 * @param <T>
	 * @param datas
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public static <T> Collection<T> cloneWithoutLazy(Collection<T> datas)
			throws InstantiationException, IllegalAccessException {
		Collection<T> rst = null;
		if (datas != null) {
			rst = datas.getClass().newInstance();
			for (T data : datas)
				rst.add(cloneWithoutLazy(data));
		}
		return rst;
	}

	/**
	 * 克隆排除延迟加载字段的数据
	 * 
	 * @param <T>
	 * @param data
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public static <T> T cloneWithoutLazy(T data) throws InstantiationException,
			IllegalAccessException {
		T rst = null;
		if (data != null) {
			Class<T> _class = (Class<T>) data.getClass();
			//判断是否有实体注解
			if (_class.isAnnotationPresent(Entity.class)) {
				//创建对象实例
				rst = _class.newInstance();
				//获取类的所有方法
				Method[] methods = _class.getMethods();
				for (Method method : methods) {
					try {
						if (method.getName().startsWith("get")) {
							Method getMethod = method;
							String field = method.getName().substring(3);
							String setMethodName = "set" + field;
							Class<?> returnType = method.getReturnType();
							Method setMethod = _class.getMethod(setMethodName,
									returnType);
							if (setMethod != null) {
								// 代表是标准get/set字段
								// 判断是否有延迟加载标识
								if (isLazy(_class, field))
									// 是延迟加载的字段,执行特殊获取数据方法
									setMethod
											.invoke(rst,
													getLazyMetohdReturn(data,
															getMethod));
								else
									setMethod.invoke(rst,
											getMethod.invoke(data));
							}
						}
					} catch (Throwable t) {
						// warn(t);
					}
				}
			} else
				rst = data;
		}
		return rst;
	}

	/**
	 * 获取延迟加载方法的属性值
	 * 
	 * @param <T>
	 * @param object
	 * @param getMethod
	 * @return
	 */
	private static <T> Object getLazyMetohdReturn(T object, Method getMethod) {
		Object rst = null;
		try {
			rst = getMethod.invoke(object);
			// 获取返回值对象后，执行其toString方法，测试是否存在延迟加载
			if (rst != null) {
				if (Iterable.class.isInstance(rst))
					((Iterable) rst).iterator().hasNext();
				else if (Map.class.isInstance(rst))
					((Map) rst).size();
				else
					rst.toString();
			}
		} catch (Throwable t) {
			// 若出错，代表存在延迟加载，返回空值
			rst = null;
		}
		return rst;
	}

	/**
	 * 判断字段是否延迟加载
	 * 
	 * @param _class
	 * @param fieldName
	 * @return
	 */
	private static boolean isLazy(Class _class, String fieldName) {
		// 利用缓存，提高效率
		String cacheKey = _class.getName() + ":" + fieldName;
		if (lazySettings.containsKey(cacheKey))
			return lazySettings.get(cacheKey);
		// 不存配置，默认先设置为true
		lazySettings.put(cacheKey, true);

		fieldName = fieldName.substring(0, 1).toLowerCase()
				+ fieldName.substring(1);
		Field field = null;
		// 查找私有成员变量是否有配置
		try {
			field = _class.getDeclaredField(fieldName);
			if (isLazyField(field))
				return true;
		} catch (SecurityException e) {
		} catch (NoSuchFieldException e) {
		}
		// 查找公有成员变量是否有配置
		try {
			field = _class.getField(fieldName);
			if (isLazyField(field))
				return true;
		} catch (SecurityException e) {
		} catch (NoSuchFieldException e) {
		}
		// 检查get方法是否有配置
		fieldName = fieldName.substring(0, 1).toUpperCase()
				+ fieldName.substring(1);
		Method method;
		try {
			method = _class.getMethod("get" + fieldName);
			if (isLazyMethod(method))
				return true;
		} catch (SecurityException e) {
		} catch (NoSuchMethodException e) {
		}
		// 修正缓存设置
		lazySettings.put(cacheKey, false);
		return false;
	}

	/**
	 * 是否延迟加载成员变量
	 * 
	 * @param field
	 * @return
	 */
	private static boolean isLazyField(Field field) {
		if (field != null) {
			if (isLazyBasicAnnotation(field))
				return true;
			else if (isLazyOneToOneAnnotation(field))
				return true;
			else if (isLazyOneToManyAnnotation(field))
				return true;
			else if (isLazyManyToOneAnnotation(field))
				return true;
			else if (isLazyManyToManyAnnotation(field))
				return true;
		}
		return false;
	}

	/**
	 * 是否延迟加载方法
	 * 
	 * @param method
	 * @return
	 */
	private static boolean isLazyMethod(Method method) {
		if (method != null) {
			if (isLazyBasicAnnotation(method))
				return true;
			else if (isLazyOneToOneAnnotation(method))
				return true;
			else if (isLazyOneToManyAnnotation(method))
				return true;
			else if (isLazyManyToOneAnnotation(method))
				return true;
			else if (isLazyManyToManyAnnotation(method))
				return true;
		}
		return false;
	}

	/**
	 * 是否有Basic注解并延迟加载
	 * @param field
	 * @return
	 */
	private static boolean isLazyBasicAnnotation(Field field) {
		if (field.isAnnotationPresent(Basic.class)) {
			Basic annotation = field.getAnnotation(Basic.class);
			if (annotation.fetch() != null
					&& annotation.fetch().equals(FetchType.LAZY))
				return true;
		}
		return false;
	}

	/**
	 * 是否有Basic注解并延迟加载
	 * @param method
	 * @return
	 */
	private static boolean isLazyBasicAnnotation(Method method) {
		if (method.isAnnotationPresent(Basic.class)) {
			Basic annotation = method.getAnnotation(Basic.class);
			if (annotation.fetch() != null
					&& annotation.fetch().equals(FetchType.LAZY))
				return true;
		}
		return false;
	}

	/**
	 * 是否有一对一注解并延迟加载
	 * @param field
	 * @return
	 */
	private static boolean isLazyOneToOneAnnotation(Field field) {
		if (field.isAnnotationPresent(OneToOne.class)) {
			OneToOne annotation = field.getAnnotation(OneToOne.class);
			if (annotation.fetch() != null
					&& annotation.fetch().equals(FetchType.LAZY))
				return true;
		}
		return false;
	}

	/**
	 * 是否有一对一注解并延迟加载
	 * @param method
	 * @return
	 */
	private static boolean isLazyOneToOneAnnotation(Method method) {
		if (method.isAnnotationPresent(OneToOne.class)) {
			OneToOne annotation = method.getAnnotation(OneToOne.class);
			if (annotation.fetch() != null
					&& annotation.fetch().equals(FetchType.LAZY))
				return true;
		}
		return false;
	}

	/**
	 * 是否有一对多注解并延迟加载
	 * @param field
	 * @return
	 */
	private static boolean isLazyOneToManyAnnotation(Field field) {
		if (field.isAnnotationPresent(OneToMany.class)) {
			OneToMany annotation = field.getAnnotation(OneToMany.class);
			if (annotation.fetch() != null
					&& annotation.fetch().equals(FetchType.LAZY))
				return true;
		}
		return false;
	}

	/**
	 * 是否有一对多注解并延迟加载
	 * @param method
	 * @return
	 */
	private static boolean isLazyOneToManyAnnotation(Method method) {
		if (method.isAnnotationPresent(OneToMany.class)) {
			OneToMany annotation = method.getAnnotation(OneToMany.class);
			if (annotation.fetch() != null
					&& annotation.fetch().equals(FetchType.LAZY))
				return true;
		}
		return false;
	}

	/**
	 * 是否有多对一注解并延迟加载
	 * @param field
	 * @return
	 */
	private static boolean isLazyManyToOneAnnotation(Field field) {
		if (field.isAnnotationPresent(ManyToOne.class)) {
			ManyToOne annotation = field.getAnnotation(ManyToOne.class);
			if (annotation.fetch() != null
					&& annotation.fetch().equals(FetchType.LAZY))
				return true;
		}
		return false;
	}

	private static boolean isLazyManyToOneAnnotation(Method method) {
		if (method.isAnnotationPresent(ManyToOne.class)) {
			ManyToOne annotation = method.getAnnotation(ManyToOne.class);
			if (annotation.fetch() != null
					&& annotation.fetch().equals(FetchType.LAZY))
				return true;
		}
		return false;
	}

	/**
	 * 是否有多对多注解并延迟加载
	 * @param field
	 * @return
	 */
	private static boolean isLazyManyToManyAnnotation(Field field) {
		if (field.isAnnotationPresent(ManyToMany.class)) {
			ManyToMany annotation = field.getAnnotation(ManyToMany.class);
			if (annotation.fetch() != null
					&& annotation.fetch().equals(FetchType.LAZY))
				return true;
		}
		return false;
	}

	/**
	 * 是否有多对多注解并延迟加载
	 * @param method
	 * @return
	 */
	private static boolean isLazyManyToManyAnnotation(Method method) {
		if (method.isAnnotationPresent(ManyToMany.class)) {
			ManyToMany annotation = method.getAnnotation(ManyToMany.class);
			if (annotation.fetch() != null
					&& annotation.fetch().equals(FetchType.LAZY))
				return true;
		}
		return false;
	}
}
