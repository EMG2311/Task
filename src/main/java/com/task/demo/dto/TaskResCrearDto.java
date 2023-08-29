package com.task.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter@Setter
public class TaskResCrearDto {
    private Integer id;
    private String nombre;
    private String descripcion;
    private Integer idUsuario;
    private Boolean check;
    private Integer codigoError;
    private String mensajeError;
}
