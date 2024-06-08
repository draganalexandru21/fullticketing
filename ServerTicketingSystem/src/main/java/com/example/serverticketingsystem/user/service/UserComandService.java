package com.example.serverticketingsystem.user.service;

import com.example.serverticketingsystem.user.dto.UserDTO;
import com.example.serverticketingsystem.user.models.User;
import com.example.serverticketingsystem.user.repository.UserRepo;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class UserComandService {
    private UserRepo repo;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public void addUser(UserDTO userDTO){
        Optional<User> user=repo.findByEmail(userDTO.mail());
        if(user.isEmpty()){
            User x= User.builder().mail(userDTO.mail())
                    .role(userDTO.role())
                    .active(userDTO.active())
                    .createdAt(userDTO.createdDate())
                    .build();
            repo.saveAndFlush(x);
        }
        else{
            throw new IllegalStateException("User already exists");
        }
    }

    public void updateUser(String email,UserDTO userDTO){
        Optional<User> user=repo.findByEmail(email);
        if(user.isPresent()){
            User us=user.get();
            us.setMail(userDTO.mail());
            us.setRole(userDTO.role());
            us.setActive(userDTO.active());
            us.setCreatedAt(userDTO.createdDate());
            repo.saveAndFlush(us);
        }
    }
    void deleteUser(String email){
        Optional<User> user=repo.findByEmail(email);
        if(user.isPresent()){
            repo.delete(user.get());
        }
        else{
            throw new IllegalStateException("User does not exist");
        }
    }
}
