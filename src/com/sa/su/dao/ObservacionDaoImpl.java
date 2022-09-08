package com.sa.su.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.sa.su.model.Observacion;
@Repository("observacionesDao")
public class ObservacionDaoImpl extends GenericDaoImpl<Long, Observacion> implements ObservacionDao {
	@Override
	public void save(Observacion observacion){
		Observacion newEntity =	search(observacion.getId_pedido().getId(), observacion.getOperacion());
		newEntity.setDescripcion(observacion.getDescripcion());
		newEntity.setId_justificacion(observacion.getId_justificacion());
		newEntity.setId_pedido(observacion.getId_pedido());
		newEntity.setOperacion(observacion.getOperacion());
		update(newEntity);
	}
	private Observacion search(Long id_ped, String operacion){
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("operacion", operacion))
				.add(Restrictions.eq("id_pedido.id", id_ped));
		criteria.uniqueResult();
		Observacion res = (Observacion) criteria.list();
			if (res == null) res = new Observacion();
		return res;
	}
}
