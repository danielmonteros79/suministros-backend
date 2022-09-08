package com.sa.su.service;

import java.util.List;

import com.sa.su.model.CentroCostos;

public interface CentroCostosService {

	List<CentroCostos> findAll();
	List<CentroCostos> findOfZona(Integer zona);
	List<CentroCostos> addAdress(List<CentroCostos> list);
	List<CentroCostos> getByUser(String ivUser);
	

}