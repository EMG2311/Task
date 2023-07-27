package com.task.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Task {
    @Id
    private Integer id;
    private String nombre;
    private String descripcion;
    private Boolean check;
    @ManyToOne
    private Usuario usuario;

}
