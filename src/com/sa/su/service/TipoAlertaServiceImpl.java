package com.sa.su.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sa.su.dao.TipoAlertaDao;
import com.sa.su.model.TipoAlerta;

@Service("tipoAlertaService")
@Transactional
public class TipoAlertaServiceImpl implements TipoAlertaService {
	
	@Autowired
    private TipoAlertaDao dao;

	public List<TipoAlerta> findAll() {
		return dao.findAll();
	}

}