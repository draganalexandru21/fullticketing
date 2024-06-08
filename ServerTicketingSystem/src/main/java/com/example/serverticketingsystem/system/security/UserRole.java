package com.example.serverticketingsystem.system.security;

import com.google.common.collect.Sets;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
public enum UserRole {
    ADMIN(Sets.newHashSet(UserPermission.USER_READ, UserPermission.USER_WRITE, UserPermission.USER_DELETE)),
    EMPLOYEE(Sets.newHashSet(UserPermission.USER_READ, UserPermission.USER_WRITE)),
    ANALYST(Sets.newHashSet(UserPermission.USER_READ));
    private final Set<UserPermission> permissionSet;
    public Set<UserPermission> getPermissionSet(){
        return permissionSet;
    }

    public Set<SimpleGrantedAuthority> grantedAuthority(){
        Set<SimpleGrantedAuthority> permissions = getPermissionSet().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return permissions;
    }
}
