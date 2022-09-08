package com.sa.su.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sa.su.dao.MaterialDao;
import com.sa.su.dao.RubroDao;
import com.sa.su.model.Material;
import com.sa.su.model.Rubro;

@Service("rubroService")
@Transactional
public class RubroServiceImpl implements RubroService {

	@Autowired
	private RubroDao dao;
	@Autowired
	private MaterialDao mdao;
	@Override
	public List<Rubro> listAll(){
		return dao.findAll();
	}
	@Override
	public List<Rubro> findFilteredList(List<Long> id){
		return dao.findFilteredList(id);
	}
	@Override
	public void saveRubro(Rubro rubro, String usrSv){
		dao.save(rubro, usrSv);
	}
	@Override
	public Long deleteRubro(Rubro rubro, String usrDlt){
		List<Long> rubList = new ArrayList<Long>();
				rubList.add(rubro.getId());
				List<Material> mList = mdao.findOfRubro(rubList);
		if (null != mList && 0 != mList.size())
						return 3L;
		return dao.deleteRubro(rubro, usrDlt);
	}
	@Override
	public Long updateRubro(Rubro rubro, String userMod) {
		return dao.update(rubro, userMod);
	}
	@Override
	public Rubro find(Long id){
		return dao.find(id);
	}
}
