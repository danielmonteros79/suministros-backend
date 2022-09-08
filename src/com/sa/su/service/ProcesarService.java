package com.sa.su.service;

import java.util.Date;
import java.util.List;

import com.sa.su.model.FileNameAndSecuence;
import com.sa.su.model.Pedido;
import com.sa.su.model.Rubro;

public interface ProcesarService {
	List<Pedido> findAProcesar(String legajo);

	List<Pedido> procesar(List<Long> material, Date feDesde, Date feHasta, List<Long> ccostos,
			List<Long> prioridad, List<Long> estado, Boolean validados);
	
	List<Pedido> procesarArchivo(List<Long> ccostos, Rubro rubro);
	
	List<Pedido> procesadosNum(Long num_trat);
	
	List<FileNameAndSecuence> getFileNameAndSecuence(List<Long> ccostos, Long rubro);
	
}
