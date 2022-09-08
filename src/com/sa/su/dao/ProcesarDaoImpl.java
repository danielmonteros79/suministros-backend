package com.sa.su.dao;

import java.util.Arrays;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import com.sa.su.enums.Estado;
import com.sa.su.enums.Validacion;
import com.sa.su.model.Pedido;

@Repository("procesarDao")
public class ProcesarDaoImpl extends GenericDaoImpl<String, Pedido> implements ProcesarDao {
	@SuppressWarnings("unchecked")
	public List<Pedido> findAProcesar(List<Long> centrosCosto) {
		Criteria criteria = createEntityCriteria();
		
		criteria.setFetchMode("observaciones", FetchMode.JOIN);
		
		criteria.add(Restrictions.in("estado.id", Arrays.asList(Estado.PENDIENTE.getId(), Estado.VALIDACION.getId())));
		
		if (centrosCosto != null && centrosCosto.size() != 0)
			criteria.add(Restrictions.in("ccostos.id", centrosCosto));
		
		criteria.add(Restrictions.and(
				Restrictions.isNotNull("validacion.id"), 
				Restrictions.not(Restrictions.eq("validacion.id", Validacion.OK.getId()))));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return (List<Pedido>) criteria.list();
	}
}
