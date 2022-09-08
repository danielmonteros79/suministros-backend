package com.sa.su.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Property;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class GenericDaoImpl<PK extends Serializable, T> {

	private final Class<T> persistentClass;

	@SuppressWarnings("unchecked")
	public GenericDaoImpl() {
		this.persistentClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass())
				.getActualTypeArguments()[1];
	}

	@Autowired
	private SessionFactory sessionFactory;

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@SuppressWarnings("unchecked")
	public T find(PK key) {
		return (T) getSession().get(persistentClass, key);
	}

	public T create(T entity) {
		getSession().persist(entity);
		getSession().flush();
		return entity;
	}

	public T update(T entity) {
		getSession().saveOrUpdate(entity);
		return entity;
	}

	public void delete(T entity) {
		getSession().delete(entity);
	}

	public Criteria createEntityCriteria() {
		return getSession().createCriteria(persistentClass);
	}

	public void generateSortOrder(String sortField, String sortOrder, Criteria criteria) {
		if (null != sortOrder && null != sortField && sortOrder.equalsIgnoreCase("-1"))
			criteria.addOrder(Property.forName(sortField).desc());
		else if (null != sortOrder && null != sortField && sortOrder.equalsIgnoreCase("1"))
			criteria.addOrder(Property.forName(sortField).asc());
	}
}
