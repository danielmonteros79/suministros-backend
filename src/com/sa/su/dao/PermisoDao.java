package com.sa.su.dao;

import java.util.List;

import com.sa.su.model.Permiso;

public interface PermisoDao {
	List<Permiso> findFilteredList(Integer puesto, Integer area, List<Integer> funciones);
	void create(Permiso permiso, String usuario) throws Exception;
	void delete(Permiso permiso, String usuario);
	boolean usuarioTieneFuncion(String legajo, Integer funcionId);
}
