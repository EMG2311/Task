
package com.task.demo.mapper;

import com.task.demo.dto.UsuarioDto;
import com.task.demo.model.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UsuarioMapper {

    public UsuarioDto converToUsuarioDto(Usuario usuario){
        return new UsuarioDto( usuario.getId(),usuario.getMail(),usuario.getContraseña(),usuario.getEstado());
    }

    public Usuario converToUsuario(UsuarioDto usuarioDto){
        return new Usuario(usuarioDto.getId(),usuarioDto.getMail(),usuarioDto.getContraseña(),usuarioDto.getEstado());
    }

}
