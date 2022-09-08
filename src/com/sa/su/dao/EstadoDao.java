package com.sa.su.dao;

import java.util.List;

import com.sa.su.model.Estado;

public interface EstadoDao {

	List<Estado> findAll();

	List<Estado> findFiltered(List<Long> estadoList);


}
