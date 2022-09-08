package com.sa.su.controller;

import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.sa.su.beans.CRUDStatus;
import com.sa.su.beans.CustomMappingStrategy;
import com.sa.su.beans.CvsTratamiento;
import com.sa.su.enums.Funcion;
import com.sa.su.model.CentroCostos;
import com.sa.su.model.FileNameAndSecuence;
import com.sa.su.model.Pedido;
import com.sa.su.model.Rubro;
import com.sa.su.service.CentroCostosService;
import com.sa.su.service.PermisoService;
import com.sa.su.service.ProcesarService;
import com.sa.su.service.RubroService;
import com.sa.su.util.DateUtil;
import com.sa.su.util.StringFormatUtil;

@RestController
@RequestMapping("api/procesar")
public class ProcesarRestController {
	@Autowired
	ProcesarService service;
	
	@Autowired 
	CentroCostosService ccService;
	
	@Autowired
	RubroService rubroService;
	
	@Autowired
	PermisoService permisoService;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "/aProcesar")
	public ResponseEntity listPending(HttpServletRequest request) {
		String ivUser = request.getHeader("iv-user").toUpperCase();
		
		if (!permisoService.usuarioTieneFuncion(ivUser, Funcion.TRATAMIENTO.getId()))
			return new ResponseEntity(new CRUDStatus(403L, ""),HttpStatus.FORBIDDEN);
		
		List<Pedido> pedidos = service.findAProcesar(ivUser);

		return new ResponseEntity(pedidos, HttpStatus.OK);
	}
	
	
	@RequestMapping(method = RequestMethod.GET, value="/generarArchivo")
	public void generateFile(HttpServletResponse response,
			@RequestParam(value="zona", required = true) Integer zona,
			@RequestParam(value="numArch", required = false) Long numArch,
			@RequestParam(value="rubro", required = true) Long idRubro) throws Exception {
			

		
			List<Long> ccostos = new ArrayList<Long>();
			List<Pedido> pedidos = null;
			Rubro rubro = rubroService.find(idRubro);
			String fileName = rubro.getDescripcion();
			for (CentroCostos cc : ccService.findOfZona(zona)){
				ccostos.add(cc.getId());
			}
			if (numArch != null)
				pedidos = service.procesadosNum(numArch);
			else pedidos = service.procesarArchivo(ccostos, rubro);
			//fijarse si hacer esto en un service.
		
            PrintWriter out = null;
        	CSVWriter writer = new CSVWriter(null);
	         try {

	            	out = response.getWriter();
	            	writer = new CSVWriter(out,';',CSVWriter.NO_QUOTE_CHARACTER,CSVWriter.DEFAULT_ESCAPE_CHARACTER,CSVWriter.DEFAULT_LINE_END);
			
			 if (pedidos != null && pedidos.size() != 0){
				 List<CvsTratamiento> list = new ArrayList<>();	
				 for (Pedido ped : pedidos){
					 
					 CvsTratamiento cvsTratamiento = new CvsTratamiento();
					 cvsTratamiento.setNroPedidoTratamiento(StringFormatUtil.leftComplete(ped.getCcostos().getId().toString().trim(), 4, '0')+ped.getMaterial().getTipo_material().getId().toString().trim()+StringFormatUtil.leftComplete(ped.getNum_tratamiento().toString().trim(), 8, '0'));
					 cvsTratamiento.setIdPedido(ped.getId().toString().trim());
					 cvsTratamiento.setIdMaterial(ped.getMaterial().getId().toString().trim());
					 cvsTratamiento.setDescripcion(StringFormatUtil.rightComplete(ped.getMaterial().getDescripcion().trim(), 50, ' '));
					 cvsTratamiento.setCantAprobada(StringFormatUtil.leftComplete(ped.getCant_aprobada().toString().trim(), 7, '0'));
					 cvsTratamiento.setUnidades(StringFormatUtil.leftComplete(ped.getMaterial().getUni_material().toString().trim(), 7, '0'));
					 cvsTratamiento.setIdCentroCosto(StringFormatUtil.leftComplete(ped.getCcostos().getId().toString().trim(), 4, '0'));
					 cvsTratamiento.setDireccion1(ped.getCcostos().getDireccion1() == null ? "" : StringFormatUtil.rightComplete(ped.getCcostos().getDireccion1().trim(), 50, ' '));
					 cvsTratamiento.setDireccion2(ped.getCcostos().getDireccion2() == null ? "" : StringFormatUtil.rightComplete(ped.getCcostos().getDireccion2().trim(), 50, ' '));
					 cvsTratamiento.setDireccion3(ped.getCcostos().getDireccion3() == null ? "" : StringFormatUtil.rightComplete(ped.getCcostos().getDireccion3().trim(), 50, ' '));
					 cvsTratamiento.setCodpostal(ped.getCcostos().getCodpostal() == null ? "" : StringFormatUtil.leftComplete(ped.getCcostos().getCodpostal().toString().trim(), 4, '0'));
					 cvsTratamiento.setMonto(StringFormatUtil.leftComplete((ped.getMaterial().getPrecio_pp().multiply(BigDecimal.valueOf(ped.getCant_aprobada()))).toString(), 13, '0'));
					 cvsTratamiento.setFechaTratamiento(DateUtil.format("dd/MM/yyyy",ped.getFe_tratamiento()));
					 cvsTratamiento.setFechaAlta(DateUtil.format("dd/MM/yyyy",ped.getFechaAlta()));
					 list.add(cvsTratamiento);
					 
				 }
				 
			    CustomMappingStrategy<CvsTratamiento> mappingStrategy = new CustomMappingStrategy<CvsTratamiento>();
			    mappingStrategy.setType(CvsTratamiento.class);
			    StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder(writer).withSeparator(';').withMappingStrategy(mappingStrategy).withApplyQuotesToAll(false).build();
			    beanToCsv.write(list);
			     
				 
			 }
			 
	         }
	         finally{
	            	if (writer != null){
	            		fileName += "_" + StringFormatUtil.leftComplete(pedidos.get(0).getNum_tratamiento().toString(), 6, '0') + "_" + DateUtil.format("ddMMyyyy", pedidos.get(0).getFe_tratamiento());;
	                	response.setContentType("text/csv; charset=UTF-8");
	                	response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName.trim() + ".csv\"");
	                	response.setCharacterEncoding("UTF-8");
	                	writer.close();
	                	out.print(out);
	                	out.flush();
	                	out.close();
	            	}
	            }
			
			
			
	}
	
