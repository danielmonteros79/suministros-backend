package com.sa.su.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sa.su.model.Funcion;
import com.sa.su.service.FuncionService;

@RestController
@RequestMapping("api/funcion")
public class FuncionRestController {
	@Autowired
	FuncionService service;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Funcion>> listAll() {
		List<Funcion> list = new ArrayList<Funcion>();
		list = service.listAll();

		return new ResponseEntity<List<Funcion>>(list, HttpStatus.OK);
	}
}
