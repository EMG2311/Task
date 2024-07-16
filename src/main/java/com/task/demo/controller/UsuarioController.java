package com.task.demo.controller;

import com.task.demo.dto.*;
import com.task.demo.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioServiceImplementation;
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
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UsuarioResMostrarDto> eliminarUsuario(@PathVariable Integer id,
                                                                @RequestHeader("Authorization") String token){
        UsuarioResMostrarDto usuarioResMostrarDto=usuarioServiceImplementation.eliminarUsuario(id,token.substring(7));
        if(usuarioResMostrarDto.getCodigoError()==0){
            return ResponseEntity.ok(usuarioResMostrarDto);
        }
        else{
            return ResponseEntity.status(409).body(usuarioResMostrarDto);
        }
    }

    @DeleteMapping("/eliminar")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UsuarioResMostrarDto> eliminarUsuario(@RequestHeader("Authorization") String token){
        UsuarioResMostrarDto usuarioResMostrarDto=usuarioServiceImplementation.eliminarUsuario(token.substring(7));
        if(usuarioResMostrarDto.getCodigoError()==0){
            return ResponseEntity.ok(usuarioResMostrarDto);
        }
        else{
            return ResponseEntity.status(409).body(usuarioResMostrarDto);
        }
    }




    @GetMapping("/listar")
    @PreAuthorize("hasRole('ADMIN')")
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
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<UsuarioResModifDto> modificarUsuario(@RequestBody UsuarioModifDto usuarioModifDto,
                                                               @RequestHeader("Authorization") String token){
        UsuarioResModifDto usuarioResModifDto=usuarioServiceImplementation.modoficarUsuario(usuarioModifDto,token.substring(7));
        if(usuarioResModifDto.getCodigoError()==0){
            return ResponseEntity.ok(usuarioResModifDto);
        }else{
            return ResponseEntity.status(409).body(usuarioResModifDto);
        }
    }


    @PutMapping("/admin/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<UsuarioRolDto> setAdmin(@PathVariable Integer id){
        return ResponseEntity.ok(usuarioServiceImplementation.setAdmin(id));
    }


}
