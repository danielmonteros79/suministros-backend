package com.sa.su.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.sa.su.model.Favorito;

@Repository("favoritoDao")
public class FavoritolDaoImpl extends GenericDaoImpl<String, Favorito> implements FavoritoDao {

	@SuppressWarnings("unchecked")
	public List<Favorito> findAll(String legajo) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.ilike("legajo", legajo.trim(), MatchMode.EXACT));

		return (List<Favorito>) criteria.list();
	}

	public void add(Favorito favorito) {
		create(favorito);
	}

	public void delete(Favorito favorito) {
		super.delete(favorito);
	}
}
