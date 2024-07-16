package com.task.demo.service.implementation;

import com.task.demo.configuration.JWT.JwtUtils;
import com.task.demo.dto.*;
import com.task.demo.model.ERol;
import com.task.demo.model.RoleEntity;
import com.task.demo.model.Usuario;
import com.task.demo.repository.RoleRepository;
import com.task.demo.repository.UsuarioRepository;
import com.task.demo.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsuarioServiceImplementation implements UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository rolesrepository;
    private static final String ROLE_ADMIN="ROLE_ADMIN";
    @Autowired
    private JwtUtils jwtUtils;
    private final static Logger LOGGER=  LoggerFactory.getLogger(UsuarioServiceImplementation.class);



    @Override
    public UsuarioResDto crearUsuario(UsuarioDto usuarioDto) {
        try {
            Usuario usuario=new Usuario();
            usuario.setContraseña(passwordEncoder.encode(usuarioDto.getContraseña()));
            usuario.setAddDate(new Date());
            usuario.setMail(usuarioDto.getMail());
            Set<RoleEntity> rols=new HashSet<>();
            rols.add(rolesrepository.findByName(ERol.USER).get());
            usuario.setRoles(rols);
            usuarioRepository.save(usuario);
            return new UsuarioResDto(usuarioDto.getMail(),usuarioDto.getContraseña(),0,"Se creo el usuario correctamente");
        }catch (Exception e){
            LOGGER.error("Error al crear usuario " + e);
            return new UsuarioResDto(usuarioDto.getMail(),usuarioDto.getContraseña(),-1, e.getMessage());
        }
    }

    @Override
    public UsuarioResMostrarDto eliminarUsuario(Integer id,String token) {
        try{
            if(jwtUtils.getRoles().contains(ROLE_ADMIN)){
                Usuario usuario=usuarioRepository.findByMail(jwtUtils.getMailFromToken(token)).get();
                if(usuario.getId()==id){
                    LOGGER.error("Un admin no puede borrar su cuenta");
                    throw  new RuntimeException("Un admin no puede borrar su cuenta");
                }
                LOGGER.info("----------Eliminando usuario----------");
                usuarioRepository.deleteById(id);
                return UsuarioResMostrarDto.builder()
                        .mail(usuario.getMail())
                        .addDate(usuario.getAddDate())
                        .codigoError(0)
                        .mensajeError("")
                        .build();
            }
            LOGGER.info("----------Buscando usuario----------");
            Usuario usuario= new Usuario(usuarioRepository.findById(id).get());
            LOGGER.info("----------Eliminando usuario----------");
            usuarioRepository.deleteById(id);
            return UsuarioResMostrarDto.builder()
                    .mail(usuario.getMail())
                    .addDate(usuario.getAddDate())
                    .codigoError(0)
                    .mensajeError("")
                    .build();

         }catch (Exception e){
            LOGGER.error("No se pudo eliminar el usuario con id: "+ id + " " + e.getMessage());
            return UsuarioResMostrarDto.builder().codigoError(-1).mensajeError(e.getMessage()).build();
        }
    }

    public UsuarioResMostrarDto eliminarUsuario(String token) {
        try{
            if(jwtUtils.getRoles().contains(ROLE_ADMIN)){
                LOGGER.error("Un admin no puede borrar su cuenta");
                throw  new RuntimeException("Un admin no puede borrar su cuenta");
            }else{
                Usuario usuario=usuarioRepository.findByMail(jwtUtils.getMailFromToken(token)).get();
                LOGGER.info("----------Eliminando usuario----------");
                usuarioRepository.deleteById(usuario.getId());
                return UsuarioResMostrarDto.builder()
                        .mail(usuario.getMail())
                        .addDate(usuario.getAddDate())
                        .codigoError(0)
                        .mensajeError("")
                        .build();
            }

        }catch (Exception e){
            LOGGER.error("No se pudo eliminar el usuario: " + e.getMessage());
            return UsuarioResMostrarDto.builder().codigoError(-1).mensajeError(e.getMessage()).build();

        }


    }

    @Override
    public UsuarioResModifDto modoficarUsuario(UsuarioModifDto usuarioModifDto,String token) {
        try {
            LOGGER.info("----------Buscando usuarios----------");
            Usuario usuario = usuarioRepository.findByMail(jwtUtils.getMailFromToken(token)).get();
            usuario.setMail(usuarioModifDto.getMail());
            usuario.setContraseña(usuarioModifDto.getContraseña());
            LOGGER.info("----------Modificando usuario----------");
            usuarioRepository.save(usuario);
            return new UsuarioResModifDto(usuario.getId(),usuario.getMail(),usuario.getContraseña(),0,"Se modifico el usuario correctamente");
        }catch (Exception e){
            LOGGER.error("No se pudo modificar el usuario " + e.getMessage());
            return new UsuarioResModifDto(null,usuarioModifDto.getMail(), usuarioModifDto.getContraseña(),-1,e.getMessage());
        }
    }

    @Override
    public List<UsuarioResMostrarDto> listarUsuarios() {
        List<UsuarioResMostrarDto> listaUsuarioResMostrarDto= new ArrayList<>();
        try {
            LOGGER.info("----------Buscando todos los usuarios----------");
            List<Usuario> listaUsuarios = usuarioRepository.findAll();
            for (Usuario u : listaUsuarios) {
                listaUsuarioResMostrarDto.add(UsuarioResMostrarDto.builder()
                        .id(u.getId())
                        .mail(u.getMail())
                        .addDate(u.getAddDate())
                        .codigoError(0)
                        .mensajeError("")
                        .build());
            }
            return listaUsuarioResMostrarDto;
        }catch (Exception e){
            LOGGER.error("Error buscando todos los usuarios");
            listaUsuarioResMostrarDto.add(UsuarioResMostrarDto.builder()
                                        .codigoError(-1)
                                        .mensajeError("Error buscando todos los usuario "+e.getMessage()).build());
            return listaUsuarioResMostrarDto;
        }

    }

    @Override
    public UsuarioRolDto setAdmin(Integer id) {
        try {
            Usuario usuario = usuarioRepository.findById(id).get();
            if(usuario.getRoles().contains(ERol.ADMIN)){
                LOGGER.info("El usuario ya es ADMIN");
                return UsuarioRolDto.builder().mail("El mail" + usuario.getMail()+" ya es admin").build();
            }
            Set<RoleEntity> roles= usuario.getRoles();
            roles.add(rolesrepository.findByName(ERol.ADMIN).get());
            usuario.setRoles(roles);
            usuarioRepository.save(usuario);
            return UsuarioRolDto.builder()
                    .mail(usuario.getMail())
                    .roles(usuario.getRoles())
                    .build();
        }catch (Exception e){
            LOGGER.error("Error al hacer admin al usuario id: "+id+" "+e.getMessage());
            return UsuarioRolDto.builder().mail("Error "+e.getMessage()).build();
        }
    }
}