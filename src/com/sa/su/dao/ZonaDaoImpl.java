package com.sa.su.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import com.sa.su.model.Zona;

@Repository("zonaDao")
public class ZonaDaoImpl extends GenericDaoImpl<Long, Zona> implements ZonaDao {
	@Override
	@SuppressWarnings("unchecked")
	public List<Zona> findAll() {
        Criteria criteria = createEntityCriteria();
        return (List<Zona>) criteria.list();
    }
}
