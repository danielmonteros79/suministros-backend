package com.sa.su.service;

import java.util.List;

import com.sa.su.model.Material;

public interface MaterialService {

	List<Material> findAll();
	Material getById(String idMaterial);
	List<Material> findForPedido(Long ccostos);
	void updateStock(Material material, Long cantidad, String usuario);
	List<Material> findOfRubro(List<Long> idRubros);
	List<Material> findUrgente(List<String> urgente);
	List<Material> getLastOrdered(String ivUser);
	List<Material> findFilteredList(List<Long> idProv, String mat);
	void saveListMaterial(List<Material> list, String userAlta);
	Long updateMaterial(Material material, String userMod);
	void saveMaterial(Material material, String userAlta);
	void deleteMaterial(Material material, String userDelete);

}