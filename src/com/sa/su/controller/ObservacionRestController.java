package com.sa.su.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sa.su.beans.CRUDStatus;
import com.sa.su.model.Observacion;
import com.sa.su.service.ObservacionService;

@RestController
@RequestMapping("api/observacion")
public class ObservacionRestController {
	@Autowired
	ObservacionService service;
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<CRUDStatus> newObservacion(@RequestBody Observacion observacion, BindingResult result, ModelMap mode, HttpServletRequest request){
		service.saveObservacion(observacion);
	    CRUDStatus status = new CRUDStatus(0L,"");
		return new ResponseEntity<CRUDStatus>(status,HttpStatus.CREATED);
	}
}
