package com.ncs.order.dao;

import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 *  Copyright 2007 - 2010 Skyway Software, Inc.
 *  from org.skyway.spring.util.dao.JpaDao
 */
public abstract class AbstractJpaDao<T extends Object> implements JpaDao<T> {

    private int defaultMaxResults = DEFAULT_MAX_RESULTS;


	/**
	 * The {@link EntityManager} which is used by all query manipulation and execution in this DAO.
	 *
	 * @return the {@link EntityManager}
	 */
	public abstract EntityManager getEntityManager();

	/*
	 * (non-Javadoc)
	 * @see org.skyway.spring.util.dao.JpaDao#getTypes()
	 */
	public abstract Set<Class<?>> getTypes();

	/*
	 * (non-Javadoc)
	 * @see org.skyway.spring.util.dao.JpaDao#store(java.lang.Object)
	 */
	@Transactional
	public T store(T toStore) {
		if (canBeMerged(toStore)) {
			return merge(toStore);
		}
		else {
			return persist(toStore);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.skyway.spring.util.dao.JpaDao#store(java.lang.Object)
	 */
	@Transactional
	public T merge(T toMerge) {
		return getEntityManager().merge(toMerge);
	}

	/*
	 * (non-Javadoc)
	 * @see org.skyway.spring.util.dao.JpaDao#store(java.lang.Object)
	 */
	@Transactional
	public T persist(T toPersist) {
		getEntityManager().persist(toPersist);
		return toPersist;
	}

	/*
	 * (non-Javadoc)
	 * @see org.skyway.spring.util.dao.JpaDao#remove(java.lang.Object)
	 */
	@Transactional
	public void remove(Object toRemove) {
		toRemove = getEntityManager().merge(toRemove);
		getEntityManager().remove(toRemove);
	}

	/*
	 * (non-Javadoc)
	 * @see org.skyway.spring.util.dao.JpaDao#flush()
	 */
	@Transactional
	public void flush() {
		getEntityManager().flush();
	}

	/*
	 * (non-Javadoc)
	 * @see org.skyway.spring.util.dao.JpaDao#refresh(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public void refresh(Object o) {
		try {
			if (o != null) {
				if (o instanceof java.util.Collection) {
					for (Iterator<?> i = ((Collection<?>) o).iterator(); i.hasNext();) {
						try {
							refresh(i.next());
						} catch (EntityNotFoundException x) {
							// This entity has been deleted - remove it from the collection
							i.remove();
						}
					}
				} else {
					if (getTypes().contains(o.getClass())) {
						getEntityManager().refresh(o);
					}
				}
			}
		} catch (EntityNotFoundException x) {
			// This entity has been deleted
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.skyway.spring.util.dao.JpaDao#setDefaultMaxResults(int)
	 */
	@Transactional
	public void setDefaultMaxResults(int defaultMaxResults) {
		this.defaultMaxResults = defaultMaxResults;
	}

	/*
	 * (non-Javadoc)
	 * @see org.skyway.spring.util.dao.JpaDao#getDefaultMaxResults()
	 */
	@Transactional
	public int getDefaultMaxResults() {
		return defaultMaxResults;
	}

	/*
	 * (non-Javadoc)
	 * @see org.skyway.spring.util.dao.JpaDao#executeQueryByNameSingleResult(java.lang.String)
	 */
	@Transactional
	@SuppressWarnings("unchecked")
	public T executeQueryByNameSingleResult(String queryName) {
		return (T) executeQueryByNameSingleResult(queryName, (Object[])null);
	}

	/*
	 * (non-Javadoc)
	 * @see org.skyway.spring.util.dao.JpaDao#executeQueryByNameSingleResult(java.lang.String, java.lang.Object[])
	 */
	@Transactional
	@SuppressWarnings("unchecked")
	public T executeQueryByNameSingleResult(String queryName, Object... parameters) {
		Query query = createNamedQuery(queryName, DEFAULT_FIRST_RESULT_INDEX, 1, parameters);
		return (T) query.getSingleResult();
	}

	/*
	 * (non-Javadoc)
	 * @see org.skyway.spring.util.dao.JpaDao#executeQueryByName(java.lang.String)
	 */
	@Transactional
	public List<T> executeQueryByName(String queryName) {
		return executeQueryByName(queryName, DEFAULT_FIRST_RESULT_INDEX, getDefaultMaxResults());
	}

	/*
	 * (non-Javadoc)
	 * @see org.skyway.spring.util.dao.JpaDao#executeQueryByName(java.lang.String, java.lang.Integer, java.lang.Integer)
	 */
	@Transactional
	public List<T> executeQueryByName(String queryName, Integer firstResult, Integer maxResults)  {
		return executeQueryByName(queryName, firstResult, maxResults, (Object[])null);
	}

	/*
	 * (non-Javadoc)
	 * @see org.skyway.spring.util.dao.JpaDao#executeQueryByName(java.lang.String, java.lang.Object[])
	 */
	@Transactional
	public List<T> executeQueryByName(String queryName, Object... parameters)  {
		return executeQueryByName(queryName, DEFAULT_FIRST_RESULT_INDEX, getDefaultMaxResults(), parameters);
	}

	/*
	 * (non-Javadoc)
	 * @see org.skyway.spring.util.dao.JpaDao#executeQueryByName(java.lang.String, java.lang.Integer, java.lang.Integer, java.lang.Object[])
	 */
	@Transactional
	@SuppressWarnings("unchecked")
	public List<T> executeQueryByName(String queryName, Integer firstResult, Integer maxResults, Object... parameters)  {
		Query query = createNamedQuery(queryName, firstResult, maxResults, parameters);
		return query.getResultList();
	}

	/*
	 * (non-Javadoc)
	 * @see org.skyway.spring.util.dao.JpaDao#createNamedQuery(java.lang.String, java.lang.Integer, java.lang.Integer)
	 */
	@Transactional
	public Query createNamedQuery(String queryName, Integer firstResult, Integer maxResults)  {
		return createNamedQuery(queryName, firstResult, maxResults, (Object[])null);
	}

	/*
	 * (non-Javadoc)
	 * @see org.skyway.spring.util.dao.JpaDao#createNamedQuery(java.lang.String, java.lang.Integer, java.lang.Integer, java.lang.Object[])
	 */
	@Transactional
	public Query createNamedQuery(String queryName, Integer firstResult, Integer maxResults, Object... parameters)  {
		Query query = getEntityManager().createNamedQuery(queryName);
		if (parameters != null) {
			for (int i = 0; i < parameters.length; i++) {
				query.setParameter(i + 1, parameters[i]);
			}
		}

		query.setFirstResult(firstResult == null || firstResult < 0 ? DEFAULT_FIRST_RESULT_INDEX : firstResult);
		if (maxResults != null && maxResults > 0)
			query.setMaxResults(maxResults);

		return query;
	}

	/*
	 * (non-Javadoc)
	 * @see org.skyway.spring.util.dao.JpaDao#executeQuery(java.lang.String, java.lang.Integer, java.lang.Integer, java.lang.Object[])
	 */
	@Transactional
	@SuppressWarnings("unchecked")
	public List<T> executeQuery(String queryString, Integer firstResult, Integer maxResults, Object... parameters)  {
		Query query = createQuery(queryString, firstResult, maxResults, parameters);
		return query.getResultList();
	}

	/*
	 * (non-Javadoc)
	 * @see org.skyway.spring.util.dao.JpaDao#executeQuery(java.lang.String, java.lang.Object[])
	 */
	@Transactional
	@SuppressWarnings("unchecked")
	public List<T> executeQuery(String queryString, Object... parameters)  {
		Query query = createQuery(queryString, DEFAULT_FIRST_RESULT_INDEX, getDefaultMaxResults(), parameters);
		return query.getResultList();
	}

	/*
	 * (non-Javadoc)
	 * @see org.skyway.spring.util.dao.JpaDao#executeQuerySingleResult(java.lang.String)
	 */
	@Transactional
	@SuppressWarnings("unchecked")
	public Object executeQuerySingleResult(String queryString) {
		return  executeQuerySingleResult(queryString, (Object[])null);
	}

	/*
	 * (non-Javadoc)
	 * @see org.skyway.spring.util.dao.JpaDao#executeQuerySingleResult(java.lang.String, java.lang.Object[])
	 */
	@Transactional
	@SuppressWarnings("unchecked")
	public T executeQuerySingleResult(String queryString, Object... parameters) {
		Query query = createQuerySingleResult(queryString, parameters);
		return (T) query.getSingleResult();
	}


	/*
	 * (non-Javadoc)
	 * @see org.skyway.spring.util.dao.JpaDao#createQuery(java.lang.String, java.lang.Integer, java.lang.Integer)
	 */
	@Transactional
	public Query createQuery (String queryString, Integer firstResult, Integer maxResults){
		return createQuery(queryString, firstResult, maxResults, (Object[])null);
	}

	/**
	 * Creates a query that will return a single result by default
	 * @param queryString
	 * @param parameters
	 * @return
	 */
	public Query createQuerySingleResult (String queryString, Object... parameters) {
		return createQuery(queryString, DEFAULT_FIRST_RESULT_INDEX, 1, parameters);
	}

	/*
	 * (non-Javadoc)
	 * @see org.skyway.spring.util.dao.JpaDao#createQuery(java.lang.String, java.lang.Integer, java.lang.Integer, java.lang.Object[])
	 */
	@Transactional
	public Query createQuery (String queryString, Integer firstResult, Integer maxResults, Object... parameters){
		Query query = getEntityManager().createQuery(queryString);
		if (parameters != null) {
			for (int i = 0; i < parameters.length; i++) {
				query.setParameter(i + 1, parameters[i]);
			}
		}

		query.setFirstResult(firstResult == null || firstResult < 0 ? DEFAULT_FIRST_RESULT_INDEX : firstResult);
		if (maxResults != null && maxResults > 0)
			query.setMaxResults(maxResults);

		return query;
	}

	/**
	 * Each DAO can decide whether the object passed can be merged using the .merge() method
	 * Generally speaking, Objects whos primary keys are auto generated must be passed to the persist method
	 * in order to have their primary keys fields filled in.
	 *
	 * @param o
	 * @return
	 */
	public abstract boolean canBeMerged (T o);

}
