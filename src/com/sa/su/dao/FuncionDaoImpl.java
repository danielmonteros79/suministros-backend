package com.sa.su.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import com.sa.su.model.Funcion;

@Repository("funcionDao")
public class FuncionDaoImpl extends GenericDaoImpl<Long, Funcion> implements FuncionDao  {
	
	@SuppressWarnings("unchecked")
	public List<Funcion> findAll() {
        Criteria criteria = createEntityCriteria();
        generateSortOrder("descripcion", "1", criteria);
        
        return (List<Funcion>) criteria.list();
    }
	
}
