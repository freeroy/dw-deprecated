package org.developerworld.framework.hibernate3;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.developerworld.db.dao.GenericDao;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;

/**
 * Hibernate GenericDao 实现
 * 
 * @author Roy.Huang
 * @version 20110421
 * @deprecated
 * @see org.developerworld.frameworks.hibernate3 project
 * @param <T>
 * @param <PK>
 */
public abstract class AbstractHibernateGenericDaoImpl<T, PK extends Serializable>
		implements GenericDao<T, PK> {

	private Class<T> entityClass;

	private SessionFactory sessionFactory;

	public AbstractHibernateGenericDaoImpl(SessionFactory sessionFactory) {
		entityClass = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
		this.sessionFactory = sessionFactory;
	}

	public Class<T> getEntityClass() {
		return entityClass;
	}

	public String getEntityClassName() {
		return getEntityClass().getName();
	}

	public long findCount() {
		return (Long) sessionFactory.getCurrentSession()
				.createCriteria(entityClass)
				.setProjection(Projections.rowCount()).setMaxResults(1)
				.uniqueResult();
	}

	public long findCountByExample(T example) {
		return (Long) sessionFactory.getCurrentSession()
				.createCriteria(entityClass).add(Example.create(example))
				.setProjection(Projections.rowCount()).setMaxResults(1)
				.uniqueResult();
	}

	public void save(T entity) {
		sessionFactory.getCurrentSession().save(entity);
	}

	public void update(T entity) {
		sessionFactory.getCurrentSession().update(entity);
	}

	public void delete(T entity) {
		sessionFactory.getCurrentSession().delete(entity);
	}

	public void delete(PK id) {
		delete((T) sessionFactory.getCurrentSession().load(entityClass, id));
	}

	public void delete(Collection<T> entitys) {
		Iterator<T> is = entitys.iterator();
		while (is.hasNext())
			sessionFactory.getCurrentSession().delete(is.next());
	}

	public void delete(PK[] ids) {
		for (PK id : ids)
			delete(id);
	}

	public T findById(PK id) {
		return (T) sessionFactory.getCurrentSession().get(entityClass, id);
	}

	public List<T> findList() {
		return sessionFactory.getCurrentSession().createCriteria(entityClass)
				.list();
	}

	public List<T> findList(int pageNum, int pageSize) {
		return sessionFactory.getCurrentSession().createCriteria(entityClass)
				.setFirstResult((pageNum - 1) * pageSize)
				.setMaxResults(pageSize).list();
	}

	public List<T> findList(String order, int pageNum, int pageSize) {
		String hql = "from " + getEntityClassName()
				+ (StringUtils.isBlank(order) ? "" : " order by " + order);
		return sessionFactory.getCurrentSession().createQuery(hql)
				.setFirstResult((pageNum - 1) * pageSize)
				.setMaxResults(pageSize).list();
	}

	public List<T> findList(String order) {
		String hql = "from " + getEntityClassName()
				+ (StringUtils.isBlank(order) ? "" : " order by " + order);
		return sessionFactory.getCurrentSession().createQuery(hql).list();
	}

	public List<T> findByExample(T example) {
		return sessionFactory.getCurrentSession()
				.createCriteria(example.getClass())
				.add(Example.create(example)).list();
	}

	public List<T> findByExample(T example, int pageNum, int pageSize) {
		return sessionFactory.getCurrentSession()
				.createCriteria(example.getClass())
				.add(Example.create(example)).setFirstResult((pageNum - 1))
				.setMaxResults(pageSize).list();
	}

	public List<T> findByExample(T example, int pageNum, int pageSize,
			String order) {
		DetachedCriteria dc = buildDetachedCriteria();
		if (StringUtils.isNotEmpty(order)) {
			String[] orders = order.split(",");
			if (orders != null) {
				for (String o : orders) {
					String[] _order = o.split(" ");
					if (_order == null || _order.length == 0)
						continue;
					if (o.toLowerCase().indexOf(" desc") != -1)
						dc.addOrder(Order.desc(_order[0]));
					else
						dc.addOrder(Order.asc(_order[0]));
				}
			}
		}
		return dc.getExecutableCriteria(sessionFactory.getCurrentSession())
				.setFirstResult((pageNum - 1) * pageSize)
				.setMaxResults(pageSize).list();
	}

	/**
	 * 创建DetachedCriteria对象
	 * 
	 * @return
	 */
	protected DetachedCriteria buildDetachedCriteria() {
		return DetachedCriteria.forClass(entityClass);
	}

}
