package com.sa.su.service;

import java.util.List;

import com.sa.su.model.Favorito;

public interface FavoritoService {
	List<Favorito> findAll(String legajo);
	void add(String materialId, String legajo);
	void delete(String materialId, String legajo);
}