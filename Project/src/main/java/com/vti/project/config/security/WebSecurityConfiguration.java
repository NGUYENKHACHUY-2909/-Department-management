package com.vti.project.config.security;

import com.vti.project.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter{
    @Autowired
    private IAccountService service;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(service).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST,"/admin/departments").hasAnyAuthority("ADMIN", "MANAGER")
                .antMatchers(HttpMethod.PUT,"/admin/departments").hasAnyAuthority("ADMIN", "MANAGER")
                .antMatchers(HttpMethod.PUT,"/admin/departments/*").hasAnyAuthority("ADMIN", "MANAGER")
                .antMatchers(HttpMethod.DELETE,"/admin/departments").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE,"/admin/departments/*").hasAnyAuthority("ADMIN")
                .anyRequest().authenticated()
                .and().httpBasic()
                .and().csrf().disable();
    }
}
