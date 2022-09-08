package com.sa.su.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.sa.su.model.Validacion;

@Repository("validacionDao")
public class ValidacionDaoImpl extends GenericDaoImpl<String, Validacion> implements ValidacionDao {
	
	@SuppressWarnings("unchecked")
    public List<Validacion> findAll() {
        Criteria criteria = createEntityCriteria().addOrder(Order.asc("id"));
        criteria.add(Restrictions.isNull("fechaBaja"));
        criteria.add(Restrictions.isNull("usuarioBaja"));
        return (List<Validacion>) criteria.list();
    }


}
