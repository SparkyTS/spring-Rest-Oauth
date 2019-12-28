package com.SparkyTS.springRest.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.SparkyTS.springRest.config.DbConfig;

@Configuration
@Import({DbConfig.class})
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception { // @formatter:off
        http
                .anonymous().and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET,"/oauth/authorize", "/api/users/**", "/api/userDetails").permitAll()
                .and().authorizeRequests().anyRequest().authenticated()
                .and()
        		.httpBasic()
        		.and()
        		.csrf().disable();
//        		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//                .and()
//                .authorizeRequests()
//                .anyRequest()
//                .authenticated();
//        http.csrf().disable();
//        http.cors().disable();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean()
            throws Exception {
        return super.authenticationManagerBean();
    }

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
        	.dataSource(dataSource).passwordEncoder(passwordEncoder())
        	.usersByUsernameQuery("select username,password, status from user where username=?")
        	.authoritiesByUsernameQuery(
				"SELECT u.username as username, a.role AS role FROM user u INNER JOIN user_authorities uaj ON uaj.user_id = u.id INNER JOIN authorities a ON a.id = uaj.authority_id where u.username=?");
        
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @SuppressWarnings("deprecation")
//    @Bean
//    public NoOpPasswordEncoder passwordEncoder() {
//        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
//    }
}

