package com.sa.su.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sa.su.dao.MaterialDao;
import com.sa.su.dao.PedidoDao;
import com.sa.su.model.Material;
import com.sa.su.model.Pedido;

@Service("materialService")
@Transactional
public class MaterialServiceImpl implements MaterialService {

	@Autowired
	private MaterialDao dao;
	@Autowired PedidoDao pdao;

	@Override
	public List<Material> findAll() {
		return dao.findAll();
	}

	@Override
	public Material getById(String idMaterial) {
		Material mat = new Material();
//		mat.setId("mat");
		return mat;

	}

	@Override
	public List<Material> findForPedido(Long ccostos) {
		return dao.findForPedido(ccostos);
	}
	
	@Override
	public void updateStock(Material material, Long cantidad, String usuario){
		dao.updateStock(material, cantidad, usuario);
	}
	
	@Override
	public List<Material> findOfRubro(List<Long> idRubros) {
		return dao.findOfRubro(idRubros);
	}
	@Override
	public List<Material> findUrgente(List<String> urgente) {
		return dao.findUrgente(urgente);
	}
	@Override
	public List<Material> getLastOrdered(String ivUser){
		List<Material> list = new ArrayList<Material>();
		for (Pedido lastPed : pdao.getLastOrdered(ivUser)){
			list.add(lastPed.getMaterial());
		}
		return list;
	}
	@Override
	public List<Material> findFilteredList(List<Long> idProv, String mat){
		return dao.findFilteredList(idProv, mat);
	}
	@Override
	public void saveListMaterial(List<Material> list, String userAlta){
		for (Material material : list){
			dao.save(material, userAlta);
		}
	}
	@Override
	public Long updateMaterial(Material material, String userMod){
		return dao.update(material, userMod);
	}
	@Override
	public void saveMaterial(Material material, String userAlta){
		dao.save(material, userAlta);
	}
	@Override
	public void deleteMaterial(Material material, String userDelete){
		dao.deleteMaterial(material, userDelete);
	}
}