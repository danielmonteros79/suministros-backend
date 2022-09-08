package com.sa.su.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sa.su.beans.CRUDStatus;
import com.sa.su.enums.Funcion;
import com.sa.su.model.CentroCostos;
import com.sa.su.service.CentroCostosService;
import com.sa.su.service.PermisoService;

@RestController
@RequestMapping("api/centrocostos")
@SuppressWarnings("all")
public class CentroCostosRestController {
	@Autowired
	CentroCostosService service;
	@Autowired
	PermisoService permisoService;
/**
 * D centro de costos segun si tiene funcion asignada o no
 * @param request
 * @return 
 */
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<CentroCostos>> listAll(HttpServletRequest request) {
		String ivUser = request.getHeader("iv-user").toUpperCase();
		List<CentroCostos> list = new ArrayList<CentroCostos>();
		
		
		if (permisoService.usuarioTieneFuncion(ivUser, Funcion.TRATAMIENTO.getId())) {
			list = service.findAll();
		} else {
			list = service.getByUser(ivUser);
		}
		
		list = service.addAdress(list);

		return new ResponseEntity<List<CentroCostos>>(list, HttpStatus.OK);
	}
	/**
	 * devuelve centro de costos para solapa "Aprobacion",solo trae centros de costos pertenecientes al usuario logueado
	 * @param request
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/pedido")
	public ResponseEntity<List<CentroCostos>> listByUser(HttpServletRequest request) {
		String ivUser = request.getHeader("iv-user").toUpperCase();
		List<CentroCostos> list = new ArrayList<CentroCostos>();
		
		list = service.getByUser(ivUser);
		list = service.addAdress(list);

		return new ResponseEntity<List<CentroCostos>>(list, HttpStatus.OK);
	}
	@RequestMapping(method = RequestMethod.GET, value = "/ofZona")
	public ResponseEntity<List<CentroCostos>> findOfZona(@RequestParam(value = "zona", required = true) Integer zona){
		List<CentroCostos> list = new ArrayList<CentroCostos>();
		list = service.findOfZona(zona);
		return new ResponseEntity<List<CentroCostos>>(list, HttpStatus.OK);
	}
}
