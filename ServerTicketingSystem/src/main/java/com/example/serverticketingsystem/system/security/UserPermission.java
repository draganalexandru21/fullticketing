package com.example.serverticketingsystem.system.security;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum UserPermission {
    USER_READ("user:read"),USER_WRITE("user:write"),USER_DELETE("user:delete");
    private final String permission;
    public String getPermission(){
        return permission;
    }
}
