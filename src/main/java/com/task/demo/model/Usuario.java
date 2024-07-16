package com.task.demo.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.Set;

@Entity
@Getter@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usr_id")
    private Integer id;
    @Column(name = "usr_mail",unique = true)
    @Email
    private String mail;


    //@NotBlank
    private String contraseña;
   // @Column(nullable = false,name = "usr_add_date")
    private Date addDate;

    @ManyToMany(fetch = FetchType.EAGER,targetEntity = RoleEntity.class, cascade = CascadeType.PERSIST)
    @JoinTable(name = "user_roles",joinColumns = @JoinColumn(name="usr_id"),inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RoleEntity> roles;




    public Usuario(Usuario usuario){
    }
}
