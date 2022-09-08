package com.sa.su.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.sa.su.model.Justificacion;
@Repository("justificacionesDao")
public class JustificacionDaoImpl extends GenericDaoImpl<Long, Justificacion> implements JustificacionDao{

	@Override
	@SuppressWarnings("unchecked")
	public List<Justificacion> findAll() {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.isNull("fechaBaja"));
        criteria.add(Restrictions.isNull("usuarioBaja"));
        return (List<Justificacion>) criteria.list();
    }
	@Override
	@SuppressWarnings("unchecked")
	public List<Justificacion> findFilteredList(List<Long> id){
		Criteria criteria = createEntityCriteria();		
        criteria.add(Restrictions.isNull("fechaBaja"));
        criteria.add(Restrictions.isNull("usuarioBaja"));
		if (null != id && 0 != id.size())
			criteria.add(Restrictions.in("id", id));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return (List<Justificacion>) criteria.list();
	}
	@Override
	public void save(Justificacion justificacion, String usrSv){
		Justificacion newEntity = new Justificacion();
		newEntity.setDescripcion(justificacion.getDescripcion());
		newEntity.setFechaAlta(new Date());
		newEntity.setUsuarioAlta(usrSv);
		update(newEntity);
	}
	@Override
	public void deleteJustificacion(Justificacion justificacion, String usrDlt){
		Justificacion delEntity = (Justificacion) getSession().get(Justificacion.class, justificacion.getId());
		delEntity.setFechaBaja(new Date());
		delEntity.setUsuarioBaja(usrDlt);
		update(delEntity);
	}
	@Override
	public Long update(Justificacion justificacion, String userMod) {
		// TODO Auto-generated method stub
		Long id = justificacion.getId();
		Justificacion updEntity = (Justificacion) getSession().get(Justificacion.class, id);
		updEntity.setDescripcion(justificacion.getDescripcion());
		updEntity.setFechaMod(new Date());
		updEntity.setUsuarioMod(userMod);
		update(updEntity);
		return id;
	}
}
