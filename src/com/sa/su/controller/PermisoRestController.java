package com.sa.su.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sa.su.beans.CRUDStatus;
import com.sa.su.beans.SuministrosException;
import com.sa.su.enums.Funcion;
import com.sa.su.model.Permiso;
import com.sa.su.service.PermisoService;

@RestController
@RequestMapping("api/permiso")
public class PermisoRestController {
	@Autowired
	PermisoService service;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Permiso>> findFilteredUsers(
			@RequestParam(value = "codPuesto", required = false) Integer codPuesto,
			@RequestParam(value = "codArea", required = false) Integer codArea,
			@RequestParam(value = "funciones", required = false) List<Integer> funciones){
		List<Permiso> resp = new ArrayList<Permiso>();
		resp = service.findFilteredList(codPuesto, codArea, funciones);
		return new ResponseEntity<List<Permiso>>(resp, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<CRUDStatus> create(@RequestBody Permiso permiso, HttpServletRequest request) {
	    try {
			String ivUser = request.getHeader("iv-user").toUpperCase();
			
			if (!service.usuarioTieneFuncion(ivUser, Funcion.PARAMETRIA.getId()))
				return new ResponseEntity<CRUDStatus>(new CRUDStatus(403L, ""),HttpStatus.FORBIDDEN);
			
			service.create(permiso, ivUser);
			return new ResponseEntity<CRUDStatus>(new CRUDStatus(0L,""),HttpStatus.CREATED);
		} catch (SuministrosException e) {
			return new ResponseEntity<CRUDStatus>(new CRUDStatus(1L, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			return new ResponseEntity<CRUDStatus>(new CRUDStatus(0L, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public ResponseEntity<CRUDStatus> delete(@RequestBody Permiso permiso, HttpServletRequest request) {
		try {
			String ivUser = request.getHeader("iv-user").toUpperCase();
			
			if (!service.usuarioTieneFuncion(ivUser, Funcion.PARAMETRIA.getId()))
				return new ResponseEntity<CRUDStatus>(new CRUDStatus(403L, ""),HttpStatus.FORBIDDEN);
			
			service.delete(permiso, ivUser);
			return new ResponseEntity<CRUDStatus>(new CRUDStatus(0L, ""), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<CRUDStatus>(new CRUDStatus(0L, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
