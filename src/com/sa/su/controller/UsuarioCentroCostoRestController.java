package com.sa.su.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sa.su.beans.CRUDStatus;
import com.sa.su.enums.Funcion;
import com.sa.su.model.UsuarioCentroCosto;
import com.sa.su.service.PermisoService;
import com.sa.su.service.UsuarioCentroCostoService;

@RestController
@RequestMapping("api/usuariocc")
public class UsuarioCentroCostoRestController {
	@Autowired
	UsuarioCentroCostoService service;
	
	@Autowired
	PermisoService permisoService;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<List<UsuarioCentroCosto>> findFilteredUsers(
			@RequestParam(value = "legajo", required = false) String legajo,
			@RequestParam(value = "centroCostos", required = false) List<Long> ccostos){
		List<UsuarioCentroCosto> resp = new ArrayList<UsuarioCentroCosto>();
		resp = service.findFilteredList(legajo, ccostos);
		return new ResponseEntity<List<UsuarioCentroCosto>>(resp, HttpStatus.OK);
	}
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<CRUDStatus> newUserCC(@RequestBody UsuarioCentroCosto userCC , BindingResult result, ModelMap mode, HttpServletRequest request) {
		String ivUser = request.getHeader("iv-user").toUpperCase();
		
		if (!permisoService.usuarioTieneFuncion(ivUser, Funcion.PARAMETRIA.getId()))
			return new ResponseEntity<CRUDStatus>(new CRUDStatus(403L, ""),HttpStatus.FORBIDDEN);
		
		service.saveUserCC(userCC);
	    CRUDStatus status = new CRUDStatus(0L,"");
		return new ResponseEntity<CRUDStatus>(status,HttpStatus.CREATED);
	}
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public ResponseEntity<CRUDStatus> delete(@RequestBody UsuarioCentroCosto userCC, BindingResult result, ModelMap model,
			HttpServletRequest request) {
		CRUDStatus status;
		status = new CRUDStatus(0L, "");
		try {
			String ivUser = request.getHeader("iv-user").toUpperCase();
			
			if (!permisoService.usuarioTieneFuncion(ivUser, Funcion.PARAMETRIA.getId()))
				return new ResponseEntity<CRUDStatus>(new CRUDStatus(403L, ""),HttpStatus.FORBIDDEN);
			
			service.deleteUserCC(userCC);

		} catch (DataIntegrityViolationException e) {
			status = new CRUDStatus(0L, e.getMessage());
			return new ResponseEntity<CRUDStatus>(status, HttpStatus.INTERNAL_SERVER_ERROR);

		} catch (Exception e) {
			status = new CRUDStatus(0L, e.getMessage());
			return new ResponseEntity<CRUDStatus>(status, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<CRUDStatus>(status, HttpStatus.CREATED);
	}
}
