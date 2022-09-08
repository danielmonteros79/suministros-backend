package com.sa.su.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.sa.su.model.TipoMaterial;

@Repository("tipoActivoDao")
public class TipoMaterialDaoImpl extends GenericDaoImpl<Long, TipoMaterial> implements TipoMaterialDao  {
	
	@Override
	@SuppressWarnings("unchecked")
	public List<TipoMaterial> findAll() {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.isNull("fechaBaja"));
        criteria.add(Restrictions.isNull("usuarioBaja"));
        return (List<TipoMaterial>) criteria.list();
    }
	@Override
	@SuppressWarnings("unchecked")
	public List<TipoMaterial> findFilteredList(List<Long> id){
		Criteria criteria = createEntityCriteria();		
        criteria.add(Restrictions.isNull("fechaBaja"));
        criteria.add(Restrictions.isNull("usuarioBaja"));
		if (null != id && 0 != id.size())
			criteria.add(Restrictions.in("id", id));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return (List<TipoMaterial>) criteria.list();
	}
	@Override
	public void save(TipoMaterial tipoMaterial, String usrSv){
		TipoMaterial newEntity = new TipoMaterial();
		newEntity.setDescripcion(tipoMaterial.getDescripcion());
		newEntity.setFechaAlta(new Date());
		newEntity.setUsuarioAlta(usrSv);
		update(newEntity);
	}
	@Override
	public void deleteTipoMaterial(TipoMaterial tipoMaterial, String usrDlt){
		TipoMaterial delEntity = (TipoMaterial) getSession().get(TipoMaterial.class, tipoMaterial.getId());
		delEntity.setFechaBaja(new Date());
		delEntity.setUsuarioBaja(usrDlt);
		update(delEntity);
	}
	@Override
	public Long update(TipoMaterial tipoMaterial, String userMod) {
		// TODO Auto-generated method stub
		Long id = tipoMaterial.getId();
		TipoMaterial updEntity = (TipoMaterial) getSession().get(TipoMaterial.class, id);
		updEntity.setDescripcion(tipoMaterial.getDescripcion());
		updEntity.setFechaMod(new Date());
		updEntity.setUsuarioMod(userMod);
		update(updEntity);
		return id;
	}
}
