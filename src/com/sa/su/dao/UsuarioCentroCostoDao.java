package com.sa.su.dao;

import java.util.List;

import com.sa.su.model.UsuarioCentroCosto;

public interface UsuarioCentroCostoDao {
	List<UsuarioCentroCosto> findFilteredList(String legajo, List<Long> cc);
	void save(UsuarioCentroCosto usuarioCC);
	void saveBatch(UsuarioCentroCosto usuarioCC);
	void deleteUsrCC(UsuarioCentroCosto userCC);
	void deleteUsrCCBatch(UsuarioCentroCosto userCC);
}
