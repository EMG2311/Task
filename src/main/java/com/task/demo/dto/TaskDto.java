package com.task.demo.dto;

import com.task.demo.model.Usuario;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter@Setter
public class TaskDto {
    private Integer id;
    private String nombre;
    private String descripcion;
    private Boolean check;
}
