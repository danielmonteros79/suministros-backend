package com.sa.su.service;

import java.util.List;

import com.sa.su.model.Justificacion;

public interface JustificacionService {
	List<Justificacion> listAll();
	List<Justificacion> findFilteredList(List<Long> id);
	Long updateJustificacion(Justificacion justificacion, String userMod);
	void saveJustificacion(Justificacion justificacion, String usrSv);
	void deleteJustificacion(Justificacion justificacion, String usrDlt);
}
