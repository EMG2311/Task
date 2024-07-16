package com.task.demo;

import com.task.demo.model.ERol;
import com.task.demo.model.RoleEntity;
import com.task.demo.model.Usuario;
import com.task.demo.repository.RoleRepository;
import com.task.demo.repository.UsuarioRepository;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;


@SpringBootApplication
public class DemoApplication{

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	UsuarioRepository usuarioRepository;

	@Autowired
	RoleRepository roleUserRepository;

	@Bean
	CommandLineRunner init() {
		return args -> {

			//roleUserRepository.save(RoleEntity.builder().name(ERol.ADMIN).build()) ;
			//roleUserRepository.save(RoleEntity.builder().name(ERol.USER).build()) ;

			/*
			// Ensure roles exist in the database

			Usuario usuario2 = Usuario.builder()
					.mail("germanmontirubioo@gmail.com")
					.contraseña(passwordEncoder.encode("aaaa"))
					.roles(Set.of(RoleEntity.builder().
							name(ERol.valueOf(ERol.USER.name()))
							.build()))
					.build();
			Usuario usuario3 = Usuario.builder()
					.mail("germanmontirubioooo@gmail.com")
					.contraseña(passwordEncoder.encode("aaaaa"))
					.roles(Set.of(RoleEntity.builder().
							name(ERol.valueOf(ERol.ADMIN.name()))
							.build()))
					.build();


			usuarioRepository.save(usuario2);
			usuarioRepository.save(usuario3);
*/
		};
	}
}
