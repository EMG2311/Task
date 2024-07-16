package com.task.demo.dto;

import com.task.demo.model.RoleEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.Set;

@Data@AllArgsConstructor@NoArgsConstructor
public class CreateUsuarioDto {
    @Email
    @NotBlank
    private String mail;
    @NotBlank
    private String userName;
    @NotBlank
    private String contraseña;
     private Set<String> roles;
}
