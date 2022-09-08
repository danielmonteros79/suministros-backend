package com.sa.su.dao;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.sa.su.configuration.HibernateConfiguration;
import com.sa.su.enums.Estado;
import com.sa.su.model.CentroCostos;
import com.sa.su.model.ConsumoMaterial;
import com.sa.su.model.Material;
import com.sa.su.model.Pedido;
import com.sa.su.model.Usuario;
import com.sa.su.service.MovimientoService;
import com.sa.su.service.UsuarioService;

@Repository("materialDao")
public class MaterialDaoImpl extends GenericDaoImpl<Long, Material> implements MaterialDao {
	
	Logger log = Logger.getLogger(HibernateConfiguration.class);
	
	@Autowired
	private MovimientoService moservice;
	
	@Autowired
	UsuarioService usuarioService;
	
	@SuppressWarnings("unchecked")
    public List<Material> findAll() {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.isNull("fechaBaja"));
        criteria.add(Restrictions.isNull("usuarioBaja"));
        return (List<Material>) criteria.list();
    }

	public Material getById(long id) {
		Long idLong = id;
		return (Material) getSession().get(Material.class, idLong);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Material> findForPedido(Long ccostos) {
        Criteria criteria = createEntityCriteria();
		DetachedCriteria subquery = DetachedCriteria.forClass(Pedido.class);
		DetachedCriteria subqueryConsumoMaterial = DetachedCriteria.forClass(ConsumoMaterial.class);
		DetachedCriteria subqueryCCostos = DetachedCriteria.forClass(CentroCostos.class);
		
		if (null != ccostos && 0 != ccostos){
			
			//SUM_CENTRO_COSTOS subquery
			subqueryCCostos.add(Restrictions.eq("id", ccostos)).setProjection(Projections.property("consumo.id"));
			//SUM_COMSUMO_MATERIAL subquery
			subqueryConsumoMaterial.add(Subqueries.propertyIn("consumo.id", subqueryCCostos));
			subqueryConsumoMaterial.setProjection(Projections.property("material.id"));
			subqueryConsumoMaterial.add(Restrictions.gt("cant_max_pedido", 0L));// mayor que 0
			//SUM_PEDIDO subquery
			subquery.add(Restrictions.eq("ccostos.id", ccostos)).setProjection(Projections.property("material.id"));
			subquery.add(Restrictions.in("estado.id", Arrays.asList(Estado.PENDIENTE.getId(), Estado.VALIDACION.getId()))).setProjection(Projections.property("material.id"));
			//SUM_MATERIAL query
			criteria.add(Subqueries.propertyIn("id", subqueryConsumoMaterial)); //select in
			criteria.add(Restrictions.gt("cantidad_stock", 0L));// mayor que 0
			criteria.add(Restrictions.not(Subqueries.propertyIn("id",subquery )));// not in
			criteria.add(Restrictions.isNull("fechaBaja"));
			criteria.add(Restrictions.isNull("usuarioBaja"));
		}
        
        return (List<Material>) criteria.list();
	}
	
	@Override
	public void updateStock(Material material, Long cantidadASumar, String usuario){
		if (null == usuario)//entra por batch
			setDataForUpdateStock(material, cantidadASumar);
		else setDataForUpdate(material, cantidadASumar, usuario);
		update(material);
		
	}
	
	private void setDataForUpdate(Material updEntity, Long cantidadASumar, String usuario) {
		setDataForUpdateStock(updEntity, cantidadASumar);
		updEntity.setFechaMod(new Date());
		updEntity.setUsuarioMod(usuario);
	}
	private void setDataForUpdateStock(Material updEntity, Long cantidadASumar) {
		
		updEntity.setCantidad_stock((cantidadASumar + updEntity.getCantidad_stock()));
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<Material> findOfRubro(List<Long> idRubros){
		Criteria criteria = createEntityCriteria();
		if (null != idRubros && 0 != idRubros.size())
			criteria.add(Restrictions.in("rubro.id", idRubros));
		criteria.add(Restrictions.isNull("fechaBaja"));
		criteria.add(Restrictions.isNull("usuarioBaja"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return (List<Material>) criteria.list();
	}
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Material> findUrgente(List<String> urgente){
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.isNull("fechaBaja"));
		criteria.add(Restrictions.isNull("usuarioBaja"));
		if (null != urgente)
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return (List<Material>) criteria.list();
	}
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Material> findFilteredList(List<Long> idRubro, String mat){
		Criteria criteria = createEntityCriteria();		
        criteria.add(Restrictions.isNull("fechaBaja"));
        criteria.add(Restrictions.isNull("usuarioBaja"));
		if (null != mat && !mat.trim().equals("")){
			criteria.add(Restrictions.ilike("descripcion", mat.trim(), MatchMode.ANYWHERE));
		}
		if ( null != idRubro && 0 != idRubro.size())
			criteria.add(Restrictions.in("rubro.id", idRubro));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return (List<Material>) criteria.list();
	
	}
	@Override
	public void save(Material material, String userAlta){
		Material newEntity = new Material();
		setDataToInsert(newEntity, material, userAlta);
		update(newEntity);
		
		Usuario usuario = 	usuarioService.find(userAlta);
		moservice.saveMovimiento(newEntity, usuario, "ABM-ALTA");//guarda log en tabla de movimiento
	}
	@Override
	public Long update(Material material, String userMod){
		Long id = material.getId();
		Material updEntity = (Material) getSession().get(Material.class, id);
		setDataToUpdate(updEntity, material, userMod);
		update(updEntity);
		Usuario usuario = 	usuarioService.find(userMod);
		moservice.saveMovimiento(updEntity, usuario, "ABM-STOCK");//guarda log en tabla de movimiento
		return id;
	}
	@Override
	public void deleteMaterial(Material material, String userDlt){
		Material delEntity = (Material) getSession().get(Material.class, material.getId());
		delEntity.setFechaBaja(new Date());
		delEntity.setUsuarioBaja(userDlt);
		update(delEntity);
		Usuario usuario = 	usuarioService.find(userDlt);
		moservice.saveMovimiento(delEntity, usuario, "ABM-BAJA");//guarda log en tabla de movimiento
	}
	private void setDataToInsert(Material newEntity, Material material, String userAlta){
		newEntity.setCantidad_maxima(material.getCantidad_maxima());
		newEntity.setCantidad_media(material.getCantidad_media());
		newEntity.setCantidad_minima(material.getCantidad_minima());
		newEntity.setCantidad_stock(material.getCantidad_stock());
		newEntity.setCodigo_material_externo(material.getCodigo_material_externo());
		newEntity.setFechaAlta(new Date());
		newEntity.setPrecio_pp(material.getPrecio_pp());
		newEntity.setRubro(material.getRubro());
		newEntity.setTipo_material(material.getTipo_material());
		newEntity.setDescripcion(material.getDescripcion());
		newEntity.setUni_material(material.getUni_material());
		newEntity.setUrgente(material.getUrgente());
		newEntity.setUsuarioAlta(userAlta);
	}
	
	private void setDataToUpdate(Material updEntity, Material material, String userMod){
		if (null != material.getCantidad_maxima())
			updEntity.setCantidad_maxima(material.getCantidad_maxima());
		if (null != material.getCantidad_media())
			updEntity.setCantidad_media(material.getCantidad_media());
		if (null != material.getCantidad_minima())
			updEntity.setCantidad_minima(material.getCantidad_minima());
		if (null != material.getCantidad_stock())
			updEntity.setCantidad_stock(material.getCantidad_stock());
		if (null != material.getPrecio_pp())
			updEntity.setPrecio_pp(material.getPrecio_pp());
		if (null != material.getRubro())
			updEntity.setRubro(material.getRubro());
		if (null != material.getCodigo_material_externo() && !material.getCodigo_material_externo().trim().equals(""))
			updEntity.setCodigo_material_externo(material.getCodigo_material_externo().trim());
		if (null != material.getDescripcion() && !material.getDescripcion().equals(""))
			updEntity.setDescripcion(material.getDescripcion());
		if (null != material.getUni_material() && 0 != material.getUni_material())
				updEntity.setUni_material(material.getUni_material());
		if (null != material.getTipo_material())
			updEntity.setTipo_material(material.getTipo_material());
		if (null != material.getUrgente())
			updEntity.setUrgente(material.getUrgente());
		updEntity.setFechaMod(new Date());
		updEntity.setUsuarioMod(userMod);
	}
}
