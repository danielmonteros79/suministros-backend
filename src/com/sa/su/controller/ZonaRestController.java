package com.sa.su.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sa.su.model.Zona;
import com.sa.su.service.ZonaService;

@RestController
@RequestMapping("api/zona")
public class ZonaRestController {
	@Autowired
	ZonaService service;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Zona>> listAll() {
		List<Zona> list = new ArrayList<Zona>();
		list = service.listAll();

		return new ResponseEntity<List<Zona>>(list, HttpStatus.OK);
	}
}