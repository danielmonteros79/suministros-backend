package com.sa.su.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sa.su.dao.CentroCostosDao;
import com.sa.su.model.CentroCostos;

@Service("centroCostosService")
@Transactional
public class CentroCostosServiceImpl implements CentroCostosService {

	@Autowired
	private CentroCostosDao dao;

	@Override
	public List<CentroCostos> findAll() {
		return dao.findAll();
	}
	@Override
	public List<CentroCostos>findOfZona(Integer zona){
		return dao.findOfZona(zona);
	}
	@Override
	public List<CentroCostos> addAdress(List<CentroCostos> list) {
		List<CentroCostos> ret = new ArrayList<CentroCostos>();
		for(CentroCostos cc : list) {
			cc.setDescripcion(cc.getDescripcion() + "-" + cc.getDireccion1());
			ret.add(cc);
		}
		return list;
	}
	@Override
	public List<CentroCostos> getByUser(String ivUser) {
		// TODO Auto-generated method stub
		return dao.getByUser(ivUser);
	}
}