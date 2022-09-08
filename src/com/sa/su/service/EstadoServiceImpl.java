package com.sa.su.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sa.su.dao.EstadoDao;
import com.sa.su.model.Estado;

@Service("estadoService")
@Transactional
public class EstadoServiceImpl implements EstadoService {

	@Autowired
	private EstadoDao dao;

	@Override
	public List<Estado> findAll() {
		return dao.findAll();
	}

	@Override
	public List<Estado> findProcessStatus() {
		// TODO Auto-generated method stub

		List<Long> estadoList = new ArrayList<Long>();
		estadoList.add(4L); // RECHAZADO
		estadoList.add(3L); // APROBADO
		return dao.findFiltered(estadoList);
	}

}