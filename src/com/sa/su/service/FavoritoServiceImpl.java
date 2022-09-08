package com.sa.su.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sa.su.dao.FavoritoDao;
import com.sa.su.dao.GenericDaoImpl;
import com.sa.su.model.Favorito;
import com.sa.su.model.Material;

@Service("favoritoService")
@Transactional
public class FavoritoServiceImpl extends GenericDaoImpl<String, Favorito> implements FavoritoService {

	@Autowired
	private FavoritoDao dao;

	@Override
	public List<Favorito> findAll(String legajo) {
		return dao.findAll(legajo);
	}
	
	public void add(String materialId, String legajo) {
		dao.add(setData(materialId, legajo));		
	}
	
	public void delete(String materialId, String legajo) {
		Favorito delEntity = (Favorito) getSession().get(Favorito.class, setData(materialId, legajo));
		dao.delete(delEntity);		
	}
	
	private Favorito setData(String materialId, String legajo) {
		Favorito favorito = new Favorito();
		Material material = new Material();
		
		material.setId(Long.parseLong(materialId));
		favorito.setLegajo(legajo);
		favorito.setMaterial(material);
		return favorito;
	}
	
}