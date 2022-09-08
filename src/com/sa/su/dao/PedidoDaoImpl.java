package com.sa.su.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.sa.su.model.CantidadMaterial;
import com.sa.su.model.CentroCostos;
import com.sa.su.model.Estado;
import com.sa.su.model.FileNameAndSecuence;
import com.sa.su.model.Observacion;
import com.sa.su.model.Pedido;
import com.sa.su.model.Periodo;
import com.sa.su.model.Rubro;
import com.sa.su.model.Validacion;
import com.sa.su.service.AlertaService;
import com.sa.su.service.MaterialService;
import com.sa.su.service.MovimientoService;
import com.sa.su.service.ValidacionService;
import com.sa.su.util.DateUtil;
import com.sa.su.util.StringFormatUtil;

@Repository("pedidoDao")
public class PedidoDaoImpl extends GenericDaoImpl<String, Pedido> implements PedidoDao {
	
	@Autowired
	private MaterialService mservice;
	@Autowired
	private AlertaService aservice;
	@Autowired
	private ValidacionService vservice;
	@Autowired
	private MovimientoService moservice;
	
	private static List<Validacion> validList = null;
	
	public Pedido getById(Long id) {
		Pedido pedido = new Pedido();
		pedido.setId(3L);

		return pedido;
	}
	@SuppressWarnings("unchecked")
	public List<Pedido> findFilteredListPendientes(List<Long> material, List<Long> ccostos, List<Long> prioridad, Date feDesde,
			Date feHasta, List<Long> estado, Boolean validados) {
		Criteria criteria = createEntityCriteria();
		generateCommonFilters(criteria, material, ccostos, prioridad, estado);
		
		if (null != feDesde)
			criteria.add(Restrictions.ge("fe_solicitud", feDesde));

		if (null != feHasta)
			criteria.add(Restrictions.le("fe_solicitud", feHasta));
		
		if (null != validados)
			if (validados)
				criteria.add(Restrictions.isNotNull("validacion.id"));
			else
				criteria.add(Restrictions.isNull("validacion.id"));
		// generateSortOrder(order, orderBy, criteria);

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return (List<Pedido>) criteria.list();
	}
	@SuppressWarnings("unchecked")
	public List<Pedido> findFilteredListTratados(List<Long> material, List<Long> ccostos, List<Long>prioridad, Date feDesde,
			Date feHasta, List<Long> estado) {
		Criteria criteria = createEntityCriteria();

		generateCommonFilters(criteria, material, ccostos, prioridad, estado);
		if (null != feDesde)
			criteria.add(Restrictions.ge("fe_solicitud", feDesde));

		if (null != feHasta)
			criteria.add(Restrictions.le("fe_solicitud", feHasta));
		
		// generateSortOrder(order, orderBy, criteria);

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return (List<Pedido>) criteria.list();
	}
	@SuppressWarnings("unchecked")
	public List<Pedido> findFilteredListResueltos(List<Long> material, List<Long> ccostos, List<Long> prioridad, Date feDesde,
			Date feHasta, List<Long> estado) {
		Criteria criteria = createEntityCriteria();

		generateCommonFilters(criteria, material, ccostos, prioridad, estado);
		
		if (null != feDesde)
			criteria.add(Restrictions.ge("fe_recepcion", feDesde));

		if (null != feHasta)
			criteria.add(Restrictions.le("fe_recepcion", feHasta));
		
		 generateSortOrder("fe_recepcion", "-1", criteria);

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		return (List<Pedido>) criteria.list();
	}

	private void generateCommonFilters(Criteria criteria, List<Long> material, List<Long> ccostos, List<Long> prioridad,
			 List<Long> estado) {
		criteria.setFetchMode("observaciones", FetchMode.JOIN);
		if (null != ccostos && 0 != ccostos.size())
			criteria.add(Restrictions.in("ccostos.id", ccostos));
		if (null != prioridad && 0 != prioridad.size())
			criteria.add(Restrictions.in("prioridad.id", prioridad));
		if (null != estado && 0 != estado.size())
			criteria.add(Restrictions.in("estado.id", estado));
		if (null != material && 0 != material.size())
			criteria.add(Restrictions.in("material.id", material));

	}

