package com.sa.su.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sa.su.model.Estado;
import com.sa.su.service.EstadoService;

@RestController
@RequestMapping("api/estado")
public class EstadoRestController {
	@Autowired
	EstadoService service;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Estado>> listAll() {
		List<Estado> list = new ArrayList<Estado>();
		list = service.findAll();

		return new ResponseEntity<List<Estado>>(list, HttpStatus.OK);
	}

	@RequestMapping(value = "/procesar", method = RequestMethod.GET)
	public ResponseEntity<List<Estado>> listProcessStatus() {
		List<Estado> list = new ArrayList<Estado>();
		list = service.findProcessStatus();

		return new ResponseEntity<List<Estado>>(list, HttpStatus.OK);
	}
}
