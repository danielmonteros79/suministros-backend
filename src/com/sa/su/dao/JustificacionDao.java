package com.sa.su.dao;

import java.util.List;

import com.sa.su.model.Justificacion;

public interface JustificacionDao extends GenericDao<Long, Justificacion>{
	List<Justificacion> findAll();
	List<Justificacion> findFilteredList(List<Long> id);
	void save(Justificacion justificacion, String usrSv);
	public Long update(Justificacion justificacion, String userMod);
	void deleteJustificacion(Justificacion justificacion, String usrDlt);
}
