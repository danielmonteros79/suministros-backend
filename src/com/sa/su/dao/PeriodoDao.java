package com.sa.su.dao;

import java.util.List;

import com.sa.su.model.Periodo;

public interface PeriodoDao  extends GenericDao<Long, Periodo>{

	List<Periodo> findAll();
}