//		@RequestMapping(method = RequestMethod.GET, value="/generarArchivo")
//	public void generateFile(HttpServletResponse response,
//			@RequestParam(value="zona", required = true) Integer zona,
//			@RequestParam(value="numArch", required = false) Long numArch,
//			@RequestParam(value="rubro", required = true) Long idRubro) throws Exception {
//			List<Long> ccostos = new ArrayList<Long>();
//			List<Pedido> pedidos = null;
//			Rubro rubro = rubroService.find(idRubro);
//			String fileName = rubro.getDescripcion();
//			for (CentroCostos cc : ccService.findOfZona(zona)){
//				ccostos.add(cc.getId());
//			}
//			if (numArch != null)
//				pedidos = service.procesadosNum(numArch);
//			else pedidos = service.procesarArchivo(ccostos, rubro);
//			//fijarse si hacer esto en un service.
//            BufferedWriter bw = null;
//            PrintWriter out = null;
//	            try {
//	            	Long cantPedidos = 0L;
//	            	out = response.getWriter();
//	            	bw = new BufferedWriter(out);
//	            if (pedidos != null && pedidos.size() != 0){
//	            	bw.write("H");
//	            	bw.write(';');
//	            	bw.write(StringFormatUtil.leftComplete(rubro.getNro_interfaz().toString(), 4, '0'));
//	            	bw.write(';');
//	            	bw.write(StringFormatUtil.leftComplete(pedidos.get(0).getNum_tratamiento().toString(), 6, '0'));
//	            	bw.write(';');
//	            	bw.write(DateUtil.format("dd/MM/yyyy",new Date()));
//	            	bw.write(';');
//            		bw.write(StringFormatUtil.rightComplete("", 294, ' '));
//            		bw.write(';');
//	            	for (Pedido ped : pedidos){
//	            		bw.newLine();
//	            		bw.write("D");
//	            		bw.write(';');
//	            		bw.write(StringFormatUtil.leftComplete(cantPedidos.toString(), 7, '0')); //num pedido EN ESTE ARCHIVO
//	            		bw.write(';');
///*NRO PEDIDO*/			bw.write(StringFormatUtil.leftComplete(StringFormatUtil.leftComplete(ped.getCcostos().getId().toString().trim(), 4, '0')+ped.getMaterial().getTipo_material().getId().toString().trim()+StringFormatUtil.leftComplete(ped.getNum_tratamiento().toString().trim(), 8, '0'), 50, ' ')+"\t");
//						bw.write(';');
///*ID PEDIDO INTERNO*/	bw.write(StringFormatUtil.leftComplete(ped.getId().toString(), 8, '0'));
//						bw.write(';');
///*ID MATERIAL*/			bw.write(StringFormatUtil.leftComplete(ped.getMaterial().getId().toString(), 8, '0'));
//						bw.write(';');
///*MATERIAL DESCRIPCION*/bw.write(StringFormatUtil.rightComplete(ped.getMaterial().getDescripcion(), 50, ' '));
////						bw.write(";");
/////*MATERIAL EXTERNO*/  bw.write(StringFormatUtil.rightComplete(ped.getMaterial().getCodigo_material_externo(), 10, ' '));
//
//						bw.write(';');
///*CANTIDAD APROBADA*/   bw.write(StringFormatUtil.leftComplete(ped.getCant_aprobada().toString(), 5, '0'));
////						bw.write(";");
/////*precio unidad*/   	bw.write(ped.getMaterial().getPrecio_pp().toString());
//
//
//						bw.write(';');
///*UNIDAD MATERIAL*/	    bw.write(StringFormatUtil.leftComplete(ped.getMaterial().getUni_material().toString(), 10, ' '));
////	            		bw.write(";");
/////*PRIORIDAD*/	        bw.write(StringFormatUtil.leftComplete(ped.getPrioridad().getId().toString(), 5, '0'));
//						bw.write(';');
///*ID CENTRO COSTOS*/	bw.write(StringFormatUtil.leftComplete(ped.getCcostos().getId().toString(), 4, '0'));
//						bw.write(';');
//	            		//VERIFICANDO QUE LOS DATOS NO VENGAN NULL, YA QUE NO SON OBLIGATORIAS LAS COLUMNAS
//	            		String datosContacto = ped.getCcostos().getDireccion1();
//	            		if (null == datosContacto)
//	            			datosContacto="";
///*DIRECCION 1*/         bw.write(StringFormatUtil.rightComplete(datosContacto, 50, ' '));
//						bw.write(';');
//	            		datosContacto = ped.getCcostos().getDireccion2();
//	            		if (null == datosContacto)
//	            			datosContacto="";
///*DIRECCION 1*/	        bw.write(StringFormatUtil.rightComplete(datosContacto, 50, ' '));
//						bw.write(';');
//	            		datosContacto = ped.getCcostos().getDireccion3();
//	            		if (null == datosContacto)
//	            			datosContacto="";
///*DIRECCION 1*/	        bw.write(StringFormatUtil.rightComplete(datosContacto, 50, ' '));
//						bw.write(';');
//	            		//agregar codigo postal aqui
//	            		//descripcion centro de costos aqui
//
//	            		BigDecimal bd =BigDecimal.valueOf(ped.getCant_aprobada());
//	            		BigDecimal pp = ped.getMaterial().getPrecio_pp();
//	            		
//	            		BigDecimal a = pp.multiply(bd);
///*precio*/	        	bw.write(a.toPlainString()+"\t");
//						bw.write(';');
//	            		datosContacto = "";
////	            		if (null != ped.getCcostos().getTelefono())
////	            			datosContacto=ped.getCcostos().getTelefono().toString();
/////*TELEFONO*/       		bw.write(StringFormatUtil.rightComplete(datosContacto, 12, '0'));
////	            		bw.write(";");
////	            		datosContacto = ped.getCcostos().getContacto();
////	            		if (null == datosContacto)
////	            			datosContacto="";
/////*CONTACTO CCOSTOS*/	bw.write(StringFormatUtil.rightComplete(datosContacto, 30, ' '));
//	            		//FIN VERIFICACION
//
////	            		bw.write(";");
///*FECHA TRATAMIENTO*/	bw.write(DateUtil.format("dd/MM/yyyy",ped.getFe_tratamiento()));
//						bw.write(';');
///*FECHA ALTA*/	        bw.write(DateUtil.format("dd/MM/yyyy",ped.getFechaAlta()));
//						bw.write(';');
//	            		cantPedidos++;
//	            	}
//	            	bw.newLine();
//	            	bw.write("T");
//	            	bw.write(';');
//	            	bw.write(StringFormatUtil.leftComplete(cantPedidos.toString(), 7, '0'));
//	            	bw.write(';');
//            		bw.write(StringFormatUtil.rightComplete("", 307, ' '));
//            		bw.write(';');
//            		fileName += "_" + StringFormatUtil.leftComplete(pedidos.get(0).getNum_tratamiento().toString(), 6, '0') + "_" + DateUtil.format("ddMMyyyy", pedidos.get(0).getFe_tratamiento());;
//            		if (numArch == null){
//            			rubro.setNro_sec_tratamiento(rubro.getNro_sec_tratamiento() + 1);
//            			rubroService.updateRubro(rubro, rubro.getUsuarioMod());
//            		}
//	            }//if
//	            }//try
//	            finally{
//	            	if (bw != null){
//	                	response.setContentType("text/csv; charset=ISO-8859-1");
//	                	response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ".csv");
//	                	response.setCharacterEncoding("ISO-8859-1");
//	                	bw.close();
//	                	out.print(out);
//	                	out.flush();
//	                	out.close();
//	            	}
//	            }
//	}
	
