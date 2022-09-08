package com.sa.su.dao;

import java.util.List;

import com.sa.su.model.Material;

public interface MaterialDao extends GenericDao<Long, Material> {
	List<Material> findAll();
	Material getById(long idMaterial);
	List<Material> findForPedido(Long ccostos);
	void updateStock(Material material, Long cantidad, String usuario);
	List<Material> findOfRubro(List<Long> idRubros);
	List<Material> findUrgente(List<String> urgente);
	List<Material> findFilteredList(List<Long> idRubro, String mat);
	void save(Material material, String userAlta);
	public Long update(Material material, String userMod);
	void deleteMaterial(Material material, String usrDlt);
}
