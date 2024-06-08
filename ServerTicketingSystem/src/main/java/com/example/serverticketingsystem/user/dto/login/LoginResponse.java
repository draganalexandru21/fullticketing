package com.example.serverticketingsystem.user.dto.login;

public record LoginResponse(String token, String username, String mail, String role, boolean active) {
}
