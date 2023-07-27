package com.task.demo.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private String id;
    @Column(nullable = false,unique = true)
    private String mail;
    private String contraseña;
}
