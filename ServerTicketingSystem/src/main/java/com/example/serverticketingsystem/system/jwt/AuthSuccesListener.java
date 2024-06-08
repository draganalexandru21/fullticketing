package com.example.serverticketingsystem.system.jwt;

import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class AuthSuccesListener {

    private static final Logger logger = LoggerFactory.getLogger(AuthSuccesListener.class);

    @EventListener
    public void onAuthenticationSuccess(AuthenticationSuccessEvent event) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Object principal = authentication.getPrincipal();
        if(principal instanceof UserDetails) {
            UserDetails user = (UserDetails) principal;
            logger.info("Authentication successful for user: {}", user.getUsername());
        }
    }
}
