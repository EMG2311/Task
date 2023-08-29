package com.task.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter@Setter
public class TaskResEliminarDto {
    private String nombre;
    private Integer codigoError;
    private String mensajeError;
}
