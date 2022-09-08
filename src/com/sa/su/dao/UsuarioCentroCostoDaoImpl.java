package com.sa.su.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.sa.su.model.UsuarioCentroCosto;

@Repository("usuarioCentroCostoDao")
public class UsuarioCentroCostoDaoImpl extends GenericDaoImpl<String, UsuarioCentroCosto> implements UsuarioCentroCostoDao {

	public void save(UsuarioCentroCosto usuarioCC){
		update(usuarioCC);
	}
	public void saveBatch(UsuarioCentroCosto usuarioCC){
		if (! (usuarioCC.getCentroCostos() == null || usuarioCC.getLegajo() == null) ) save(usuarioCC);
	}
	
	public void deleteUsrCC(UsuarioCentroCosto userCC){
		UsuarioCentroCosto delEntity = (UsuarioCentroCosto) getSession().get(UsuarioCentroCosto.class, userCC);
		delete(delEntity);
	}
	public void deleteUsrCCBatch(UsuarioCentroCosto userCC){
		UsuarioCentroCosto delEntity = (UsuarioCentroCosto) getSession().get(UsuarioCentroCosto.class, userCC);
		if (!(delEntity == null)) delete(delEntity);
	}
	@SuppressWarnings("unchecked")
	public List<UsuarioCentroCosto> findFilteredList(String legajo, List<Long> cc){
		Criteria criteria = createEntityCriteria();		
		if (null != legajo && !legajo.trim().equals(""))
			criteria.add(Restrictions.ilike("legajo", legajo.trim(), MatchMode.ANYWHERE));

		if (null != cc && cc.size() != 0)
			criteria.add(Restrictions.in("centroCostos.id", cc));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return (List<UsuarioCentroCosto>) criteria.list();
	}
	
}
