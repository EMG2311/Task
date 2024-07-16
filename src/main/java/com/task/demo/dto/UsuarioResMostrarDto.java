package com.task.demo.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UsuarioResMostrarDto {
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("mail")
    private String mail;
    @JsonProperty("AddDate")
    private Date addDate;
    private Integer codigoError;
    private String mensajeError;



}
