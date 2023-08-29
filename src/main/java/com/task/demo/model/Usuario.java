package com.task.demo.model;

import com.task.demo.dto.UsuarioDto;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usr_id")
    private Integer id;
    @Column(nullable = false,name = "usr_mail",unique = true)
    private String mail;
    @Column(nullable = false,name = "usr_contraseña")
    private String contraseña;
    @Column(nullable = false,name = "usr_add_date")
    private Date addDate;

    public Usuario(Usuario usuario){
        this.id=usuario.getId();
        this.mail=usuario.getMail();
        this.contraseña=usuario.getContraseña();
        this.addDate=usuario.getAddDate();
    }

}
