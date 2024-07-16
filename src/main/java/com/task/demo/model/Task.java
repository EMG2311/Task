package com.task.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.persistence.*;

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

    public Task(String nombre, String descripcion,Boolean check, Usuario usuario){
        this.nombre=nombre;
        this.descripcion=descripcion;
        this.check=(check==null?check:false);
        this.usuario=usuario;
    }
}
