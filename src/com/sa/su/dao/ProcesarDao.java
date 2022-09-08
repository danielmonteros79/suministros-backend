package com.sa.su.dao;

import java.util.List;

import com.sa.su.model.Pedido;

public interface ProcesarDao {
	List<Pedido> findAProcesar(List<Long> centrosCosto);
}
