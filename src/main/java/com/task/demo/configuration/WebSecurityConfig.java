package com.task.demo.configuration;

import com.task.demo.configuration.Filters.JwtAuthenticationFilter;
import com.task.demo.configuration.Filters.JwtAuthorizationFilter;
import com.task.demo.configuration.JWT.JwtUtils;
import com.task.demo.service.implementation.UserDetailServiceImplementation;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)//Permito utilizar la anotacion @PreAuthorization en los controllers
public class WebSecurityConfig {
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    public UserDetailServiceImplementation userDetailServiceImplementation;
    @Autowired
    public JwtAuthorizationFilter jwtAuthorizationFilter;

    /*configuracion1
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws  Exception{
        return http.authorizeHttpRequests()
                .antMatchers("/usuario/listar").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().permitAll().and().build();

    }
     */

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception{
        JwtAuthenticationFilter jwtAuthenticationFilter= new JwtAuthenticationFilter(jwtUtils);
        jwtAuthenticationFilter.setAuthenticationManager(authenticationManager);
        return http
                .csrf(confi->confi.disable())
                .authorizeHttpRequests(auth->
        {
            auth.antMatchers("/usuario/crear").permitAll();
            auth.anyRequest().authenticated();

        })
                .sessionManagement(session->{
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                .addFilter(jwtAuthenticationFilter)
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }


    public AuthenticationSuccessHandler successHandler(){
        return ((request, response, authentication) -> {
            response.sendRedirect("/task/listar"); //si se loggea correctamente se va a este endpoint
        });
    }


    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    AuthenticationManager authenticationManager(HttpSecurity httpSecurity, PasswordEncoder passwordEncoder) throws Exception {
        return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailServiceImplementation) //Se le pasa el usuario que vamos a autenticar
                .passwordEncoder(passwordEncoder)
                .and().build();
    }

}