package com.sa.su.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sa.su.dao.ObservacionDao;
import com.sa.su.model.Observacion;

@Service("observacionService")
@Transactional
public class ObservacionServiceImpl implements ObservacionService {

	@Autowired
	ObservacionDao dao;
	@Override
	public void saveObservacion(Observacion observacion){
		dao.save(observacion);
	}
}
