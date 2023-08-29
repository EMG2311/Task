package com.task.demo.service;

import com.task.demo.dto.*;

import java.util.List;

public interface UsuarioService {
    UsuarioResDto crearUsuario(UsuarioDto usuarioDto);
    UsuarioResMostrarDto eliminarUsuario(Integer id);
    UsuarioResModifDto modoficarUsuario(UsuarioModifDto usuarioModfDto);

    List<UsuarioResMostrarDto> listarUsuarios();

}
