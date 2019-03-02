package com.can.core.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;

@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private TokenProvider jwtTokenProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests()//
                .antMatchers("/contact/signup").permitAll()//
                .antMatchers("/contact/login").permitAll()//
                .antMatchers("/contact").permitAll()//
                .antMatchers("/contact/search").permitAll()//
                // Disallow everything else..
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationFailureHandler());


        http.apply(new SecurityFilterConfigurer(jwtTokenProvider));

    }

    @Bean
    public AuthenticationEntryPoint authenticationFailureHandler() {
        return new APIAuthenticationEntryPoint();
    }



}