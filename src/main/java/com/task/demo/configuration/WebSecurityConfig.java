package com.task.demo.configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Deshabilitar la configuración de seguridad para todos los endpoints.
        http.authorizeRequests().anyRequest().permitAll();

        // Desactivar la protección CSRF (Cross-Site Request Forgery) para simplificar el ejemplo.
        // En una aplicación real, CSRF debe estar habilitado para proteger contra ataques CSRF.
        http.csrf().disable();
    }
}