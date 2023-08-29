package com.task.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter@AllArgsConstructor
public class UsuarioResDto {
    private String mail;
    private String contraseña;
    private Integer codigoError;
    private String mensajeError;
}
