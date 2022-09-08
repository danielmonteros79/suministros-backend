package com.sa.su.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sa.su.dao.JustificacionDao;
import com.sa.su.model.Justificacion;

@Service("justificacionesService")
@Transactional
public class JustificacionServiceImpl implements JustificacionService {
	@Autowired
	private JustificacionDao dao;
	@Override
	public List<Justificacion> listAll(){
		return dao.findAll();
	}
	@Override
	public List<Justificacion> findFilteredList(List<Long> id){
		return dao.findFilteredList(id);
	}
	@Override
	public void saveJustificacion(Justificacion justificacion, String usrSv){
		dao.save(justificacion, usrSv);
	}
	@Override
	public void deleteJustificacion(Justificacion justificacion, String usrDlt){
		dao.deleteJustificacion(justificacion, usrDlt);
	}
	@Override
	public Long updateJustificacion(Justificacion justificacion, String userMod) {
		return dao.update(justificacion, userMod);
	}
}
