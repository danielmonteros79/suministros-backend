package com.sa.su.dao;

import java.util.List;

import com.sa.su.model.CentroCostos;

public interface CentroCostosDao extends GenericDao<Long, CentroCostos>{

	List<CentroCostos> findAll();
	List<CentroCostos> findOfZona(Integer zona);
	List<CentroCostos> getByUser(String ivUser);


}
