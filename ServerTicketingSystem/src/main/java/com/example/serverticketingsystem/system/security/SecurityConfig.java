package com.example.serverticketingsystem.system.security;

import com.example.serverticketingsystem.system.jwt.JWTAcessDeniedHandler;
import com.example.serverticketingsystem.system.jwt.JWTAuthEntryPoint;
import com.example.serverticketingsystem.system.jwt.JWTAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.example.serverticketingsystem.utils.Utils.PUBLIC_URLS;
import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    private JWTAuthorizationFilter jwtAuthorizationFilter;
    private JWTAcessDeniedHandler   jwtAcessDeniedHandler;
    private JWTAuthEntryPoint jwtentryPoint;
    private UserDetailsService userDetailService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    public SecurityConfig(JWTAuthorizationFilter jwtAuthorizationFilter, JWTAcessDeniedHandler jwtAcessDeniedHandler, JWTAuthEntryPoint jwtentryPoint, UserDetailsService userDetailService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.jwtAuthorizationFilter = jwtAuthorizationFilter;
        this.jwtAcessDeniedHandler = jwtAcessDeniedHandler;
        this.jwtentryPoint = jwtentryPoint;
        this.userDetailService = userDetailService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailService);
        http.csrf(crsf -> crsf.disable()).cors(withDefaults());
        http.sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(STATELESS));
        http.exceptionHandling(exceptionHandling -> exceptionHandling.accessDeniedHandler(jwtAcessDeniedHandler).authenticationEntryPoint(jwtentryPoint));
        http.authorizeRequests(authorizeRequests -> authorizeRequests.requestMatchers("api/v1/login").permitAll().requestMatchers(PUBLIC_URLS).permitAll().anyRequest().authenticated());
        http.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
