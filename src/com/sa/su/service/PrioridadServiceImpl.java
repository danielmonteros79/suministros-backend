package com.sa.su.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sa.su.dao.PrioridadDao;
import com.sa.su.model.Prioridad;

@Service("prioridadService")
@Transactional
public class PrioridadServiceImpl implements PrioridadService {

	@Autowired
	private PrioridadDao dao;

	@Override
	public List<Prioridad> findAll() {
		return dao.findAll();
	}
	@Override
	public List<Prioridad> findFilteredList(List<Long> id){
		return dao.findFilteredList(id);
	}
	@Override
	public void savePrioridad(Prioridad prioridad, String usrSv){
		dao.save(prioridad, usrSv);
	}
	@Override
	public void deletePrioridad(Prioridad prioridad, String usrDlt){
		dao.deletePrioridad(prioridad, usrDlt);
	}
	@Override
	public Long updatePrioridad(Prioridad prioridad, String userMod) {
		return dao.update(prioridad, userMod);
	}
}