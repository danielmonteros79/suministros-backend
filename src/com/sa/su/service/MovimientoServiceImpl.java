package com.sa.su.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sa.su.dao.MovimientoDao;
import com.sa.su.model.Material;
import com.sa.su.model.Pedido;
import com.sa.su.model.Usuario;

@Service("movimientoService")
@Transactional
public class MovimientoServiceImpl implements MovimientoService {

	@Autowired
	private MovimientoDao dao;

	@Override
	public void saveMovimiento(Pedido pedido, String userAlta, String tipoMovimiento) {

		dao.save(pedido, userAlta, tipoMovimiento);

	}

	@Override
	public void saveMovimiento(Material newEntity, Usuario usuario, String tipoMovimiento) {

		dao.save(newEntity, usuario, tipoMovimiento);

	}

}