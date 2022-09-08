package com.sa.su.dao;

import java.util.Date;
import org.springframework.stereotype.Repository;
import com.sa.su.model.Material;
import com.sa.su.model.Movimiento;
import com.sa.su.model.Pedido;
import com.sa.su.model.Usuario;


@Repository("movimientosDao")
public class MovimientoDaoImpl extends GenericDaoImpl<Long, Movimiento> implements MovimientoDao {


	
	public void  save(Pedido pedido, String usuarioAlta, String tipoMovimiento) {
		Movimiento newEntity = new Movimiento();
		setDataForInsert(newEntity, pedido, usuarioAlta, tipoMovimiento);
		update(newEntity);
		
	}


	private void setDataForInsert(Movimiento newEntity, Pedido pedido, String usuarioAlta,String tipoMovimiento)  {
		
		
		newEntity.setFechaAlta(new Date());
		newEntity.setUsuarioAlta(usuarioAlta);
		newEntity.setCantidad(pedido.getCant_aprobada());
		newEntity.setCcostos(pedido.getCcostos().getId());
		newEntity.setMaterial(pedido.getMaterial().getId());
		newEntity.setPrecio_pp(pedido.getMaterial().getPrecio_pp());
		newEntity.setTipo_movimiento(tipoMovimiento);
		

		}






	@Override
	public void save(Material newEntity, Usuario usuario, String tipoMovimiento) {

		Movimiento newEntityMovimiento = new Movimiento();
		setDataForInsert(newEntityMovimiento, newEntity, usuario, tipoMovimiento);
		update(newEntityMovimiento);
		
	}

	private void setDataForInsert(Movimiento newEntity, Material newEntityMaterial, Usuario usuario,
			String tipoMovimiento) {
	
		newEntity.setFechaAlta(new Date());
		newEntity.setUsuarioAlta(usuario.getLegajo());
		newEntity.setCantidad(newEntityMaterial.getCantidad_stock());
		newEntity.setCcostos(usuario.getCentroCostos().getId());
		newEntity.setMaterial(newEntityMaterial.getId());
		newEntity.setPrecio_pp(newEntityMaterial.getPrecio_pp());
		newEntity.setTipo_movimiento(tipoMovimiento);
		
	}

}
