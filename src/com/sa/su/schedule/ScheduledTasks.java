package com.sa.su.schedule;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.sa.su.model.CentroCostos;
import com.sa.su.model.Prioridad;
import com.sa.su.model.Zona;
import com.sa.su.service.CentroCostosService;
import com.sa.su.service.PedidoService;
import com.sa.su.service.PrioridadService;
import com.sa.su.service.ProcesarService;
import com.sa.su.service.UsuarioCentroCostoService;
import com.sa.su.service.ZonaService;

@Component
public class ScheduledTasks {
	Logger log = Logger.getLogger(ScheduledTasks.class);

	@Autowired
	UsuarioCentroCostoService service;
	
	@Autowired
	ProcesarService procesarService;
	
	@Autowired
	CentroCostosService centroCostosService;
	
	@Autowired
	ZonaService zonaService;
	
	@Autowired
	PedidoService pedidoService;
	
	@Autowired
	PrioridadService prioridadService;
	
//	@Scheduled(fixedDelay = 100000, initialDelay = 1000)
	@Scheduled(cron = "${batch.update.UsuarioCentroCostos.schedule}")
	public void generateFilePendienteContabilizacion() {
		log.info("*** Batch update Centro Costos por usuario ***");
		
		try {
			service.updateCCBatch();
		} catch (Exception e) {
			log.error("Error al ejecutar batch", e);
		}
		log.info("*** Batch update Usuario Centro Costos END ***");
	}
	
	@SuppressWarnings("all")
//	@Scheduled(fixedDelay = 100000, initialDelay = 1000)
	@Scheduled(cron = "${batch.pendiente.Procesar.schedule}")
	public void validacionPedidos() {
		log.info("*** Batch validacion Pedidos ***");
		try {
			List<Long> centroCostosList = new ArrayList<Long>();
			Calendar c = Calendar.getInstance();
			for (Zona zona : zonaService.findZonaOfSemana(c.get(Calendar.WEEK_OF_YEAR))) {
				for (CentroCostos cc : centroCostosService.findOfZona(zona.getId())) {
					centroCostosList.add(cc.getId());
				}
			}
			
			List<Long> prioridadList = new ArrayList<Long>();
			for (Prioridad prioridad : prioridadService.findAll()) {
				if (!prioridad.getDescripcion().equals("URGENTE"))
					prioridadList.add(prioridad.getId());
			}
			
			List<Long> estadoList = new ArrayList<Long>();
			estadoList.add(1L);
			estadoList.add(2L);
				
			if (centroCostosList != null && centroCostosList.size() != 0) {
				procesarService.procesar(null, null, null, centroCostosList, prioridadList, estadoList, false);
			}
			
		} catch (Exception e) {
			log.error("Error al ejecutar batch", e);
		}
		log.info("*** Batch validacion Pedidos END ***");
	}
	
	
}