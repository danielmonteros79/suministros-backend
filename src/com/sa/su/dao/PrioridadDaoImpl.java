package com.sa.su.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.sa.su.model.Prioridad;

@Repository("prioridadDao")
public class PrioridadDaoImpl 
extends GenericDaoImpl<String, Prioridad> implements PrioridadDao {
	
	@SuppressWarnings("unchecked")
    public List<Prioridad> findAll() {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.isNull("fechaBaja"));
        criteria.add(Restrictions.isNull("usuarioBaja"));
        return (List<Prioridad>) criteria.list();
    }
	@Override
	@SuppressWarnings("unchecked")
	public List<Prioridad> findFilteredList(List<Long> id){
		Criteria criteria = createEntityCriteria();		
        criteria.add(Restrictions.isNull("fechaBaja"));
        criteria.add(Restrictions.isNull("usuarioBaja"));
		if (null != id && 0 != id.size())
			criteria.add(Restrictions.ilike("id", id));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return (List<Prioridad>) criteria.list();
	}
	@Override
	public void save(Prioridad prioridad, String usrSv){
		Prioridad newEntity = new Prioridad();
		newEntity.setDescripcion(prioridad.getDescripcion());
		newEntity.setFechaAlta(new Date());
		newEntity.setUsuarioAlta(usrSv);
		update(newEntity);
	}
	@Override
	public void deletePrioridad(Prioridad prioridad, String usrDlt){
		Prioridad delEntity = (Prioridad) getSession().get(Prioridad.class, prioridad.getId());
		delEntity.setFechaBaja(new Date());
		delEntity.setUsuarioBaja(usrDlt);
		update(delEntity);
	}
	@Override
	public Long update(Prioridad prioridad, String userMod) {
		// TODO Auto-generated method stub
		Long id = prioridad.getId();
		Prioridad updEntity = (Prioridad) getSession().get(Prioridad.class, id);
		updEntity.setDescripcion(prioridad.getDescripcion());
		updEntity.setFechaMod(new Date());
		updEntity.setUsuarioMod(userMod);
		update(updEntity);
		return id;
	}
}
