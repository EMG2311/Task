package com.task.demo.service;

import com.task.demo.dto.UsuarioDto;

public interface UsuarioService {
    UsuarioDto crearUsuario(UsuarioDto usuarioDto);
    UsuarioDto eliminarUsuario(Integer id);
    UsuarioDto modoficarUsuario(UsuarioDto usuarioDto);
}
