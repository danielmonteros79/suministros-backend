package com.sa.su.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.sa.su.model.Rubro;

@Repository("rubroDao")
public class RubroDaoImpl extends GenericDaoImpl<Long, Rubro> implements RubroDao  {
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Rubro> findAll() {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.isNull("fechaBaja"));
        criteria.add(Restrictions.isNull("usuarioBaja"));
        return (List<Rubro>) criteria.list();
    }
	@Override
	@SuppressWarnings("unchecked")
	public List<Rubro> findFilteredList(List<Long> id){
		Criteria criteria = createEntityCriteria();
	        criteria.add(Restrictions.isNull("fechaBaja"));
	        criteria.add(Restrictions.isNull("usuarioBaja"));
		if (null != id && 0 != id.size())
			criteria.add(Restrictions.in("id", id));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return (List<Rubro>) criteria.list();
	}
	@Override
	public void save(Rubro rubro, String usrSv){
		Rubro newEntity = new Rubro();
		newEntity.setDescripcion(rubro.getDescripcion());
		newEntity.setFechaAlta(new Date());
		newEntity.setUsuarioAlta(usrSv);
		newEntity.setNro_sec_tratamiento(1L);
		newEntity.setNro_interfaz(rubro.getNro_interfaz());
		update(newEntity);
	}
	@Override
	public Long deleteRubro(Rubro rubro, String usrDlt){
		Rubro delEntity = (Rubro) getSession().get(Rubro.class, rubro.getId());
		delEntity.setFechaBaja(new Date());
		delEntity.setUsuarioBaja(usrDlt);
		update(delEntity);
		return 0L;
	}
	@Override
	public Long update(Rubro rubro, String userMod) {
		// TODO Auto-generated method stub
		Long id = rubro.getId();
		Rubro updEntity = (Rubro) getSession().get(Rubro.class, id);
		updEntity.setDescripcion(rubro.getDescripcion());
		updEntity.setFechaMod(new Date());
		updEntity.setUsuarioMod(userMod);
		updEntity.setNro_interfaz(rubro.getNro_interfaz());
		updEntity.setNro_sec_tratamiento(rubro.getNro_sec_tratamiento());
		update(updEntity);
		return id;
	}
	
}
