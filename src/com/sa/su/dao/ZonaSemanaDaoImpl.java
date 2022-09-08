package com.sa.su.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.sa.su.model.Zona;
import com.sa.su.model.ZonaSemana;
@Repository("zonaSemanaDao")
public class ZonaSemanaDaoImpl extends GenericDaoImpl<Integer, ZonaSemana> implements ZonaSemanaDao{
	@SuppressWarnings("unchecked")
	@Override
	public List<Zona> findZonaOfSemana(Integer semana) {
		Criteria criteria = createEntityCriteria()
				.setProjection(Projections.distinct(Projections.projectionList()
																	            .add(Projections.property("id_zona"))));
		criteria.add(Restrictions.eq("semana", semana));
		
		return (List<Zona>) criteria.list();
	}
}
