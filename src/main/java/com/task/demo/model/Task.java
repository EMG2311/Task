package com.task.demo.model;

import jakarta.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tsk_id")
    private Integer id;
    @Column(nullable = false, name = "tsk_nombre")
    private String nombre;
    @Column(name = "tsk_descripcion")
    private String descripcion;
    @Column(nullable = false,name = "tsk_check")
    private Boolean check;
    @ManyToOne
    @JoinColumn(name = "tsk_usr_id", nullable = false)
    private Usuario usuario;

}
