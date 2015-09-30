package org.developerworld.framework.spring.orm.jpa;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.developerworld.db.dao.GenericDao;
import org.developerworld.framework.spring.orm.jpa.support.DwJpaDaoSupport;

/**
 * 针对JPA的持久层基础类
 * 
 * @author Roy Huang
 * @version 20120328
 * 
 * @deprecated
 * @see org.developerworld.frameworks.spring project
 * @param <T>
 * @param <PK>
 */
public abstract class AbstractJpaGenericDaoImpl<T, PK extends Serializable>
		extends DwJpaDaoSupport implements GenericDao<T, PK> {

	protected Class<T> entityClass;

	public AbstractJpaGenericDaoImpl() {
		entityClass = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}

	public Class<T> getEntityClass() {
		return entityClass;
	}

	public String getEntityClassName() {
		return getEntityClass().getName();
	}

	public void save(T entity) {
		getDwJpaTemplate().persist(entity);
	}

	public void update(T entity) {
		getDwJpaTemplate().merge(entity);
	}

	public void delete(T entity) {
		getDwJpaTemplate().remove(entity);
	}

	public void delete(final PK id) {
		delete(getDwJpaTemplate().getReference(entityClass, id));
	}

	public void delete(Collection<T> entitys) {
		for (T entity : entitys)
			delete(entity);
	}

	public void delete(PK[] ids) {
		for (PK id : ids)
			delete(id);
	}

	public long findCount() {
		return getDwJpaTemplate().findLong(
				"select count(*) from " + getEntityClassName());
	}

	public T findById(PK id) {
		return getDwJpaTemplate().find(entityClass, id);
	}

	public List<T> findList() {
		return getDwJpaTemplate().find("from " + getEntityClassName());
	}

	public List<T> findList(int pageNum, int pageSize) {
		return getDwJpaTemplate().find("from " + getEntityClassName(),
				(pageNum - 1) * pageSize, pageSize);
	}

	public List<T> findList(String order, int pageNum, int pageSize) {
		String hql = "from " + getEntityClassName()
				+ (StringUtils.isBlank(order) ? "" : " order by " + order);
		return getDwJpaTemplate().find(hql, (pageNum - 1) * pageSize, pageSize);
	}

	public List<T> findList(String order) {
		String hql = "from " + getEntityClassName()
				+ (StringUtils.isBlank(order) ? "" : " order by " + order);
		return getDwJpaTemplate().find(hql);
	}

}
