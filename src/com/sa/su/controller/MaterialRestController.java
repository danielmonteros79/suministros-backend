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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.sa.su.beans.CRUDStatus;
import com.sa.su.enums.Funcion;
import com.sa.su.model.Material;
import com.sa.su.service.MaterialService;
import com.sa.su.service.PermisoService;
import com.sa.su.service.UsuarioService;
import com.sa.su.util.DateUtil;

@RestController
@RequestMapping("api/material")
public class MaterialRestController {
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	MaterialService service;
	
	@Autowired
	PermisoService permisoService;

	@RequestMapping(method = RequestMethod.GET)
	public List<Material> listAll() {
		List<Material> list = new ArrayList<Material>();
		list = service.findAll();

		return list;
	}

	@RequestMapping(value = "/forPedido",method = RequestMethod.GET)
	public List<Material> listForPedido(HttpServletRequest request) {
		String ivUser = request.getHeader("iv-user").toUpperCase();
		List<Material> list = new ArrayList<Material>();

		
		list = service.findForPedido(usuarioService.find(ivUser).getCentroCostos().getId());
//		list = service.findForPedido(1000l);

		return list;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Material> get(@PathVariable("id") String id) {
		Material object = service.getById(id);
		return new ResponseEntity<Material>(object, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/ofRubro")
	public ResponseEntity<List<Material>> getFromRubro(@RequestParam(value = "rubros", required = true) List<Long> idRubros){
		List<Material> list = new ArrayList<Material>();
		list = service.findOfRubro(idRubros);
		return new ResponseEntity<List<Material>>(list, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/urgente")
	public ResponseEntity<List<Material>> getUrgente(@RequestParam(value = "urgente", required = true) List<String> urgente){
		List<Material> list = new ArrayList<Material>();
		list = service.findUrgente(urgente);
		return new ResponseEntity<List<Material>>(list, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/lastOrdered", method = RequestMethod.GET)
	public ResponseEntity<List<Material>> getLastOrdered(HttpServletRequest request){
		String ivUser = request.getHeader("iv-user").toUpperCase();
		List<Material> list = new ArrayList<Material>();
		list = service.getLastOrdered(ivUser);
		return new ResponseEntity<List<Material>>(list, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/filtered", method = RequestMethod.GET)
	public ResponseEntity<List<Material>> findFilteredMaterial(
			@RequestParam(value = "rubro", required = false) List<Long> idProv,
			@RequestParam(value = "material", required = false) String mat){
		List<Material> resp = new ArrayList<Material>();
		resp = service.findFilteredList(idProv, mat);
		return new ResponseEntity<List<Material>>(resp, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<CRUDStatus> newMaterial(@RequestBody Material material, HttpServletRequest request) {
		String ivUser = request.getHeader("iv-user").toUpperCase();
		
		if (!permisoService.usuarioTieneFuncion(ivUser, Funcion.PARAMETRIA.getId()))
			return new ResponseEntity<CRUDStatus>(new CRUDStatus(403L, ""),HttpStatus.FORBIDDEN);
		
		service.saveMaterial(material, ivUser);
	    CRUDStatus status = new CRUDStatus(0L,"");
		return new ResponseEntity<CRUDStatus>(status,HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public ResponseEntity<CRUDStatus> delete(@RequestBody Material material, BindingResult result, ModelMap model,
			HttpServletRequest request) {
		CRUDStatus status;
		status = new CRUDStatus(0L, "");
		try {
			String ivUser = request.getHeader("iv-user").toUpperCase();
			
			if (!permisoService.usuarioTieneFuncion(ivUser, Funcion.PARAMETRIA.getId()))
				return new ResponseEntity<CRUDStatus>(new CRUDStatus(403L, ""),HttpStatus.FORBIDDEN);
			
			service.deleteMaterial(material, ivUser);

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
	public ResponseEntity<CRUDStatus> update(@RequestBody Material material, HttpServletRequest request) {
		CRUDStatus status;
		Long id;
		try {
			String ivUser = request.getHeader("iv-user").toUpperCase();
			
			if (!permisoService.usuarioTieneFuncion(ivUser, Funcion.PARAMETRIA.getId()))
				return new ResponseEntity<CRUDStatus>(new CRUDStatus(403L, ""),HttpStatus.FORBIDDEN);
			
			id = service.updateMaterial(material, ivUser);
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
			@RequestParam(value = "material", required = false) String mat,
			@RequestParam(value = "rubro", required = false) List<Long> idRubro) throws Exception {
		try {
			Workbook wb = new HSSFWorkbook();
            Sheet sheet = wb.createSheet("MySheet");
            List<Material> materiales = service.findFilteredList(idRubro, mat);
            
			int i = 0, j = 0;
            Row row = sheet.createRow(i++);
            row.createCell(j++).setCellValue("Descripcion");
            row.createCell(j++).setCellValue("Tipo Material");
            row.createCell(j++).setCellValue("Rubro");
            row.createCell(j++).setCellValue("Cod. Externo");
            row.createCell(j++).setCellValue("Unidad");
            row.createCell(j++).setCellValue("Cantidad Maxima");
            row.createCell(j++).setCellValue("Cantidad Media");
            row.createCell(j++).setCellValue("Cantidad Minima");
            row.createCell(j++).setCellValue("Cantidad Stock");
            row.createCell(j++).setCellValue("Precio");
            row.createCell(j++).setCellValue("Fecha Alta");
//            row.createCell(j++).setCellValue("Usuario Alta");
//            row.createCell(j++).setCellValue("Fecha Modificacion");
//            row.createCell(j++).setCellValue("Usuario Modificacion");
//            row.createCell(j++).setCellValue("Fecha Baja");
//            row.createCell(j++).setCellValue("Usuario Baja");
            for (Material material : materiales) {				
				row = sheet.createRow(i++);
				j = 0;
	            row.createCell(j++).setCellValue(material.getDescripcion());
	            row.createCell(j++).setCellValue(material.getTipo_material().getDescripcion());
	            row.createCell(j++).setCellValue(material.getRubro().getDescripcion());
	            row.createCell(j++).setCellValue(material.getCodigo_material_externo());
	            row.createCell(j++).setCellValue(material.getUni_material());
	            row.createCell(j++).setCellValue(material.getCantidad_maxima());
	            row.createCell(j++).setCellValue(material.getCantidad_media());
	            row.createCell(j++).setCellValue(material.getCantidad_minima());
	            row.createCell(j++).setCellValue(material.getCantidad_stock());
	            row.createCell(j++).setCellValue(material.getPrecio_pp().doubleValue());
	            row.createCell(j++).setCellValue(DateUtil.format("dd/MM/yyyy", material.getFechaAlta()));
//	            row.createCell(j++).setCellValue(material.getUsuarioAlta());
//	            if (material.getFechaMod() != null){
//	            	row.createCell(j++).setCellValue(DateUtil.format("dd/MM/yyyy", material.getFechaMod()));
//	            	row.createCell(j++).setCellValue(material.getUsuarioMod());
//	            }else{
//	            	row.createCell(j++).setCellValue("");
//	            	row.createCell(j++).setCellValue("");
//	            }
//	            if (material.getFechaBaja() != null){
//	            	row.createCell(j++).setCellValue(DateUtil.format("dd/MM/yyyy", material.getFechaBaja()));
//	            	row.createCell(j++).setCellValue(material.getUsuarioBaja());
//	            }else{
//	            	row.createCell(j++).setCellValue("");
//	            	row.createCell(j++).setCellValue("");
//	            }
			}
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=tiposmateriales.xls");

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