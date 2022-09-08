package com.sa.su.dao;

import java.util.List;

import com.sa.su.model.Alerta;

public interface AlertaDao extends GenericDao<Long, Alerta>{

	List<Alerta> findAll(List<Long> material, List<String> tipoAlertaPeriodo, List<Long> centroCostos);
	
	List<Alerta> getAlertasMaterialCentroCostos(Long material, Long cc);
}
