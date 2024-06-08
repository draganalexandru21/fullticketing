package com.example.serverticketingsystem.user.web;

import com.example.serverticketingsystem.system.jwt.JWTTokenProvider;
import com.example.serverticketingsystem.user.dto.UserDTO;
import com.example.serverticketingsystem.user.dto.login.LoginResponse;
import com.example.serverticketingsystem.user.models.User;
import com.example.serverticketingsystem.user.service.UserComandService;
import com.example.serverticketingsystem.user.service.UserQuerryService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import static com.example.serverticketingsystem.utils.Utils.JWT_TOKEN_HEADER;
import static com.example.serverticketingsystem.utils.Utils.TOKEN_PREFIX;

import static org.springframework.http.HttpStatus.OK;
@RestController
@AllArgsConstructor
@CrossOrigin
@RequestMapping("/api/v1/")
public class UserRest {
    private UserComandService userCommandService;
    private UserQuerryService userQueryService;
    private AuthenticationManager authenticationManager;
    private JWTTokenProvider jwtTokenProvider;
    private static final Logger logger= LoggerFactory.getLogger(UserRest.class);
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody UserDTO user) {
        logger.info("Attempting to login user: {}", user.mail());
        try {
            authenticate(user.mail(), user.password());
            User loginUser = userQueryService.findByMail(user.mail()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
            User userPrincipal = new User(loginUser.getId(), loginUser.getUsername(), loginUser.getMail(), loginUser.getRole(), loginUser.getCreatedAt(), loginUser.isActive());
            userPrincipal.setPassword(loginUser.getPassword());
            HttpHeaders jwtHeader = getJwtHeader(userPrincipal);
            LoginResponse loginResp = new LoginResponse(jwtHeader.getFirst(JWT_TOKEN_HEADER), userPrincipal.getUsername(), userPrincipal.getMail(), userPrincipal.getRole().name(), userPrincipal.isActive());
            return new ResponseEntity<>(loginResp, jwtHeader, OK);
        } catch (Exception e) {
            logger.error("Login failed: ", e);
            throw e;
        }
    }

    private void authenticate(String email,String password){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email,password));
    }

    private HttpHeaders getJwtHeader(User user){
        HttpHeaders headers=new HttpHeaders();
        headers.add(JWT_TOKEN_HEADER,TOKEN_PREFIX+jwtTokenProvider.generateJWTToken(user));
        return headers;
    }
}
