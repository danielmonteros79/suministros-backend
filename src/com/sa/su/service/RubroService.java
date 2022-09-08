package com.sa.su.service;

import java.util.List;

import com.sa.su.model.Rubro;

public interface RubroService {
	List<Rubro> listAll();
	List<Rubro> findFilteredList(List<Long> id);
	Long updateRubro(Rubro rubro, String userMod);
	void saveRubro(Rubro rubro, String usrSv);
	Long deleteRubro(Rubro rubro, String usrDlt);
	Rubro find(Long id);
}
