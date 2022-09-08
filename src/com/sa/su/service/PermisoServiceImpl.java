package com.sa.su.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sa.su.dao.PermisoDao;
import com.sa.su.model.Permiso;

@Service("permisoService")
@Transactional
public class PermisoServiceImpl implements PermisoService {
	Logger log = Logger.getLogger(PermisoServiceImpl.class);
	
	@Autowired
	PermisoDao dao;

	@Override
	public List<Permiso> findFilteredList(Integer puesto, Integer area, List<Integer> funciones) {
		return dao.findFilteredList(puesto, area, funciones);
	}

	@Override
	public void create(Permiso permiso, String usuario) throws Exception {
		dao.create(permiso, usuario);
	}

	@Override
	public void delete(Permiso permiso, String usuario) {
		dao.delete(permiso, usuario);
	}

	@Override
	public boolean usuarioTieneFuncion(String legajo, Integer funcionId) {
		return dao.usuarioTieneFuncion(legajo, funcionId);
	}
}
