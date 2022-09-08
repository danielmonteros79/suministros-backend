package com.sa.su.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sa.su.dao.FuncionDao;
import com.sa.su.model.Funcion;

@Service("funcionService")
@Transactional
public class FuncionServiceImpl implements FuncionService {

	@Autowired
	private FuncionDao dao;

	@Override
	public List<Funcion> listAll(){
		return dao.findAll();
	}
}
