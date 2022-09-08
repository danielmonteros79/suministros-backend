package com.sa.su.service;

import java.util.Date;
import java.util.List;

import com.sa.su.model.Pedido;

public interface PedidoService {

	List<Pedido> findFilteredList(List<Long> material, List<Long> ccostos, List<Long> prioridad, Date feDesde, Date feHasta, List<Long> estado, String screen);
	Pedido getById(Long id);
	List<Long> saveListPedidos(List<Pedido> list, String usuarioAlta, Long cc) throws Exception;
	Long savePedido(Pedido pedido, String usuarioAlta) throws Exception;
	Long deletePedido(Long pedido);
	void recibir(List<Pedido> list, String usuario);
	void saveListPedidos(List<Pedido> list, String usuario) throws Exception;

}