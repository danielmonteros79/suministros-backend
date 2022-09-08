package com.sa.su.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sa.su.beans.CRUDStatus;
import com.sa.su.model.Favorito;
import com.sa.su.service.FavoritoService;

@RestController
@RequestMapping("api/favorito")
public class FavoritoRestController {
	@Autowired
	FavoritoService service;

	@RequestMapping(method = RequestMethod.GET)
	public List<Favorito> listAll(HttpServletRequest request) {
		List<Favorito> list = new ArrayList<Favorito>();
		String ivUser = request.getHeader("iv-user").toUpperCase();
		list = service.findAll(ivUser);

		return list;
	}

	@RequestMapping(value = "add/{id}", method = RequestMethod.POST)
	public ResponseEntity<CRUDStatus> add(HttpServletRequest request, @PathVariable("id") String id) {
		String ivUser = request.getHeader("iv-user").toUpperCase();
		service.add(id, ivUser);
		return new ResponseEntity<CRUDStatus>(new CRUDStatus(0L, ""), HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "delete/{id}", method = RequestMethod.POST)
	public ResponseEntity<CRUDStatus> delete(HttpServletRequest request, @PathVariable("id") String id) {
		String ivUser = request.getHeader("iv-user").toUpperCase();
		service.delete(id, ivUser);
		return new ResponseEntity<CRUDStatus>(new CRUDStatus(0L, ""), HttpStatus.OK);
	}
		

}
