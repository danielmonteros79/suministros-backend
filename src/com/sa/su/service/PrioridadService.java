package com.sa.su.service;

import java.util.List;

import com.sa.su.model.Prioridad;

public interface PrioridadService {
	List<Prioridad> findAll();
	List<Prioridad> findFilteredList(List<Long> id);
	Long updatePrioridad(Prioridad prioridad, String userMod);
	void savePrioridad(Prioridad prioridad, String usrSv);
	void deletePrioridad(Prioridad prioridad, String usrDlt);
}