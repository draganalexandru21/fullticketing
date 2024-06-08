package com.example.serverticketingsystem.user.dto;

import com.example.serverticketingsystem.system.security.UserRole;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Builder
public record UserDTO(String username, String mail, String password,UserRole role, boolean active, LocalDateTime createdDate, LocalDateTime updatedDate) {
            public UserDTO(String username,String mail,String password,UserRole role,boolean active,LocalDateTime createdDate,LocalDateTime updatedDate){
                this.username=username;
                this.mail=mail;
                this.password=password;
                this.role=role;
                this.active=active||true;
                this.createdDate=(createdDate==null)?LocalDateTime.now():createdDate;
                this.updatedDate=updatedDate;
            }
}