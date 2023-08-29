package com.task.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter@Setter
public class UsuarioResModifDto {
    private Integer id;
    private String mail;
    private String Contraseña;
    private Integer codigoError;
    private String mensajeError;
}
