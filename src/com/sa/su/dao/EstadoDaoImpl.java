package com.sa.su.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.sa.su.model.Estado;

@Repository("estadoDao")
public class EstadoDaoImpl extends GenericDaoImpl<String, Estado> implements EstadoDao {
	
	@SuppressWarnings("unchecked")
    public List<Estado> findAll() {
        Criteria criteria = createEntityCriteria().addOrder(Order.asc("id"));
        criteria.add(Restrictions.isNull("fechaBaja"));
        criteria.add(Restrictions.isNull("usuarioBaja"));
        return (List<Estado>) criteria.list();
    }
	@SuppressWarnings("unchecked")
	@Override
	public List<Estado> findFiltered(List<Long> estadoList) {
        Criteria criteria = createEntityCriteria();
//		if (null != estadoList && 0 != estadoList.size())
			criteria.add(Restrictions.in("id", estadoList));
	        criteria.add(Restrictions.isNull("fechaBaja"));
	        criteria.add(Restrictions.isNull("usuarioBaja"));
        return (List<Estado>) criteria.list();
	}

}
