package com.sa.su.service;

import java.util.List;

import com.sa.su.model.TipoMaterial;

public interface TipoMaterialService {
	List<TipoMaterial> listAll();
	List<TipoMaterial> findFilteredList(List<Long> id);
	Long updateTipoMaterial(TipoMaterial tipoMaterial, String userMod);
	void saveTipoMaterial(TipoMaterial tipoMaterial, String usrSv);
	void deleteTipoMaterial(TipoMaterial tipoMaterial, String usrDlt);
}
