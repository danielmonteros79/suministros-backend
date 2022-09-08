package com.sa.su.service;

import java.util.List;

import com.sa.su.model.Zona;

public interface ZonaService {
	List<Zona> listAll();
	List<Zona> findZonaOfSemana(Integer semana);
}
