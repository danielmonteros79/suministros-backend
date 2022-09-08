package com.sa.su.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
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
import com.sa.su.beans.PedidosResponse;
import com.sa.su.enums.Funcion;
import com.sa.su.model.CentroCostos;
import com.sa.su.model.Pedido;
import com.sa.su.service.CentroCostosService;
import com.sa.su.service.PedidoService;
import com.sa.su.service.PermisoService;
import com.sa.su.service.UsuarioService;

import sun.security.krb5.internal.ccache.CCacheOutputStream;

@RestController
@RequestMapping("api/pedido")
@SuppressWarnings("all")
public class PedidosRestController {
	@Autowired
	PedidoService service;
	
	@Autowired
	PermisoService permisoService;
	
	@Autowired
	UsuarioService usuarioService;
	@Autowired
	CentroCostosService cCostosservice;

	@RequestMapping(method = RequestMethod.GET, value = "/pendiente")
	public ResponseEntity<PedidosResponse> listPending(
			@RequestParam(value = "material", required = false) List<Long> material,
			@RequestParam(value = "ccostos", required = false) List<Long> ccostos,
			@RequestParam(value = "prioridad", required = false) List<Long> prioridad,
			@RequestParam(value = "fechaDesdePend", required = false) Date feDesde,
			@RequestParam(value = "fechaHastaPend", required = false) Date feHasta, HttpServletRequest request){
		PedidosResponse pr = new PedidosResponse();
		String ivUser = request.getHeader("iv-user").toUpperCase();
		List<CentroCostos> list = new ArrayList<CentroCostos>();
		List<Long> estadoList = new ArrayList<Long>();
		estadoList.add(1L); //Pending?

		if (ccostos == null) {
			//if (!permisoService.usuarioTieneFuncion(ivUser, Funcion.TRATAMIENTO.getId())) {
				list = cCostosservice.getByUser(ivUser);
				ccostos = new ArrayList<Long>();
				ccostos.add(list.get(0).getId());
		//	} 
		}
		pr.setPendiente(service.findFilteredList(material, ccostos, prioridad, feDesde, feHasta, estadoList, "PEND"));

		return new ResponseEntity<PedidosResponse>(pr, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/resuelto")
	public ResponseEntity<PedidosResponse> listResuelto(
			@RequestParam(value = "material", required = false) List<Long> material,
			@RequestParam(value = "ccostos", required = false) List<Long> ccostos,
			@RequestParam(value = "prioridad", required = false) List<Long> prioridad,
			@RequestParam(value = "fechaDesdeRecep", required = false) Date feDesde,
			@RequestParam(value = "fechaHastaRecep", required = false) Date feHasta,
			@RequestParam(value = "checkRejected", required = true) boolean checkRej, HttpServletRequest request) {
//		List<Empleado> empleados = service.findFilteredList(id);
		PedidosResponse pr = new PedidosResponse();
		String ivUser = request.getHeader("iv-user").toUpperCase();
		List<CentroCostos> list = new ArrayList<CentroCostos>();
		List<Long> estadoList = new ArrayList<Long>();
		if (checkRej) estadoList.add(4L); // Rechazado
		else estadoList.add(6L); // Recibido
		
		if (ccostos == null) {
		//	if (!permisoService.usuarioTieneFuncion(ivUser, Funcion.TRATAMIENTO.getId())) {
				list = cCostosservice.getByUser(ivUser);
				ccostos = new ArrayList<Long>();
				ccostos.add(list.get(0).getId());
		//	} 
		}
		pr.setResuelto(service.findFilteredList(material, ccostos, prioridad, feDesde, feHasta, estadoList, "RES"));


		return new ResponseEntity<PedidosResponse>(pr, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/tratado")
	public ResponseEntity<PedidosResponse> listTratados(
			@RequestParam(value = "material", required = false) List<Long> material,
			@RequestParam(value = "ccostos", required = false) List<Long> ccostos,
			@RequestParam(value = "prioridad", required = false) List<Long> prioridad,
			@RequestParam(value = "feDesde", required = false) Date feDesde,
			@RequestParam(value = "feHasta", required = false) Date feHasta, HttpServletRequest request) {
		PedidosResponse pr = new PedidosResponse();
		String ivUser = request.getHeader("iv-user").toUpperCase();
		List<CentroCostos> list = new ArrayList<CentroCostos>();
		List<Long> estadoList = new ArrayList<Long>();
		estadoList.add(3L); //aprobado, sin tratar.
		estadoList.add(5L);
		
		if (ccostos == null) {
		//	if (!permisoService.usuarioTieneFuncion(ivUser, Funcion.TRATAMIENTO.getId())) {
				list = cCostosservice.getByUser(ivUser);
				ccostos = new ArrayList<Long>();
				ccostos.add(list.get(0).getId());
		//	} 
		}
		pr.setRecepcion(service.findFilteredList(material, ccostos, prioridad, feDesde, feHasta, estadoList, "TRAT"));

		return new ResponseEntity<PedidosResponse>(pr, HttpStatus.OK);
	}


	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<CRUDStatus> newGasto(@RequestBody List<Pedido> list, BindingResult result, ModelMap mode, HttpServletRequest request) {

		String ivUser = request.getHeader("iv-user").toUpperCase();
		CRUDStatus status = new CRUDStatus(0L,"");
		try {
		service.saveListPedidos(list, ivUser, usuarioService.find(ivUser).getCentroCostos().getId());
		return new ResponseEntity<CRUDStatus>(status,HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<CRUDStatus>(status, HttpStatus.CREATED);
		}
	}
	
	@RequestMapping(value = "/recibir", method = RequestMethod.POST)
	public ResponseEntity<CRUDStatus> recibir(@RequestBody List<Pedido> list, BindingResult result, ModelMap mode, HttpServletRequest request) {
		String ivUser = request.getHeader("iv-user").toUpperCase();
		
		if (!permisoService.usuarioTieneFuncion(ivUser, Funcion.TRATAMIENTO.getId()))
			return new ResponseEntity<CRUDStatus>(new CRUDStatus(403L, ""),HttpStatus.FORBIDDEN);
		
		service.recibir(list, ivUser); 
	    CRUDStatus status = new CRUDStatus(0L,"");
		return new ResponseEntity<CRUDStatus>(status,HttpStatus.CREATED);
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ResponseEntity<CRUDStatus> update(@RequestBody Pedido pedido, BindingResult result, ModelMap model,
			HttpServletRequest request) {
		CRUDStatus status;
		Long id;
		try {
			String ivUser = request.getHeader("iv-user").toUpperCase();
			id = service.savePedido(pedido, ivUser);
			status = new CRUDStatus(id, "");

		} catch (DataIntegrityViolationException e) {
			status = new CRUDStatus(0L, e.getMessage());
			return new ResponseEntity<CRUDStatus>(status, HttpStatus.INTERNAL_SERVER_ERROR);

		} catch (Exception e) {
			status = new CRUDStatus(0L, e.getMessage());
			return new ResponseEntity<CRUDStatus>(status, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<CRUDStatus>(status, HttpStatus.CREATED);
	}
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public ResponseEntity<CRUDStatus> delete(@RequestBody Long idPedido, BindingResult result, ModelMap model,
			HttpServletRequest request) {
		CRUDStatus status;
		Long id;
		try {
//			String ivUser = request.getHeader("iv-user").toUpperCase();
			id = service.deletePedido(idPedido);
			status = new CRUDStatus(id, "");

		} catch (DataIntegrityViolationException e) {
			status = new CRUDStatus(0L, e.getMessage());
			return new ResponseEntity<CRUDStatus>(status, HttpStatus.INTERNAL_SERVER_ERROR);

		} catch (Exception e) {
			status = new CRUDStatus(0L, e.getMessage());
			return new ResponseEntity<CRUDStatus>(status, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<CRUDStatus>(status, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/tratar", method = RequestMethod.POST)
	public ResponseEntity<CRUDStatus> tratar(@RequestBody List<Pedido> list, BindingResult result, ModelMap mode, HttpServletRequest request) {

		String ivUser = request.getHeader("iv-user").toUpperCase();
		
		if (!permisoService.usuarioTieneFuncion(ivUser, Funcion.TRATAMIENTO.getId()))
			return new ResponseEntity<CRUDStatus>(new CRUDStatus(403L, ""),HttpStatus.FORBIDDEN);
		
		CRUDStatus status = new CRUDStatus(0L,"");
		try {
			List<Pedido> listSinDuplicados = new ArrayList<Pedido>(new HashSet<Pedido>(list));//Elimina los duplicados (revisar porque vienen desde el front asi!!) checkCode
			service.saveListPedidos(listSinDuplicados, ivUser);
			return new ResponseEntity<CRUDStatus>(status,HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<CRUDStatus>(status,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
