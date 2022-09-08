package com.sa.su.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.sa.su.model.Alerta;

@Repository("alertaDao")
@SuppressWarnings("all")
public class AlertaDaoImpl extends GenericDaoImpl<Long, Alerta> implements AlertaDao {

	@Override
    public List<Alerta> findAll(List<Long> material, List<String> tipoAlertaPeriodo, List<Long> centroCostos) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.isNull("fechaBaja"));
        criteria.add(Restrictions.isNull("usuarioBaja"));
        if (material != null && !material.isEmpty())
			criteria.add(Restrictions.in("material.id", material));

		if (centroCostos != null && !centroCostos.isEmpty())
			criteria.add(Restrictions.in("centroCostos.id", centroCostos));
		
		if (tipoAlertaPeriodo != null && !tipoAlertaPeriodo.isEmpty()) {
			Disjunction disj = Restrictions.disjunction();
			for (String tap : tipoAlertaPeriodo) {
				disj.add(Restrictions.and(
					Restrictions.eq("tipoAlerta.id", new Integer(tap.substring(0, tap.indexOf("_")))),
					Restrictions.eq("periodo.id", new Integer(tap.substring(tap.indexOf("_") + 1)))));
			}
			criteria.add(disj);
		}
        return (List<Alerta>) criteria.list();
    }

	@Override
	public List<Alerta> getAlertasMaterialCentroCostos(Long material, Long cc) {
		Criteria criteria = createEntityCriteria()
			.add(Restrictions.isNull("fechaBaja"))
        	.add(Restrictions.isNull("usuarioBaja"))
        	.add(Restrictions.eq("material.id", material))
        	.add(Restrictions.eq("centroCostos.id", cc));
		return (List<Alerta>) criteria.list();
	}
}
