package com.sa.su.dao;

import java.util.List;

import com.sa.su.model.TipoAlerta;

public interface TipoAlertaDao extends GenericDao<Long, TipoAlerta>{

	List<TipoAlerta> findAll();
	
}