	public Long save(Pedido pedido, String usuarioAlta, Long ccostos) throws Exception {
		Pedido newEntity = new Pedido();
		setDataForInsert(newEntity, pedido, usuarioAlta, ccostos);
		update(newEntity);
		return newEntity.getId();
	}

	
//MARCO revisar
	@Override
	public Long update(Pedido pedido, String usuario) throws Exception {
		// TODO Auto-generated method stub
		Long id = pedido.getId();
		Pedido updEntity = (Pedido) getSession().get(Pedido.class, id);
		setDataForUpdate(updEntity, pedido, usuario);
		update(updEntity);	//updateo stock solo si fue aprobado.
		if (updEntity.getEstado().getId() == 3){ // cant +, la necesito -
					mservice.updateStock(updEntity.getMaterial(), -1 *updEntity.getCant_aprobada(), usuario); //ver de donde viene este usuario
					//guardo movimientos de stock en tabla de movimientos (SUM_MOVIMIENTOS)
					moservice.saveMovimiento(updEntity, usuario, "PEDIDO");//
		}
		return id;
	}


	@Override
	public Long delete(Long pedido) {
		
		Pedido delEntity = (Pedido) getSession().get(Pedido.class, pedido);
		delete(delEntity);		
		return pedido;
	}

	@Override
	public Long recibir(Pedido pedido, String usuario) {
		// TODO Auto-generated method stub
		Long id = pedido.getId();
		Pedido updEntity = (Pedido) getSession().get(Pedido.class, id);
		setDataForProcesar(updEntity, pedido, usuario);
		update(updEntity);
		
		Long cantidad = pedido.getCant_aprobada();
			cantidad -= updEntity.getCantidad_recepcion();
			mservice.updateStock(updEntity.getMaterial(), cantidad, usuario);
		return id;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<CantidadMaterial> sumAmountByPendingMaterial(){
//	    ESTADO 1 = pendiente , 2 VALIDANDO
		Long[] status = {1L, 2L};
		Criteria criteria = 
				createEntityCriteria()
			        .add(Restrictions.in("estado.id", status))
			        .setProjection(Projections.projectionList()
			            .add(Projections.property("material.id"), "material")
			            .add(Projections.sum("cantidad").as("cantidad"))
			            .add(Projections.groupProperty("material.id")))
			        .setResultTransformer(Transformers.aliasToBean(CantidadMaterial.class));
			        
			        
			return (List<CantidadMaterial>) criteria.list();

	}
	private void setDataForInsert(Pedido newEntity, Pedido pedido, String usuarioAlta, Long ccostos) throws Exception {
		newEntity.setCantidad(pedido.getCantidad());
		newEntity.setCant_aprobada(0L);
		CentroCostos cc = new CentroCostos();
		cc.setId(ccostos);
		newEntity.setCcostos(cc);
		Estado newEstado = new Estado();
		newEstado.setId(1L);
		newEntity.setEstado(newEstado);
		newEntity.setFe_solicitud(new Date());
		newEntity.setMaterial(pedido.getMaterial());
		newEntity.setPrioridad(pedido.getPrioridad());
		newEntity.setFechaAlta(new Date());
		newEntity.setUsuarioAlta(usuarioAlta);
		for (Observacion obs : pedido.getObservaciones()) {
			obs.setId_pedido(newEntity);
		}
		newEntity.setObservaciones(pedido.getObservaciones());
		if (aservice.validarPedidoAlerta(newEntity))
		{//SI ENTRA COLOCAR ESTADO 2
			loadValidaciones();
			newEntity.setValidacion(validList.get(1));
		//	newEstado.setId(2L);
		}
		}
	
	private void setDataForUpdate(Pedido updEntity, Pedido pedido, String usuario) throws Exception {
		
		if(null != pedido.getEstado() && null != pedido.getEstado().getId()){
			Estado estado = new Estado();
			estado.setId(pedido.getEstado().getId());
			updEntity.setEstado(estado);			
		}
		if(pedido.getCant_aprobada() != null)
			updEntity.setCant_aprobada(pedido.getCant_aprobada());	
		
		updEntity.setCantidad(pedido.getCantidad());
		updEntity.setPrioridad(pedido.getPrioridad());
		updEntity.setFechaMod(new Date());
//		updEntity.setFe_tratamiento(new Date());
		updEntity.setUsuarioMod(usuario);
		for (Observacion obs : pedido.getObservaciones()) {
			obs.setId_pedido(updEntity);
		}
		updEntity.setObservaciones(pedido.getObservaciones());
		if (aservice.validarPedidoAlerta(updEntity)) {
			loadValidaciones();
			updEntity.setValidacion(validList.get(1));
		} else updEntity.setValidacion(null);
	}
	//No confundir con el procesar de generar un archivo..
	private void setDataForProcesar(Pedido updEntity, Pedido pedido, String usuario) {
		Estado estado = new Estado();
//		estado.setId(pedido.getEstado().getId());
		//solo recibido?
		estado.setId(6L); 
		updEntity.setEstado(estado);
		updEntity.setCantidad_recepcion(pedido.getCantidad_recepcion());
		updEntity.setFe_recepcion(new Date());
		updEntity.setCod_usuario_recepcion(usuario);
		for (Observacion obs : pedido.getObservaciones()) {
			obs.setId_pedido(updEntity);
		}
		updEntity.setObservaciones(pedido.getObservaciones());
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Pedido> getLastOrdered(String ivUser){
		Date lastDate = lastDate(ivUser);
		if (lastDate != null){
		Criteria criteria = 
				createEntityCriteria()
				.add(Restrictions.eq("usuarioAlta",ivUser))
				.add(Restrictions.ne("estado.id", Long.valueOf(1)))
				.add(Restrictions.eq("fe_solicitud", lastDate));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return (List<Pedido>) criteria.list();
		}
		return new ArrayList<Pedido>();
	}
	private Date lastDate(String ivUser){
		Criteria criteria = 
				createEntityCriteria()
				.add(Restrictions.eq("usuarioAlta", ivUser))
				.add(Restrictions.ne("estado.id", Long.valueOf(1)))
				.setProjection(Projections.max("fe_solicitud"));
		return (Date) criteria.uniqueResult();
	}
	@Override
	public List<Pedido> processFile(List<Long> ccostos, Rubro rubro){
		List<Pedido> toProcess = searchToProcess(ccostos, rubro.getId());
		for (Pedido pedProc : toProcess){
			Pedido updEntity = (Pedido) getSession().get(Pedido.class, pedProc.getId());
			Estado estado = new Estado();
			estado.setId(5L);
			updEntity.setNum_tratamiento(rubro.getNro_sec_tratamiento());
			updEntity.setEstado(estado);
			updEntity.setFe_tratamiento(new Date());
			update(updEntity);
		}
		return toProcess;
	}
	
	@SuppressWarnings("unchecked")
	private List<Pedido> searchToProcess(List<Long> ccostos, Long rubro){
	List<Long> estado = new ArrayList<Long>();
//	PROCESAR ARCHIVO!
//		estado.add(6L); //recibido
		estado.add(3L); //aprobado
		Criteria criteria =
				createEntityCriteria()
				.add(Restrictions.in("estado.id", estado))
				.add(Restrictions.isNull("fe_tratamiento"))
				.add(Restrictions.isNull("num_tratamiento"))
				.createAlias("material", "material");
		if (ccostos != null && ccostos.size() != 0)
			criteria.add(Restrictions.in("ccostos.id", ccostos));
		if (rubro != null)
			criteria.add(Restrictions.eq("material.rubro.id", rubro));
		generateSortOrder("id", "1", criteria);
			criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return (List<Pedido>) criteria.list();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Pedido> procesadosNum(Long num_trat){
	List<Long> estado = new ArrayList<Long>();
//	PROCESAR ARCHIVO!
		estado.add(6L); //recibido
		estado.add(5L); //TRATADO
		Criteria criteria =
				createEntityCriteria()
				.add(Restrictions.in("estado.id", estado))
				.add(Restrictions.isNotNull("fe_tratamiento"))
				.add(Restrictions.eq("num_tratamiento", num_trat));
		generateSortOrder("id", "1", criteria);
			criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return (List<Pedido>) criteria.list();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<FileNameAndSecuence>getFileNameAndSecuence(List<Long> ccostos, Long rubro){
		Criteria criteria = 
				createEntityCriteria()
				.createAlias("material", "material")
				.setProjection(Projections.distinct(Projections.projectionList()
																	            .add(Projections.property("num_tratamiento"))
																	            .add(Projections.property("fe_tratamiento"))))
				.add(Restrictions.isNotNull("num_tratamiento"))
				.add(Restrictions.isNotNull("fe_tratamiento"))
				.add(Restrictions.in("ccostos.id", ccostos))
				.add(Restrictions.eq("material.rubro.id", rubro))											//sumo 1 dia ya que sino es hasta la fecha con timestamp 00:00:00.
																											//se podria hacer Restrictions.ge y Restrictions.le
				.add(Restrictions.between("fe_tratamiento", new DateTime().minusMonths(3).toDate() , new DateTime().plusDays(1).toDate()));
		generateSortOrder("num_tratamiento", "-1", criteria);
		List<FileNameAndSecuence> response = new ArrayList<FileNameAndSecuence>();
		List<Object[]> rs = criteria.list();
		for (Object[] row : rs){                               //EN LA POSICION 0 SIEMPRE DEVUELVE EL NRO DE TRATAMIENTO, Y EN LA POSICION 1 LA FECHA DE TRATAMIENTO.
															  // HAY QUE CASTEARLO, YA QUE TIENE OBJECTS ADENTRO, NO DATES, STRINGS O LONGS!!
			response.add(new FileNameAndSecuence( StringFormatUtil.leftComplete(row[0].toString(), 6, '0') + " (" + DateUtil.format("dd/MM/yyyy",(Date) row[1]) + ")", (Long) row[0]));
		}
		return response;
	}
	private void loadValidaciones() {
		if (validList == null)
			validList = vservice.findAll();
	}
	
	@Override
	public Long getSumCantidadMaterialPedidosRecibidosPeriodoByCCMinusPedido(Long material, Long cc, Periodo periodo, Long pedido) throws Exception {
		Date startDate = DateUtil.getStartDatePeriodoActual(periodo);
		Date endDate = DateUtil.getEndDatePeriodoActual(periodo);
		Criteria criteria =
				createEntityCriteria()
				.setProjection(Projections.projectionList()
						.add(Projections.sum("cantidad_recepcion")))
				.add(Restrictions.between("fe_solicitud", startDate, endDate))
				.add(Restrictions.isNull("fechaBaja"))
				.add(Restrictions.isNull("usuarioBaja"));
		Long cantPed = getSumMaterialPedidoPeriodoByCC(material, cc, startDate, endDate, pedido), cantRec = 0L;
		Object rs = criteria.uniqueResult();
		if (rs != null)
			cantRec = Long.parseLong(rs.toString());
		return (cantPed + cantRec);
	}
	
	// No suma los recibidos, solo lo que pidio el centro de costos.
	private Long getSumMaterialPedidoPeriodoByCC(Long material, Long cc, Date startDate, Date endDate, Long pedido) {
		Criteria criteria = createEntityCriteria()
				.setProjection(Projections.sum("cantidad"))
				.add(Restrictions.between("fe_solicitud", startDate, endDate))
				.add(Restrictions.isNull("fechaBaja"))
				.add(Restrictions.isNull("usuarioBaja"))
				.add(Restrictions.isNull("cantidad_recepcion"))
				.add(Restrictions.isNull("fe_recepcion"))
				.add(Restrictions.isNull("cod_usuario_recepcion"));
		if (pedido != null)
			criteria.add(Restrictions.ne("id", pedido));
		Object rs = criteria.uniqueResult();
		Long res = 0L;
		if (rs != null)
			res = Long.parseLong(rs.toString());
		return res;
	}
}