/*	@RequestMapping(method = RequestMethod.GET, value="/generarArchivo")
	public void generateFile(HttpServletResponse response,
			@RequestParam(value="zona", required = true) Integer zona,
			@RequestParam(value="numArch", required = false) Long numArch,
			@RequestParam(value="rubro", required = true) Long idRubro) throws Exception {
			List<Long> ccostos = new ArrayList<Long>();
			List<Pedido> pedidos = null;
			Rubro rubro = rubroService.find(idRubro);
			String fileName = rubro.getDescripcion();
			for (CentroCostos cc : ccService.findOfZona(zona)){
				ccostos.add(cc.getId());
			}
			if (numArch != null)
				pedidos = service.procesadosNum(numArch);
			else pedidos = service.procesarArchivo(ccostos, rubro);
			//fijarse si hacer esto en un service.
            BufferedWriter bw = null;
            PrintWriter out = null;
	            try {
	            	Long cantPedidos = 0L;
	            	out = response.getWriter();
	            	bw = new BufferedWriter(out);
	            if (pedidos != null && pedidos.size() != 0){
	            	bw.write("H");
	            	bw.write(";");
	            	bw.write(StringFormatUtil.leftComplete(rubro.getNro_interfaz().toString(), 4, '0'));
	            	bw.write(";");
	            	bw.write(StringFormatUtil.leftComplete(pedidos.get(0).getNum_tratamiento().toString(), 6, '0'));
	            	bw.write(";");
	            	bw.write(DateUtil.format("dd/MM/yyyy",new Date()));
	            	bw.write(";");
            		bw.write(StringFormatUtil.rightComplete("", 294, ' '));
            		bw.write(";");
	            	for (Pedido ped : pedidos){
	            		bw.newLine();
	            		bw.write("D");
	            		bw.write(";");
	            		bw.write(StringFormatUtil.leftComplete(cantPedidos.toString(), 7, '0')); //num pedido EN ESTE ARCHIVO
	            		bw.write(";");
	            		bw.write(StringFormatUtil.leftComplete(ped.getMaterial().getId().toString(), 8, '0'));
	            		bw.write(";");
	            		bw.write(StringFormatUtil.rightComplete(ped.getMaterial().getCodigo_material_externo(), 10, ' '));
	            		bw.write(";");
	            		bw.write(StringFormatUtil.rightComplete(ped.getMaterial().getDescripcion(), 50, ' '));
	            		bw.write(";");
	            		bw.write(StringFormatUtil.leftComplete(ped.getCant_aprobada().toString(), 5, '0'));
	            		bw.write(";");
	            		bw.write(StringFormatUtil.leftComplete(ped.getMaterial().getUni_material().toString(), 10, ' '));
	            		bw.write(";");
	            		bw.write(StringFormatUtil.leftComplete(ped.getPrioridad().getId().toString(), 5, '0'));
	            		bw.write(";");
	            		bw.write(StringFormatUtil.leftComplete(ped.getCcostos().getId().toString(), 4, '0'));
	            		bw.write(";");
	            		//VERIFICANDO QUE LOS DATOS NO VENGAN NULL, YA QUE NO SON OBLIGATORIAS LAS COLUMNAS
	            		String datosContacto = ped.getCcostos().getDireccion1();
	            		if (null == datosContacto)
	            			datosContacto="";
	            		bw.write(StringFormatUtil.rightComplete(datosContacto, 50, ' '));
	            		bw.write(";");
	            		datosContacto = ped.getCcostos().getDireccion2();
	            		if (null == datosContacto)
	            			datosContacto="";
	            		bw.write(StringFormatUtil.rightComplete(datosContacto, 50, ' '));
	            		bw.write(";");
	            		datosContacto = ped.getCcostos().getDireccion3();
	            		if (null == datosContacto)
	            			datosContacto="";
	            		bw.write(StringFormatUtil.rightComplete(datosContacto, 50, ' '));
	            		bw.write(";");
	            		datosContacto = "";
	            		if (null != ped.getCcostos().getTelefono())
	            			datosContacto=ped.getCcostos().getTelefono().toString();
	            		bw.write(StringFormatUtil.rightComplete(datosContacto, 12, '0'));
	            		bw.write(";");
	            		datosContacto = ped.getCcostos().getContacto();
	            		if (null == datosContacto)
	            			datosContacto="";
	            		bw.write(StringFormatUtil.rightComplete(datosContacto, 30, ' '));
	            		bw.write(";");
	            		//FIN VERIFICACION
	            		bw.write(StringFormatUtil.leftComplete(ped.getId().toString(), 8, '0'));
	            		bw.write(";");
	            		bw.write(DateUtil.format("dd/MM/yyyy",ped.getFe_tratamiento()));
	            		bw.write(";");
	            		bw.write(DateUtil.format("dd/MM/yyyy",ped.getFechaAlta()));
	            		bw.write(";");
	            		cantPedidos++;
	            	}
	            	bw.newLine();
	            	bw.write("T");
	            	bw.write(";");
	            	bw.write(StringFormatUtil.leftComplete(cantPedidos.toString(), 7, '0'));
	            	bw.write(";");
            		bw.write(StringFormatUtil.rightComplete("", 307, ' '));
            		bw.write(";");
            		fileName += "_" + StringFormatUtil.leftComplete(pedidos.get(0).getNum_tratamiento().toString(), 6, '0') + "_" + DateUtil.format("ddMMyyyy", pedidos.get(0).getFe_tratamiento());;
            		if (numArch == null){
            			rubro.setNro_sec_tratamiento(rubro.getNro_sec_tratamiento() + 1);
            			rubroService.updateRubro(rubro, rubro.getUsuarioMod());
            		}
	            }//if
	            }//try
	            finally{
	            	if (bw != null){
	                	response.setContentType("text/plain; charset=UTF-8");
	                	response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ".txt");
	                	response.setCharacterEncoding("UTF-8");
	                	bw.close();
	                	out.print(out);
	                	out.flush();
	                	out.close();
	            	}
	            }
	}*/
	
	@SuppressWarnings({"rawtypes", "unchecked"})
	@RequestMapping(method = RequestMethod.GET, value = "/getOldFiles")
	public ResponseEntity<List<FileNameAndSecuence>> listPending(
			@RequestParam(value = "rubro", required = true) Long rubro,
			@RequestParam(value = "zona", required = true) Integer zona) {
		List<FileNameAndSecuence> response = new ArrayList<FileNameAndSecuence>();
			List<Long> ccostos = new ArrayList<Long>();
			for (CentroCostos cc : ccService.findOfZona(zona)){
				ccostos.add(cc.getId());
			}
			if (ccostos == null || ccostos.size() == 0)
				return new ResponseEntity(new Exception("ZONA_NO_CC"), HttpStatus.INTERNAL_SERVER_ERROR);
			response = service.getFileNameAndSecuence(ccostos, rubro);

		return new ResponseEntity<List<FileNameAndSecuence>>(response, HttpStatus.OK);
	}
}