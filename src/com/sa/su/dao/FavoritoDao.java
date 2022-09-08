package com.sa.su.dao;

import java.util.List;

import com.sa.su.model.Favorito;

public interface FavoritoDao {

	List<Favorito> findAll(String legajo);
	void add(Favorito favorito);
	void delete(Favorito favorito);
}
