
package com.task.demo.mapper;

import com.task.demo.dto.UsuarioDto;
import com.task.demo.dto.UsuarioResMostrarDto;
import com.task.demo.model.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UsuarioMapper {

    public UsuarioDto converToUsuarioDto(Usuario usuario){
        return new UsuarioDto(usuario.getMail(),usuario.getContraseña());
    }

  /*  public Usuario converToUsuario(UsuarioDto usuarioDto){
        return new Usuario(usuarioDto.getId(),usuarioDto.getMail(),usuarioDto.getContraseña(),new Date());
    }*/

}
