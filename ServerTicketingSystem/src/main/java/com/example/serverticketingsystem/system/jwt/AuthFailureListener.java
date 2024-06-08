package com.example.serverticketingsystem.system.jwt;

import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class AuthFailureListener {

    private static final Logger logger = LoggerFactory.getLogger(AuthFailureListener.class);

    @EventListener
    public void onAuthenticationFailure(AuthenticationFailureBadCredentialsEvent event) {
        Object principal = event.getAuthentication().getPrincipal();
        if (principal instanceof String) {
            String username = (String) principal;
            // Simplu logare a eșecului autentificării
            logger.warn("Authentication failed for user: {}", username);
        }
    }
}