package com.sa.su.service;

import java.util.List;

import com.sa.su.model.UsuarioCentroCosto;

public interface UsuarioCentroCostoService {
	List<UsuarioCentroCosto> findFilteredList(String legajo, List<Long> cc);
	void saveListUserCC(List<UsuarioCentroCosto> list);
	void saveUserCC(UsuarioCentroCosto userCC);
	void deleteUserCC(UsuarioCentroCosto userCC);
	void updateCCBatch() throws Exception;
}
