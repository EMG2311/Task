package com.task.demo.service.implementation;

import com.task.demo.model.ERol;
import com.task.demo.model.RoleEntity;
import com.task.demo.model.Usuario;
import com.task.demo.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserDetailServiceImplementation implements UserDetailsService {
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    private static final Logger logger = LoggerFactory.getLogger(UserDetailServiceImplementation.class);
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String mensaje;
        Usuario usuario=  usuarioRepository.findByMail(username)
                .orElse(Usuario.builder()
                        .mail("ERROR")
                        .contraseña("ERROR")
                        .roles(Set.of(RoleEntity.builder().
                                name(ERol.valueOf(ERol.USER.name()))
                                .build()))
                        .build());
        Collection<? extends GrantedAuthority> authoritis = usuario.getRoles()
                .stream()
                .map(rol->new SimpleGrantedAuthority("ROLE_".concat(rol.getName().name())))
                .collect(Collectors.toSet());

        return new User(usuario.getMail(), usuario.getContraseña(),true,true,true,true,
                authoritis);
    }
}
