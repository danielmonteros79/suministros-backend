package com.sa.su.controller;


import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sa.su.model.Periodo;
import com.sa.su.model.TipoAlertaPeriodo;
import com.sa.su.model.TipoAlerta;
import com.sa.su.service.PeriodoService;
import com.sa.su.service.TipoAlertaService;

@RestController
@RequestMapping("api/tipoAlerta")
public class TipoAlertaRestController {
	Logger log = Logger.getLogger(TipoAlertaRestController.class);
	
	@Autowired
	TipoAlertaService tipoAlertaService;
	
	@Autowired
	PeriodoService periodoService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<TipoAlertaPeriodo> listAll() {
		List<TipoAlertaPeriodo> list = new ArrayList<TipoAlertaPeriodo>();
		
		List<TipoAlerta> tipos = tipoAlertaService.findAll();
		List<Periodo> periodos = periodoService.findAll();
		
		for (TipoAlerta tipo : tipos) {
			for (Periodo periodo : periodos) {
				list.add(new TipoAlertaPeriodo(tipo, periodo));
			}
		}
		
		return list;
	}
	
}
