package com.sa.su.dao;

import java.util.List;

import com.sa.su.model.TipoMaterial;

public interface TipoMaterialDao extends GenericDao<Long, TipoMaterial> {
	List<TipoMaterial> findAll();
	List<TipoMaterial> findFilteredList(List<Long> id);
	void save(TipoMaterial tipoMaterial, String usrSv);
	public Long update(TipoMaterial tipoMaterial, String userMod);
	void deleteTipoMaterial(TipoMaterial tipoMaterial, String usrDlt);
}
