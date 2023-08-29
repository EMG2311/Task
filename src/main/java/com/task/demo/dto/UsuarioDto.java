package com.task.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@Getter@Setter
public class UsuarioDto {

    public UsuarioDto(UsuarioDto usuarioDto){
        this.mail=usuarioDto.getMail();
        this.contraseña=usuarioDto.getContraseña();

    }

    @JsonProperty("mail")
    private String mail;
    @JsonProperty("contraseña")
    private String contraseña;


}
