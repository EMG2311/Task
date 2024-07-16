package com.task.demo.service;

import com.task.demo.dto.*;

import java.util.List;

public interface UsuarioService {
    UsuarioResDto crearUsuario(UsuarioDto usuarioDto);
    UsuarioResMostrarDto eliminarUsuario(Integer id,String token);
    UsuarioResMostrarDto eliminarUsuario(String token);
    UsuarioResModifDto modoficarUsuario(UsuarioModifDto usuarioModfDto,String token);
    List<UsuarioResMostrarDto> listarUsuarios();
    UsuarioRolDto setAdmin(Integer id);
}
