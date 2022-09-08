package com.sa.su.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.sa.su.beans.SuministrosException;
import com.sa.su.model.Permiso;

@Repository("permisoDao")
public class PermisoDaoImpl extends GenericDaoImpl<String, Permiso> implements PermisoDao {

	@SuppressWarnings("unchecked")
	public List<Permiso> findFilteredList(Integer puesto, Integer area, List<Integer> funciones) {
		Criteria criteria = createEntityCriteria();

		if (puesto != null)
			criteria.add(Restrictions.eq("codPuesto", puesto));

		if (area != null)
			criteria.add(Restrictions.eq("codArea", area));

		if (funciones != null && funciones.size() != 0)
			criteria.add(Restrictions.in("funcion.id", funciones));
		
        criteria.add(Restrictions.isNull("fechaBaja"));
        criteria.add(Restrictions.isNull("usuarioBaja"));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return (List<Permiso>) criteria.list();
	}

	public void create(Permiso permiso, String usuario) throws Exception {
		Permiso permisoDb = (Permiso) getSession().get(Permiso.class, permiso);
		if (permisoDb != null && permisoDb.getFechaBaja() == null)
			throw new SuministrosException("Permiso existente");
		
		permiso.setUsuarioAlta(usuario);
		permiso.setFechaAlta(new Date());
		permiso.setUsuarioBaja(null);
		permiso.setFechaBaja(null);
		
		update(permiso);
	}

	public void delete(Permiso permiso, String usuario) {
		permiso.setUsuarioBaja(usuario);
		permiso.setFechaBaja(new Date());
		
		update(permiso);
	}
	
	public boolean usuarioTieneFuncion(String legajo, Integer funcionId) {
		Query query = getSession().createQuery("SELECT f FROM Funcion f, Permiso p, Usuario u " + 
				"WHERE f.id = p.funcion.id AND p.codArea = u.codArea AND p.fechaBaja IS NULL " + 
				"AND u.legajo = :legajo " + 
				"AND f.id = :funcionId");
		query.setParameter("legajo", legajo);
		query.setParameter("funcionId", funcionId);
		return !query.list().isEmpty();
	}
}
