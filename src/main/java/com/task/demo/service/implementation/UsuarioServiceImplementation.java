package com.task.demo.service.implementation;

import com.task.demo.dto.*;
import com.task.demo.mapper.UsuarioMapper;
import com.task.demo.model.Usuario;
import com.task.demo.repository.UsuarioRepository;
import com.task.demo.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsuarioServiceImplementation implements UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private UsuarioMapper usuarioMapper;
    private final static  Logger LOGGER=  LoggerFactory.getLogger(UsuarioServiceImplementation.class);



    @Override
    public UsuarioResDto crearUsuario(UsuarioDto usuarioDto) {
        try {
            Usuario usuario=new Usuario();
            usuario.setContraseña(usuarioDto.getContraseña());
            usuario.setAddDate(new Date());
            usuario.setMail(usuarioDto.getMail());
            usuarioRepository.save(usuario);
            return new UsuarioResDto(usuarioDto.getMail(),usuarioDto.getContraseña(),0,"Se creo el usuario correctamente");
        }catch (Exception e){
            LOGGER.error("Error al crear usuario " + e);
            return new UsuarioResDto(usuarioDto.getMail(),usuarioDto.getContraseña(),-1, e.getMessage());
        }
    }

    @Override
    public UsuarioResMostrarDto eliminarUsuario(Integer id) {
        try{
            LOGGER.info("----------Buscando usuario----------");
            Usuario usuario= new Usuario(usuarioRepository.findById(id).get());
            LOGGER.info("----------Eliminando usuario----------");
            usuarioRepository.deleteById(id);
            return new UsuarioResMostrarDto(usuario.getMail(),usuario.getContraseña(),usuario.getAddDate(),0,"Se elimino el usuario correctamente");
        }catch (Exception e){
            LOGGER.error("No se pudo eliminar el usuario con id: "+ id + " " + e.getMessage());
            return new UsuarioResMostrarDto(null,null,null,-1,e.getMessage());
        }
    }

    @Override
    public UsuarioResModifDto modoficarUsuario(UsuarioModifDto usuarioModifDto) {
        try {
            LOGGER.info("----------Buscando usuarios----------");
            Usuario usuario = usuarioRepository.findById(usuarioModifDto.getId()).get();
            usuario.setMail(usuarioModifDto.getMail());
            usuario.setContraseña(usuarioModifDto.getContraseña());
            LOGGER.info("----------Modificando usuario----------");
            usuarioRepository.save(usuario);
            return new UsuarioResModifDto(usuario.getId(),usuario.getMail(),usuario.getContraseña(),0,"Se modifico el usuario correctamente");
        }catch (Exception e){
            LOGGER.error("No se pudo modificar el usuario " + e.getMessage());
            return new UsuarioResModifDto(usuarioModifDto.getId(),usuarioModifDto.getMail(), usuarioModifDto.getContraseña(),-1,e.getMessage());
        }
    }

    @Override
    public List<UsuarioResMostrarDto> listarUsuarios() {
        List<UsuarioResMostrarDto> listaUsuarioResMostrarDto= new ArrayList<>();
        try {
            LOGGER.info("----------Buscando todos los usuarios----------");
            List<Usuario> listaUsuarios = usuarioRepository.findAll();
            for (Usuario u : listaUsuarios) {
                listaUsuarioResMostrarDto.add(new UsuarioResMostrarDto(u.getMail(),u.getContraseña(),u.getAddDate(),0,null));
            }
            return listaUsuarioResMostrarDto;
        }catch (Exception e){
            LOGGER.error("Error buscando todos los usuarios");
            listaUsuarioResMostrarDto.add(new UsuarioResMostrarDto(null,null,null,-1,"No se pudo listar los usuarios"));
            return listaUsuarioResMostrarDto;
        }

    }
}
