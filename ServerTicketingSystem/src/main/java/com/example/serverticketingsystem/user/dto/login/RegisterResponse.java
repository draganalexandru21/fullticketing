package com.example.serverticketingsystem.user.dto.login;

public record RegisterResponse(String token,String username, String mail, String role, boolean active) {
}
