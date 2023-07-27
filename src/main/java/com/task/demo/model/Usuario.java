package com.task.demo.model;

import com.task.demo.enums.UsuarioEstadoEnum;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    @Column(nullable = false)
    private String mail;
    @Column(nullable = false)
    private String contraseña;
    @Column(nullable = false)
    private UsuarioEstadoEnum estado;
}
