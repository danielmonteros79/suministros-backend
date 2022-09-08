package com.sa.su.dao;

import java.util.List;

import com.sa.su.model.Zona;

public interface ZonaSemanaDao {
	List<Zona> findZonaOfSemana(Integer semana);
}
