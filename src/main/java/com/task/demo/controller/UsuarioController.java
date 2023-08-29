package com.task.demo.controller;

import com.task.demo.dto.*;
import com.task.demo.service.implementation.UsuarioServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {
    @Autowired
    private UsuarioServiceImplementation usuarioServiceImplementation;
    @PostMapping("/crear")
    public ResponseEntity<UsuarioResDto> crearUsuario(@RequestBody@Validated UsuarioDto usuarioDto){
        UsuarioResDto usuarioResDto=usuarioServiceImplementation.crearUsuario(usuarioDto);
        if(usuarioResDto.getCodigoError()==0) {
            return ResponseEntity.ok(usuarioResDto);
        }else{
            return  ResponseEntity.status(409).body(usuarioResDto);
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<UsuarioResMostrarDto> eliminarUsuario(@PathVariable Integer id){
        UsuarioResMostrarDto usuarioResMostrarDto=usuarioServiceImplementation.eliminarUsuario(id);
        if(usuarioResMostrarDto.getCodigoError()==0){
            return ResponseEntity.ok(usuarioResMostrarDto);
        }
        else{
            return ResponseEntity.status(409).body(usuarioResMostrarDto);
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<UsuarioResMostrarDto>> listarUsuarios(){
        List<UsuarioResMostrarDto> listaUsuarioResMostrarDto =usuarioServiceImplementation.listarUsuarios();
        for(UsuarioResMostrarDto u : listaUsuarioResMostrarDto){
            if(u.getCodigoError()!=0){
                return ResponseEntity.status(409).body(listaUsuarioResMostrarDto);
            }
        }
        return ResponseEntity.ok(listaUsuarioResMostrarDto);

    }
    @PostMapping("/modificar")
    public ResponseEntity<UsuarioResModifDto> modificarUsuario(@RequestBody UsuarioModifDto usuarioModifDto){
        UsuarioResModifDto usuarioResModifDto=usuarioServiceImplementation.modoficarUsuario(usuarioModifDto);
        if(usuarioResModifDto.getCodigoError()==0){
            return ResponseEntity.ok(usuarioResModifDto);
        }else{
            return ResponseEntity.status(409).body(usuarioResModifDto);
        }
    }


}
