package com.sa.su.service;

import java.util.List;

import com.sa.su.model.Alerta;
import com.sa.su.model.Pedido;

public interface AlertaService {

	List<Alerta> findAll(List<Long> material, List<String> tipoAlertaPeriodo, List<Long> centroCostos);

	Alerta create(Alerta alerta, String usuarioAlta) throws Exception;
	
	Alerta update(Alerta alerta, String usuarioMod) throws Exception;

	void delete(long id, String usuarioBaja) throws Exception;
	
	boolean validarPedidoAlerta(Pedido pedido) throws Exception;
}
