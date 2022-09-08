package com.sa.su.service;

import java.util.List;

import com.sa.su.model.Estado;

public interface EstadoService {

	List<Estado> findAll();

	List<Estado> findProcessStatus();

}