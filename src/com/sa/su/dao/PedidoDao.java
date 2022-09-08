package com.sa.su.dao;

import java.util.Date;
import java.util.List;
import com.sa.su.model.CantidadMaterial;
import com.sa.su.model.FileNameAndSecuence;
import com.sa.su.model.Pedido;
import com.sa.su.model.Periodo;
import com.sa.su.model.Rubro;

public interface PedidoDao {

	List<Pedido> findFilteredListPendientes(List<Long> material, List<Long> ccostos, List<Long> prioridad, Date feDesde,
			Date feHasta, List<Long> estado, Boolean validados);
	List<Pedido> findFilteredListResueltos(List<Long> material, List<Long> ccostos, List<Long> prioridad, Date feDesde,
			Date feHasta, List<Long> estado);
	List<Pedido> findFilteredListTratados(List<Long> material, List<Long> ccostos, List<Long> prioridad, Date feDesde,
			Date feHasta, List<Long> estado);
	Pedido getById(Long pIdEmpleado);
	public Long save(Pedido pedido, String usuarioAlta, Long ccostos) throws Exception;
	public Long update(Pedido pedido, String usuario) throws Exception;
	public Long recibir(Pedido pedido, String usuario);
	Long delete(Long pedido);
	List<Pedido> getLastOrdered(String ivUser);

	Long getSumCantidadMaterialPedidosRecibidosPeriodoByCCMinusPedido(Long material, Long cc, Periodo periodo, Long Pedido) throws Exception;
	
	List<CantidadMaterial> sumAmountByPendingMaterial();
	List<Pedido> processFile(List<Long> ccostos, Rubro rubro);
	List<Pedido> procesadosNum(Long num_trat);
	List<FileNameAndSecuence>getFileNameAndSecuence(List<Long> ccostos, Long rubro);

}
