package com.task.demo.dto;

import com.task.demo.model.ERol;
import com.task.demo.model.RoleEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class UsuarioRolDto {
    private String mail;
    private Set<RoleEntity> roles;
}