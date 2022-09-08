package com.sa.su.dao;

import java.util.List;

import com.sa.su.model.Funcion;

public interface FuncionDao extends GenericDao<Long, Funcion> {
	List<Funcion> findAll();
}
