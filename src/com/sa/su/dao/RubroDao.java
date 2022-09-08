package com.sa.su.dao;

import java.util.List;

import com.sa.su.model.Rubro;

public interface RubroDao extends GenericDao<Long, Rubro> {
	List<Rubro> findAll();
	List<Rubro> findFilteredList(List<Long> id);
	void save(Rubro rubro, String usrSv);
	public Long update(Rubro rubro, String userMod);
	Long deleteRubro(Rubro rubro, String usrDlt);
}
