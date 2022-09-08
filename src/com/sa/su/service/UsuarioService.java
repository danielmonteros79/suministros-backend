package com.sa.su.service;

import com.sa.su.model.Usuario;

public interface UsuarioService {

	Usuario update(Usuario usuario) throws Exception;

	Usuario find(String legajo);

}