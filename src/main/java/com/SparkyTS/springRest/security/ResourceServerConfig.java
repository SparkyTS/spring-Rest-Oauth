package com.SparkyTS.springRest.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    String [] ignoredPaths = new String[]{
            "/api/users"
    };

    @Override
    public void configure(HttpSecurity http) throws Exception{

        http.authorizeRequests()
            .antMatchers(ignoredPaths).permitAll()
            .anyRequest().authenticated()
        .and()
            .httpBasic();   
    }
}