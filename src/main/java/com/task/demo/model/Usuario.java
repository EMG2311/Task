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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usr_id")
    private Integer id;
    @Column(nullable = false,name = "usr_mail")
    private String mail;
    @Column(nullable = false,name = "usr_contraseña")
    private String contraseña;
    @Column(nullable = false,name = "usr_estado")
    @Enumerated(EnumType.STRING)
    private UsuarioEstadoEnum estado;
}
