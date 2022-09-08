package com.sa.su.controller;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
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
import com.sa.su.model.Justificacion;
import com.sa.su.service.JustificacionService;
import com.sa.su.service.PermisoService;
import com.sa.su.util.DateUtil;

@RestController
@RequestMapping("api/justificacion")
public class JustificacionRestController {
	@Autowired
	JustificacionService service;
	
	@Autowired
	PermisoService permisoService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Justificacion>> listAll() {
		List<Justificacion> list = new ArrayList<Justificacion>();
		list = service.listAll();

		return new ResponseEntity<List<Justificacion>>(list, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/filtered", method = RequestMethod.GET)
	public ResponseEntity<List<Justificacion>> findFilteredJustificacion(
			@RequestParam(value = "id", required = false) List<Long> id){
		List<Justificacion> resp = new ArrayList<Justificacion>();
		resp = service.findFilteredList(id);
		return new ResponseEntity<List<Justificacion>>(resp, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<CRUDStatus> newJustificacion(@RequestBody Justificacion justificacion, BindingResult result, ModelMap mode, HttpServletRequest request){
		String ivUser = request.getHeader("iv-user").toUpperCase();
		
		if (!permisoService.usuarioTieneFuncion(ivUser, Funcion.PARAMETRIA.getId()))
			return new ResponseEntity<CRUDStatus>(new CRUDStatus(403L, ""),HttpStatus.FORBIDDEN);
		
		service.saveJustificacion(justificacion, ivUser);
	    CRUDStatus status = new CRUDStatus(0L,"");
		return new ResponseEntity<CRUDStatus>(status,HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public ResponseEntity<CRUDStatus> delete(@RequestBody Justificacion justificacion, BindingResult result, ModelMap model,
			HttpServletRequest request) {
		CRUDStatus status;
		status = new CRUDStatus(0L, "");
		try {
			String ivUser = request.getHeader("iv-user").toUpperCase();
			
			if (!permisoService.usuarioTieneFuncion(ivUser, Funcion.PARAMETRIA.getId()))
				return new ResponseEntity<CRUDStatus>(new CRUDStatus(403L, ""),HttpStatus.FORBIDDEN);
			
			service.deleteJustificacion(justificacion, ivUser);

		} catch (DataIntegrityViolationException e) {
			status = new CRUDStatus(0L, e.getMessage());
			return new ResponseEntity<CRUDStatus>(status, HttpStatus.INTERNAL_SERVER_ERROR);

		} catch (Exception e) {
			status = new CRUDStatus(0L, e.getMessage());
			return new ResponseEntity<CRUDStatus>(status, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<CRUDStatus>(status, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ResponseEntity<CRUDStatus> update(@RequestBody Justificacion justificacion, BindingResult result, ModelMap model,
			HttpServletRequest request) {
		CRUDStatus status;
		Long id;
		try {
			String ivUser = request.getHeader("iv-user").toUpperCase();
			
			if (!permisoService.usuarioTieneFuncion(ivUser, Funcion.PARAMETRIA.getId()))
				return new ResponseEntity<CRUDStatus>(new CRUDStatus(403L, ""),HttpStatus.FORBIDDEN);
			
			id = service.updateJustificacion(justificacion, ivUser);
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
	
	@RequestMapping(value = "/exportXLS", method = RequestMethod.GET)
	public void exportXLS(HttpServletResponse response,
			@RequestParam(value = "id", required = false) List<Long> id) throws Exception {
		try {
			Workbook wb = new HSSFWorkbook();
            Sheet sheet = wb.createSheet("MySheet");
            List<Justificacion> justificaciones = service.findFilteredList(id);
            
			int i = 0, j = 0;
            Row row = sheet.createRow(i++);
            row.createCell(j++).setCellValue("Id");
            row.createCell(j++).setCellValue("Justificaciones");
            row.createCell(j++).setCellValue("Fecha Alta");
//            row.createCell(j++).setCellValue("Usuario Alta");
//            row.createCell(j++).setCellValue("Fecha Modificacion");
//            row.createCell(j++).setCellValue("Usuario Modificacion");
//            row.createCell(j++).setCellValue("Fecha Baja");
//            row.createCell(j++).setCellValue("Usuario Baja");
            for (Justificacion justificacion : justificaciones) {				
				row = sheet.createRow(i++);
				j = 0;
	            row.createCell(j++).setCellValue(justificacion.getId());
	            row.createCell(j++).setCellValue(justificacion.getDescripcion());
	            row.createCell(j++).setCellValue(DateUtil.format("dd/MM/yyyy",justificacion.getFechaAlta()));
//	            row.createCell(j++).setCellValue(justificacion.getUsuarioAlta());
//	            if (justificacion.getFechaMod() != null){
//	            	row.createCell(j++).setCellValue(DateUtil.format("dd/MM/yyyy", justificacion.getFechaMod()));
//	            	row.createCell(j++).setCellValue(justificacion.getUsuarioMod());
//	            }else{
//	            	row.createCell(j++).setCellValue("");
//	            	row.createCell(j++).setCellValue("");
//	            }
//	            if (justificacion.getFechaBaja() != null){
//	            	row.createCell(j++).setCellValue(DateUtil.format("dd/MM/yyyy", justificacion.getFechaBaja()));
//	            	row.createCell(j++).setCellValue(justificacion.getUsuarioBaja());
//	            }else{
//	            	row.createCell(j++).setCellValue("");
//	            	row.createCell(j++).setCellValue("");
//	            }
			}
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=justificaciones.xls");

            OutputStream out = response.getOutputStream();
            wb.write(out); 

            out.flush();
            out.close();
            wb.close();
        } catch(Exception e) {
			e.printStackTrace();
            throw new Exception(e.getMessage());
        }
	}
}
