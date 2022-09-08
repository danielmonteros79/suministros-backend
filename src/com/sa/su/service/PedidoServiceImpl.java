package com.sa.su.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sa.su.dao.PedidoDao;
import com.sa.su.model.Pedido;

@Service("pedidoService")
@Transactional
@SuppressWarnings("all")
public class PedidoServiceImpl implements PedidoService {

	@Autowired
	private PedidoDao dao;

	@Override
	public List<Pedido> findFilteredList(List<Long> material, List<Long> ccostos, List<Long> prioridad, Date feDesde,
			Date feHasta, List<Long> estado, String screen) {
		
		if (screen.equals("PEND")) return dao.findFilteredListPendientes(material, ccostos, prioridad, feDesde, feHasta, estado, null);
		else if (screen.equals("RES")) return dao.findFilteredListResueltos(material, ccostos, prioridad, feDesde, feHasta, estado);
		else{ 
				List<Pedido> tratados = dao.findFilteredListTratados(material, ccostos, prioridad, feDesde, feHasta, estado);
					for (Pedido tratado : tratados){
						tratado.setCantidad_recepcion(tratado.getCant_aprobada());
					}
			return tratados;
		}
	}


	@Override
	public Pedido getById(Long id) {
			return dao.getById(id);

	}

	@Override
	public List<Long> saveListPedidos(List<Pedido> list, String usuarioAlta, Long ccostos) throws Exception {
		List<Long> ids = new ArrayList<Long>();	
		for (Pedido pedido : list) {
			ids.add(dao.save(pedido, usuarioAlta,ccostos));
		}
		return ids;
	}

	@Override
	public Long savePedido(Pedido pedido, String usuario) throws Exception {
		// TODO Auto-generated method stub
		return dao.update(pedido, usuario);
	}

	@Override
	public Long deletePedido(Long pedido) {
		// TODO Auto-generated method stub
		return dao.delete(pedido);
	}


	@Override
	public void recibir(List<Pedido> list, String usuario) {
		for (Pedido pedido : list) {
			dao.recibir(pedido, usuario);	
		}		
	}
	@Override
	public void saveListPedidos(List<Pedido> list, String usuario) throws Exception {
		// TODO Auto-generated method stub
		for (Pedido pedido : list) {
			if (pedido.getFe_tratamiento() == null && pedido.getValidacion().getId() != 4)
				dao.update(pedido, usuario);	
		}		
	}

}