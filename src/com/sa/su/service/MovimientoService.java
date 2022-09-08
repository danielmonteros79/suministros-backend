package com.sa.su.service;


import com.sa.su.model.Material;
import com.sa.su.model.Pedido;
import com.sa.su.model.Usuario;

public interface MovimientoService {


	void saveMovimiento(Pedido pedido, String userAlta, String tipoMovimiento);

	

	void saveMovimiento(Material newEntity, Usuario usuario, String tipoMovimiento);


}