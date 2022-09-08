package com.sa.su.service;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sa.su.dao.EstadoDao;
import com.sa.su.dao.MaterialDao;
import com.sa.su.dao.PedidoDao;
import com.sa.su.dao.ProcesarDao;
import com.sa.su.dao.UsuarioCentroCostoDao;
import com.sa.su.dao.ValidacionDao;
import com.sa.su.model.CantidadMaterial;
import com.sa.su.model.Estado;
import com.sa.su.model.FileNameAndSecuence;
import com.sa.su.model.Material;
import com.sa.su.model.Pedido;
import com.sa.su.model.Rubro;
import com.sa.su.model.UsuarioCentroCosto;
import com.sa.su.model.Validacion;

@Service("procesarService")
@Transactional
@Component
public class ProcesarServiceImpl implements ProcesarService {
	@Autowired
	private ProcesarDao dao;
	
	@Autowired
	private PedidoDao pdao;
	
	@Autowired
	private MaterialDao mdao;
	
	@Autowired
	private ValidacionDao vdao;
	
	@Autowired
	private EstadoDao edao;
	
	@Autowired
	UsuarioCentroCostoDao usuarioCentroCostoDao;
	
	@Autowired
	private MovimientoService moservice;

	private static List<Estado> estadoList = null;
	private static List<Validacion> validacionList = null;

	@Override
	public List<Pedido> findAProcesar(String legajo) {
		List<Long> centrosCosto = new ArrayList<Long>();
		List<UsuarioCentroCosto> usuarioCentrosCosto = usuarioCentroCostoDao.findFilteredList(legajo, null);
		for (UsuarioCentroCosto usuarioCentroCosto : usuarioCentrosCosto) {
			centrosCosto.add(usuarioCentroCosto.getCentroCostos().getId());
		}
		return dao.findAProcesar(centrosCosto);
	}
	public List<Pedido> procesar(List<Long> material, Date feDesde, Date feHasta,List<Long> ccostos,
			List<Long> prioridad, List<Long> estado, Boolean validados) {
		loadEstadoValidaciones();
		return validarSegunStock(material, feDesde, feHasta, ccostos, prioridad, estado, validados);
	}

	@Override
	public List<Pedido> procesarArchivo(List<Long> ccostos, Rubro rubro){
			return pdao.processFile(ccostos, rubro);
	}
	@Override
	public List<Pedido> procesadosNum(Long num_trat){
		return pdao.procesadosNum(num_trat);
	}
	@Override
	public List<FileNameAndSecuence> getFileNameAndSecuence(List<Long> ccostos, Long rubro){
		return pdao.getFileNameAndSecuence(ccostos, rubro);
	}
	private List<Pedido> validarSegunStock(List<Long> material, Date feDesde, Date feHasta,
			List<Long> ccostos, List<Long> prioridad, List<Long> estado, Boolean validados) {
		List<Pedido> pedidoList = new ArrayList<Pedido>();
		// mapa contiene (material.id, cantidadTotalPedida)
		Map<Long, Long> cantPedMap = pendingToMap(pdao.sumAmountByPendingMaterial());
		if(!cantPedMap.isEmpty()){
			Map<Long, Long> cantAproMap = validateMaxAmount(cantPedMap, mdao.findAll());
			pedidoList = pdao.findFilteredListPendientes(material, ccostos, prioridad, feDesde, feHasta, estado, validados);
			setCantAprobada(pedidoList, cantAproMap);
		}else{
			System.out.println("IGNORE ALL");
		}
		return pedidoList;

	};

	private void setCantAprobada(List<Pedido> pedidoList, Map<Long, Long> cantAproMap) {
		for (Pedido pedido : pedidoList) {
			if (pedido.getCantidad() > cantAproMap.get(pedido.getMaterial().getId())) {
				pedido.setCant_aprobada(cantAproMap.get(pedido.getMaterial().getId()));
				// VALIDACION DESCRIPCION = STOCK
				pedido.setValidacion(validacionList.get(0));
				pedido.setEstado(estadoList.get(1));
			} else {
				pedido.setCant_aprobada(pedido.getCantidad());
				// VALIDACION DESCRIPCION = OK
				pedido.setValidacion(validacionList.get(3)); 
				pedido.setEstado(estadoList.get(2));//APRUEBO DE UNA SI ESTA OK
				//ACTUALIZO STOCK!!
				mdao.updateStock(pedido.getMaterial(), -1 *pedido.getCant_aprobada(), null);
				//guardo movimientos de stock en tabla de movimientos (SUM_MOVIMIENTOS)
				moservice.saveMovimiento(pedido, "BATCH", "PEDIDO"); //VIENE DEL PROCESO AUTOMATICO
			}
		}

	}

	private Map<Long, Long> pendingToMap(List<CantidadMaterial> sumAmountByPendingMaterial) {
		Map<Long, Long> cantPedMap = new HashMap<Long, Long>();

		for (CantidadMaterial cm : sumAmountByPendingMaterial) {
			cantPedMap.put(cm.getMaterial(), cm.getCantidad());
		}
		return cantPedMap;
	}

	private Map<Long, Long> validateMaxAmount(Map<Long, Long> cantPedMap, List<Material> matlist) {
		Map<Long, Long> cantAproMap = new HashMap<Long, Long>();

		for (Material m : matlist) {
			if (cantPedMap.containsKey(m.getId())) {
				Long cantidadPedida = cantPedMap.get(m.getId());
				boolean aproved = false;
				System.out.println("id a aprobar"+m.getId());
				for (int i = 0; i < 4 && !aproved; i++) {
					if (m.getCantidad_stock() >= cantidadPedida) {
						aproved = true;
					} else {
						cantidadPedida = cantidadPedida / 2;
					}
				}
				if (aproved) {
					System.out.println("aprobado"+cantidadPedida);
					cantAproMap.put(m.getId(), cantidadPedida);
				} else {
					cantAproMap.put(m.getId(), 0L);
					System.out.println("aprobado"+0L);
				}
			}
		}
		return cantAproMap;
	}

	private void loadEstadoValidaciones() {
		if (validacionList == null)
			validacionList = vdao.findAll();
		if (estadoList == null)
			estadoList = edao.findAll();
	}
}
