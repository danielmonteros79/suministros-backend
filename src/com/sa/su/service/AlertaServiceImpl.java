package com.sa.su.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sa.su.dao.AlertaDao;
import com.sa.su.dao.PedidoDao;
import com.sa.su.model.Alerta;
import com.sa.su.model.Pedido;

@Service("alertaService")
@Transactional
public class AlertaServiceImpl implements AlertaService {

	@Autowired
	private AlertaDao dao;
	
	@Autowired
	private PedidoDao pdao;
	
	@Override
	public List<Alerta> findAll(List<Long> material, List<String> tipoAlertaPeriodo, List<Long> centroCostos) {
		return dao.findAll(material, tipoAlertaPeriodo, centroCostos);
	}
	
	public Alerta create(Alerta alerta, String usuarioAlta) throws Exception {
		this.validateAlerta(alerta, true);

		Alerta entity = new Alerta();
		entity.setFechaAlta(new Date());
		entity.setUsuarioAlta(usuarioAlta);
		this.copyFields(entity, alerta);

		return dao.create(entity);
	}

	@Override
	public Alerta update(Alerta alerta, String usuarioMod) throws Exception {
		this.validateAlerta(alerta, false);
		
		Alerta entity = dao.find(alerta.getId());
		entity.setFechaMod(new Date());
		entity.setUsuarioMod(usuarioMod);
		this.copyFields(entity, alerta);

		return dao.update(entity);
	}

	@Override
	public void delete(long id, String usuarioBaja) throws Exception {
		Alerta entity = dao.find(id);

		entity.setFechaBaja(new Date());
		entity.setUsuarioBaja(usuarioBaja);

		dao.update(entity);
	}
	
	@Override
	public boolean validarPedidoAlerta(Pedido pedido) throws Exception {
		boolean res = false;
		Long matId = pedido.getMaterial().getId();
		Long ccId = pedido.getCcostos().getId();
		for (Alerta alerta : dao.getAlertasMaterialCentroCostos(matId, ccId)) {
			Long cantTotal = pdao.getSumCantidadMaterialPedidosRecibidosPeriodoByCCMinusPedido(matId, ccId, alerta.getPeriodo(), pedido.getId()) + pedido.getCantidad();
			res|= (cantTotal > alerta.getValor().longValue());
		}
		return res;
	}

	private void copyFields(Alerta entity, Alerta alerta) {
		entity.setMaterial(alerta.getMaterial());
		entity.setCentroCostos(alerta.getCentroCostos());
		entity.setTipoAlerta(alerta.getTipoAlerta());
		entity.setPeriodo(alerta.getPeriodo());
		entity.setValor(alerta.getValor());
	}
	
	private void validateAlerta(Alerta alerta, boolean isCreate) throws Exception {
		List<Long> materialId = new ArrayList<Long>();
		List<Long> centroCostosId = new ArrayList<Long>();
		materialId.add(alerta.getMaterial().getId());
		centroCostosId.add(alerta.getCentroCostos().getId());
		List<Alerta> alertas = dao.findAll(materialId,null, centroCostosId);
		
		for (Alerta a : alertas) {
			if (alerta.getTipoAlerta().equals(a.getTipoAlerta())) {
				if (isCreate && alerta.getPeriodo().equals(a.getPeriodo()))
					throw new SQLException("DUPLICATED_ID");
				
				if (alerta.getPeriodo().getId() > a.getPeriodo().getId() && alerta.getValor().compareTo(a.getValor()) < 0)
					throw new SQLException("PERIOD_MIN");
				if (alerta.getPeriodo().getId() < a.getPeriodo().getId() && alerta.getValor().compareTo(a.getValor()) > 0)
					throw new SQLException("PERIOD_MAX");
			}
		}
	}
}
