package com.ncs.order.dao.impl;

import com.ncs.order.dao.BaseDao;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Dao实现类 - Dao实现类基类
 */

@Repository
public class BaseDaoImpl<T, PK extends Serializable> implements BaseDao<T, PK> {

	private Class<T> entityClass;

    @PersistenceContext
    EntityManager entityManager;

	public BaseDaoImpl() {
		this.entityClass = null;
        Class c = getClass();
        Type type = c.getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Type[] parameterizedType = ((ParameterizedType) type).getActualTypeArguments();
            this.entityClass = (Class<T>) parameterizedType[0];
        }
	}

	public T get(PK id) {
		Assert.notNull(id, "id is required");
        return entityManager.find(entityClass,id);
	}

    public Set<T> get(PK[] ids) {
		Assert.notEmpty(ids, "ids must not be empty");
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(entityClass);
        Root<T> root = cq.from(entityClass);
//        root.fetch(entityClass.getField("id"), JoinType.LEFT); //will not create the extra queries
        ArrayList<Predicate> predicateList = new ArrayList<Predicate>();
//        Path<PK> id = root.get("id");
//        Path<PK> id = root.get("imageId");
        Path<PK> id = root.get("employeeId");
        cq.select(root);
        CriteriaBuilder.In<PK> in = cb.in(id);
        for(PK pk : ids){
            in = in.value(pk);
        }
        predicateList.add(in);
        Predicate[] predicates = new Predicate[predicateList.size()];
        cq.where(predicateList.toArray(predicates));
        return new LinkedHashSet<T>(entityManager.createQuery(cq).getResultList());
	}

	public T get(String propertyName, Object value) {
		Assert.hasText(propertyName, "propertyName must not be empty");
		Assert.notNull(value, "value is required");
		String ql = "select model from " + entityClass.getName() + " as model where model." + propertyName + " = :param";
		return (T) entityManager.createQuery(ql).setParameter("param", value).getSingleResult();
	}
	
	public Set<T> getList(String propertyName, Object value) {
		Assert.hasText(propertyName, "propertyName must not be empty");
		Assert.notNull(value, "value is required");
		String ql = "select model from " + entityClass.getName() + " as model where model." + propertyName + " = :param";
        return new LinkedHashSet<T>(entityManager.createQuery(ql).setParameter("param", value).getResultList());
	}

	public Set<T> getAll() {
        String ql = "select model from " + entityClass.getName() + " as model";
		return new LinkedHashSet<T>(entityManager.createQuery(ql).getResultList());
	}
	
	public Long getTotalCount() {
        String ql = "select count(*) from "+ entityClass.getName();
		return (Long) entityManager.createQuery(ql).getSingleResult();
	}

	public boolean isUnique(String propertyName, Object oldValue, Object newValue) {
		Assert.hasText(propertyName, "propertyName must not be empty");
		Assert.notNull(newValue, "newValue is required");
		if (newValue == oldValue || newValue.equals(oldValue)) {
			return true;
		}
		if (newValue instanceof String) {
			if (oldValue != null && StringUtils.equalsIgnoreCase((String) oldValue, (String) newValue)) {
				return true;
			}
		}
		T object = get(propertyName, newValue);
		return (object == null);
	}
	
	public boolean isExist(String propertyName, Object value) {
		Assert.hasText(propertyName, "propertyName must not be empty");
		Assert.notNull(value, "value is required");
		T object = get(propertyName, value);
		return (object != null);
	}

	public T save(T entity) {
		Assert.notNull(entity, "entity is required");
        entityManager.persist(entity);
        return entity;
	}

	public void update(T entity) {
		Assert.notNull(entity, "entity is required");
        entityManager.persist(entity);
	}

	public void delete(T entity) {
		Assert.notNull(entity, "entity is required");
        entityManager.remove(entity);
	}

	public void delete(PK id) {
		Assert.notNull(id, "id is required");
        T entity = get(id);
        entityManager.remove(entity);
	}

	public void delete(PK[] ids) {
		Assert.notEmpty(ids, "ids must not be empty");
		for (PK id : ids) {
			T entity = get(id);
            entityManager.remove(entity);
		}
	}

	public void flush() {
        entityManager.flush();
	}

	public void clear() {
        entityManager.clear();
	}

}