package com.sa.su.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import com.sa.su.model.CentroCostos;


@Repository("centroCostosDao")
public class CentroCostosDaoImpl extends GenericDaoImpl<Long, CentroCostos> implements CentroCostosDao {
		
		@SuppressWarnings("unchecked")
	    public List<CentroCostos> findAll() {
	        Criteria criteria = createEntityCriteria();
	        criteria.add(Restrictions.isNull("fechaBaja"));
	        criteria.add(Restrictions.isNull("usuarioBaja"));
	        return (List<CentroCostos>) criteria.list();
	    }
		@SuppressWarnings("unchecked")
		public List<CentroCostos> findOfZona(Integer zona){
			Criteria criteria = createEntityCriteria();
	        criteria.add(Restrictions.isNull("fechaBaja"));
	        criteria.add(Restrictions.isNull("usuarioBaja"));
			if (null != zona && 0 != zona)
				criteria.add(Restrictions.eq("zona.id", zona));
			criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			return (List<CentroCostos>) criteria.list();
		}
	
		@SuppressWarnings("unchecked")
		public List<CentroCostos> getByUser(String ivUser) {
					
			Query query = getSession().createQuery("SELECT cc FROM CentroCostos cc " +
					"WHERE cc.usuarioBaja IS NULL AND cc.fechaBaja IS NULL and cc.id in (select usu.centroCostos from Usuario as usu where usu.id =:legajo)");
			query.setParameter("legajo", ivUser);
			return query.list();
		}
		
		
}
