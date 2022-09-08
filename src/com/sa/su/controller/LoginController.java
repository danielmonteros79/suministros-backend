package com.sa.su.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sa.su.model.CentroCostos;
import com.sa.su.model.Funcion;
import com.sa.su.model.Permiso;
import com.sa.su.model.Usuario;
import com.sa.su.service.CentroCostosService;
import com.sa.su.service.PermisoService;
import com.sa.su.service.UsuarioService;
import com.sa.su.util.AuthUtils;

import ar.com.bbva.web.services.usrdtws.client.CustomUserDetailsService;
import ar.com.bbva.web.services.usrdtws.client.WebsealWSClient;

@Controller
@RequestMapping("api/login")
public class LoginController {
	Logger log = Logger.getLogger(LoginController.class);
	
	
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	PermisoService permisoService;
	
	@Autowired
	CentroCostosService centroCostosService;
	
//	@RequestMapping
//	public String login(HttpServletRequest request) {
//		try {			
//			String ivUser = request.getHeader("iv-user");
//			log.info("###############################################iv-user: "+ivUser);
//	CustomUserDetailsService cuds = new CustomUserDetailsService();
//	cuds.setWebsealWSClient(new WebsealWSClient());
//	Usuario user = cuds.processUsers(ivUser.toUpperCase());
////	Se obtiene el centro de costos de la tabla de usuarios	
//		user.setCentroCostos(usuarioService.find(ivUser).getCentroCostos());
//		
//	this.setUserFunciones(user);
//	
//	user = usuarioService.update(user);
//	
//	String token = AuthUtils.createToken(user);
//	
//	return "redirect:../#/login/" + token;
//		} catch (Exception e) {
//			log.error("Error en login", e);
//		}
//		
//		return null;
//	}
	
	//TODO: No pasar a prod
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String login(HttpServletRequest request, @RequestParam(value = "legajo", required = false) String legajo) {
		try {			
			String ivUser = request.getHeader("iv-user").toUpperCase();
			log.info("iv-user: "+ivUser);
			if (ivUser == null)
				ivUser = legajo;
			
            legajo = ivUser;
			
			CustomUserDetailsService cuds = new CustomUserDetailsService();
			cuds.setWebsealWSClient(new WebsealWSClient());
			Usuario user = cuds.processUsers(ivUser.toUpperCase());
//			user.setCentroCostos(new CentroCostos(1000l));
//			Se obtiene el centro de costos de la tabla de usuarios	
				user.setCentroCostos(usuarioService.find(legajo).getCentroCostos());
				
			this.setUserFunciones(user);
			
		//	user = usuarioService.update(user);
			
			String token = AuthUtils.createToken(user);
			
			return "redirect:../#/login/" + token;
		} catch (Exception e) {
			log.error("Error en login", e);
			
			try {
				Usuario user = new Usuario();
				user.setLegajo(legajo);
				if (legajo.equals("A119460")) {
					user.setNombre(usuarioService.find(legajo).getNombre());
					user.setApellido(usuarioService.find(legajo).getApellido());
					user.setCodPuesto(usuarioService.find(legajo).getCodPuesto());
					user.setCodArea(usuarioService.find(legajo).getCodArea());
					user.setCentroCostos(usuarioService.find(legajo).getCentroCostos());
					this.setUserFunciones(user);
					
				} else if (legajo.equals("A103555")) {
					user.setNombre(usuarioService.find(legajo).getNombre());
					user.setApellido(usuarioService.find(legajo).getApellido());
					user.setCodPuesto(usuarioService.find(legajo).getCodPuesto());
					user.setCodArea(usuarioService.find(legajo).getCodArea());
					user.setCentroCostos(usuarioService.find(legajo).getCentroCostos());
					this.setUserFunciones(user);
				}
				//user = usuarioService.update(user);
				String token = AuthUtils.createToken(user);
				
				return "redirect:../#/login/" + token;
			} catch (Exception e2) {
				log.error(e2);
			}
		}
		
		return null;
	} 
	
	private void setUserFunciones(Usuario user) {
		user.setFunciones(new ArrayList<Integer>());
		List<Permiso> permisos = permisoService.findFilteredList(user.getCodPuesto(), user.getCodArea(), null);
		for (Permiso permiso : permisos) {
			user.getFunciones().add(permiso.getFuncion().getId());
		}
	}
	
}