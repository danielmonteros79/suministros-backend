package com.sa.su.dao;

import org.springframework.stereotype.Repository;

import com.sa.su.model.Usuario;

@Repository("usuarioDao")
public class UsuarioDaoImpl extends GenericDaoImpl<String, Usuario> implements UsuarioDao {

}
