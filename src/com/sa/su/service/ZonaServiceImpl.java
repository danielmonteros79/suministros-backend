package com.sa.su.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sa.su.dao.ZonaDao;
import com.sa.su.dao.ZonaSemanaDao;
import com.sa.su.model.Zona;

@Service("zonaService")
@Transactional
public class ZonaServiceImpl implements ZonaService {
	@Autowired
	private ZonaDao dao;
	@Autowired
	private ZonaSemanaDao zonaSemanaDao;
	@Override
	public List<Zona> listAll(){
		return dao.findAll();
	}
	
	@Override
	public List<Zona> findZonaOfSemana(Integer semana) {
		return zonaSemanaDao.findZonaOfSemana(semana);
	}
}
