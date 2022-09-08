package com.sa.su.controller;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sa.su.model.Alerta;
import com.sa.su.service.AlertaService;

@RestController
@RequestMapping("api/alerta")
@SuppressWarnings("all")
public class AlertaRestController {
	Logger log = Logger.getLogger(AlertaRestController.class);
	
	@Autowired
	AlertaService service;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Alerta>> listAll(@RequestParam(value = "material", required = false) List<Long> material,
												@RequestParam(value = "tipoAlertaPeriodo", required = false) List<String> tipoAlertaPeriodo,
												@RequestParam(value = "centroCostos", required = false) List<Long> centroCostos) {
		List<Alerta> list = new ArrayList<Alerta>();
		list = service.findAll(material, tipoAlertaPeriodo, centroCostos);

		return new ResponseEntity<List<Alerta>>(list, HttpStatus.OK);
	}
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity create(@RequestBody Alerta alerta, HttpServletRequest req) {
		try {
			String ivUser = (String) req.getHeader("iv-user").toUpperCase();
			Long id = service.create(alerta, ivUser).getId();
			return new ResponseEntity((id), HttpStatus.CREATED);
		} catch (Exception e) {
			log.error("", e);
			return new ResponseEntity((e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ResponseEntity update(@RequestBody Alerta alerta, HttpServletRequest req) {
		try {
			String ivUser = (String) req.getHeader("iv-user").toUpperCase();
			service.update(alerta, ivUser);
			return new ResponseEntity((alerta.getId()), HttpStatus.OK);
		} catch (Exception e) {
			log.error("", e);
			return new ResponseEntity((e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity delete(@PathVariable("id") long id, HttpServletRequest req) {
		try {
			String ivUser = (String) req.getHeader("iv-user").toUpperCase();
			service.delete(id, ivUser);
			return new ResponseEntity((0L), HttpStatus.OK);
		} catch (Exception e) {
			log.error("", e);
			return new ResponseEntity((e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	
	}
}
