package com.sa.su.dao;

import java.util.List;

import com.sa.su.model.Prioridad;

public interface PrioridadDao {
	List<Prioridad> findAll();
	List<Prioridad> findFilteredList(List<Long> id);
	void save(Prioridad prioridad, String usrSv);
	public Long update(Prioridad prioridad, String userMod);
	void deletePrioridad(Prioridad prioridad, String usrDlt);
}
