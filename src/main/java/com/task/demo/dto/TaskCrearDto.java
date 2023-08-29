package com.task.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
@AllArgsConstructor
public class TaskCrearDto {
    private String nombre;
    private String descripcion;
    private Integer idUsuario;
}
