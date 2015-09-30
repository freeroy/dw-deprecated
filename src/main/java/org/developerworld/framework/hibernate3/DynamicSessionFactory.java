package org.developerworld.framework.hibernate3;

import java.io.Serializable;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.naming.NamingException;
import javax.naming.Reference;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.classic.Session;
import org.hibernate.Cache;
import org.hibernate.HibernateException;
import org.hibernate.Interceptor;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.TypeHelper;
import org.hibernate.engine.FilterDefinition;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.metadata.CollectionMetadata;
import org.hibernate.stat.Statistics;

/**
 * Hibernate SessionFactory 动态切换版本
 * 
 * @author Roy Huang
 * @version 20111230
 * @deprecated
 * @see org.developerworld.frameworks.hibernate3 project
 * 
 */
public class DynamicSessionFactory implements SessionFactory {

	private static Log log = LogFactory.getLog(DynamicSessionFactory.class);

	private SessionFactory defaultSessionFactory;
	private Map<String, SessionFactory> sessionFactorys = new HashMap<String, SessionFactory>();

	/**
	 * 设置默认的待选择的SessionFactory
	 * 
	 * @param sessionFactory
	 */
	public void setSessionFactorys(Map<String, SessionFactory> sessionFactorys) {
		if (sessionFactorys != null)
			this.sessionFactorys = sessionFactorys;
	}

	/**
	 * 设置默认的sessionFactory;
	 * 
	 * @param defaultSessionFactory
	 */
	public void setDefaultSessionFactory(SessionFactory defaultSessionFactory) {
		this.defaultSessionFactory = defaultSessionFactory;
	}

	/**
	 * 获取Sessionfactory
	 * 
	 * @return
	 */
	public SessionFactory getSessionFactory() {
		SessionFactory rst = defaultSessionFactory;
		String sessionFactoryKey = DynamicSessionFactoryHolder
				.getSessionFactoryKey();
		log.debug("the SessionFactoryHolder.getSessionFactoryKey is "
				+ sessionFactoryKey);
		if (sessionFactorys != null
				&& sessionFactorys.containsKey(sessionFactoryKey))
			rst = sessionFactorys.get(sessionFactoryKey);
		log.debug("return sessionFactory is " + rst);
		return rst;
	}

	/*
	 * 重载sessionFactory方法
	 * 
	 * @see javax.naming.Referenceable#getReference()
	 */
	public Reference getReference() throws NamingException {
		return getSessionFactory().getReference();
	}

	public void close() throws HibernateException {
		getSessionFactory().close();
	}

	public boolean containsFetchProfileDefinition(String arg0) {
		return getSessionFactory().containsFetchProfileDefinition(arg0);
	}

	public void evict(Class arg0) throws HibernateException {
		getSessionFactory().evict(arg0);
	}

	public void evict(Class arg0, Serializable arg1) throws HibernateException {
		getSessionFactory().evict(arg0, arg1);
	}

	public void evictCollection(String arg0) throws HibernateException {
		getSessionFactory().evictCollection(arg0);
	}

	public void evictCollection(String arg0, Serializable arg1)
			throws HibernateException {
		getSessionFactory().evictCollection(arg0, arg1);
	}

	public void evictEntity(String arg0) throws HibernateException {
		getSessionFactory().evictEntity(arg0);
	}

	public void evictEntity(String arg0, Serializable arg1)
			throws HibernateException {
		getSessionFactory().evictEntity(arg0, arg1);
	}

	public void evictQueries() throws HibernateException {
		getSessionFactory().evictQueries();
	}

	public void evictQueries(String arg0) throws HibernateException {
		getSessionFactory().evictQueries(arg0);
	}

	public Map getAllClassMetadata() {
		return getSessionFactory().getAllClassMetadata();
	}

	public Map getAllCollectionMetadata() {
		return getSessionFactory().getAllCollectionMetadata();
	}

	public Cache getCache() {
		return getSessionFactory().getCache();
	}

	public ClassMetadata getClassMetadata(Class arg0) {
		return getSessionFactory().getClassMetadata(arg0);
	}

	public ClassMetadata getClassMetadata(String arg0) {
		return getSessionFactory().getClassMetadata(arg0);
	}

	public CollectionMetadata getCollectionMetadata(String arg0) {
		return getSessionFactory().getCollectionMetadata(arg0);
	}

	public Session getCurrentSession() throws HibernateException {
		return getSessionFactory().getCurrentSession();
	}

	public Set getDefinedFilterNames() {
		return getSessionFactory().getDefinedFilterNames();
	}

	public Statistics getStatistics() {
		return getSessionFactory().getStatistics();
	}

	public boolean isClosed() {
		return getSessionFactory().isClosed();
	}

	public Session openSession() throws HibernateException {
		return getSessionFactory().openSession();
	}

	public Session openSession(Interceptor interceptor)
			throws HibernateException {
		return getSessionFactory().openSession(interceptor);
	}

	public Session openSession(Connection connection) {
		return getSessionFactory().openSession(connection);
	}

	public Session openSession(Connection connection, Interceptor interceptor) {
		return getSessionFactory().openSession(connection, interceptor);
	}

	public StatelessSession openStatelessSession() {
		return getSessionFactory().openStatelessSession();
	}

	public StatelessSession openStatelessSession(Connection arg0) {
		return getSessionFactory().openStatelessSession(arg0);
	}

	public TypeHelper getTypeHelper() {
		return getSessionFactory().getTypeHelper();
	}

	public FilterDefinition getFilterDefinition(String arg0)
			throws HibernateException {
		return getSessionFactory().getFilterDefinition(arg0);
	}

}
