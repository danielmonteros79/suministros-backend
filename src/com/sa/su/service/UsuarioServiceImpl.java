package com.sa.su.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sa.su.dao.CentroCostosDao;
import com.sa.su.dao.UsuarioDao;
import com.sa.su.model.Usuario;

@Service("usuarioService")
@Transactional
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired private UsuarioDao dao;
	@Autowired private CentroCostosDao centroCostosDao;

	@Override
	public Usuario update(Usuario usuario) throws Exception {
		Usuario entity = dao.find(usuario.getLegajo());
		if (entity == null)
			entity = new Usuario();
		
		this.copyFields(entity, usuario);

		return dao.update(entity);
	}

	@Override
	public Usuario find(String legajo) {
		return dao.find(legajo);
	}
	
	private void copyFields(Usuario entity, Usuario usuario) {
		entity.setLegajo(usuario.getLegajo());
		entity.setCodSector(usuario.getCodSector());
		entity.setDescSector(usuario.getDescSector());
		entity.setCodArea(usuario.getCodArea());
		entity.setDescArea(usuario.getDescArea());
		entity.setCodPuesto(usuario.getCodPuesto());
		entity.setDescPuesto(usuario.getDescPuesto());
		entity.setEmail(usuario.getEmail());
		entity.setApellido(usuario.getApellido());
		entity.setNombre(usuario.getNombre());
		entity.setFunciones(usuario.getFunciones());
		entity.setCentroCostos(centroCostosDao.find(usuario.getCentroCostos().getId()));
	}
}