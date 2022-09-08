package com.sa.su.dao;

import java.io.Serializable;

public interface GenericDao<PK extends Serializable, T> {

	T find(PK key);

	T create(T entity);
	
	T update(T entity);

	void delete(T entity);
	
}
