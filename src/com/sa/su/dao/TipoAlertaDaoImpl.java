package com.sa.su.dao;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Property;
import org.springframework.stereotype.Repository;

import com.sa.su.model.TipoAlerta;
@Repository("tipoAlertaDao")
public class TipoAlertaDaoImpl extends GenericDaoImpl<Long, TipoAlerta> implements TipoAlertaDao {
	
	@SuppressWarnings("unchecked")
    public List<TipoAlerta> findAll() {
        Criteria criteria = createEntityCriteria();
        criteria.addOrder(Property.forName("descripcion").asc());
        return (List<TipoAlerta>) criteria.list();
    }
	
}