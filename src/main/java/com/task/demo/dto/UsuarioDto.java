package com.task.demo.dto;

import com.task.demo.enums.UsuarioEstadoEnum;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@Getter@Setter
public class UsuarioDto {

    private Integer id;
    private String mail;
    private String contraseña;
    private UsuarioEstadoEnum estado;
}
