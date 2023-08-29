package com.task.demo.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@AllArgsConstructor
@Getter
@Setter
public class UsuarioResMostrarDto {
    @JsonProperty("mail")
    private String mail;
    @JsonProperty("contraseña")
    private String contraseña;
    @JsonProperty("AddDate")
    private Date addDate;
    private Integer codigoError;
    private String mensajeError;
    public UsuarioResMostrarDto(UsuarioResMostrarDto usuarioResMostrarDto){
        this.mail= usuarioResMostrarDto.getMail();
        this.contraseña= usuarioResMostrarDto.getContraseña();
        this.addDate= usuarioResMostrarDto.getAddDate();
    }
}
