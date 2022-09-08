package com.sa.su.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Property;
import org.springframework.stereotype.Repository;

import com.sa.su.model.Periodo;
@Repository("PeriodoDao")
public class PeriodoDaoImpl extends GenericDaoImpl<Long, Periodo> implements PeriodoDao {
	
	@SuppressWarnings("unchecked")
    public List<Periodo> findAll() {
        Criteria criteria = createEntityCriteria();
        criteria.addOrder(Property.forName("id").asc());
        return (List<Periodo>) criteria.list();
    }
}
