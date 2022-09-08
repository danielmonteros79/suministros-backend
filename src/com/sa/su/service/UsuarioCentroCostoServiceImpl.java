package com.sa.su.service;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sa.su.dao.CentroCostosDao;
import com.sa.su.dao.UsuarioCentroCostoDao;
import com.sa.su.model.UsuarioCentroCosto;

@Service("usuarioCentroCostoService")
@Transactional
public class UsuarioCentroCostoServiceImpl implements UsuarioCentroCostoService {

	@Autowired
	UsuarioCentroCostoDao dao;
	@Autowired
	CentroCostosDao ccdao;
	@Autowired
	private ConfigurableEnvironment environment;
	Logger log = Logger.getLogger(UsuarioCentroCostoServiceImpl.class);
	@Override
	public void saveListUserCC(List<UsuarioCentroCosto> list){
		for (UsuarioCentroCosto userCC : list) {
			dao.save(userCC);
		}
	}
	
	@Override 
	public void deleteUserCC(UsuarioCentroCosto userCC){
		dao.deleteUsrCC(userCC);
	}
	@Override
	public List<UsuarioCentroCosto> findFilteredList(String legajo, List<Long> cc){
		return dao.findFilteredList(legajo, cc);
	}
	@Override
	public void saveUserCC(UsuarioCentroCosto userCC){
		dao.save(userCC);
	}
	@Override
	public void updateCCBatch() throws Exception{
			String fieldSeparator = ",";
			String filePath = environment.getRequiredProperty("batch.update.UsuarioCentroCostos.file.path");
			String fileName = environment.getRequiredProperty("batch.update.UsuarioCentroCostos.file.name");
			
			if (!filePath.substring(filePath.length() - 1).equals(File.separator))
				filePath += File.separator;
			
			String path = filePath + fileName + ".txt";
			log.info("Path archivo: " + path);
			Scanner scanner = null;
			try {
				scanner = new Scanner(new FileInputStream(path));
				scanner.useDelimiter(fieldSeparator);
				while (scanner.hasNext()){
					String readed = scanner.next();
					UsuarioCentroCosto entity = new UsuarioCentroCosto();
					entity.setLegajo(readed.substring(1, 8));
					entity.setCentroCostos(ccdao.find(Long.valueOf(readed.substring(8,12))));
					if (readed.substring(0,1).toUpperCase().equals("A")) dao.saveBatch(entity);
					else if (readed.substring(0,1).toUpperCase().equals("B")) dao.deleteUsrCCBatch(entity);
				}
			}catch (Exception e){
				throw e;
			}
			finally{
				if (scanner != null) scanner.close();
			}
	}

}
