package com.sa.su.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sa.su.dao.TipoMaterialDao;
import com.sa.su.model.TipoMaterial;

@Service("tipoActivoService")
@Transactional
public class TipoMaterialServiceImpl implements TipoMaterialService {

	@Autowired
	private TipoMaterialDao dao;
	@Override
	public List<TipoMaterial> listAll(){
		return dao.findAll();
	}
	@Override
	public List<TipoMaterial> findFilteredList(List<Long> id){
		return dao.findFilteredList(id);
	}
	@Override
	public void saveTipoMaterial(TipoMaterial tipoMaterial, String usrSv){
		dao.save(tipoMaterial, usrSv);
	}
	@Override
	public void deleteTipoMaterial(TipoMaterial tipoMaterial, String usrDlt){
		dao.deleteTipoMaterial(tipoMaterial, usrDlt);
	}
	@Override
	public Long updateTipoMaterial(TipoMaterial tipoMaterial, String userMod) {
		return dao.update(tipoMaterial, userMod);
	}
}
