package com.sa.su.dao;

import com.sa.su.model.Material;
import com.sa.su.model.Movimiento;
import com.sa.su.model.Pedido;
import com.sa.su.model.Usuario;



	
	public interface MovimientoDao extends GenericDao<Long, Movimiento>{

		void save(Pedido pedido, String usuarioAlta,String tipoMovimiento) ;

		void save(Material newEntity, Usuario usuario, String tipoMovimiento);
		

	}


