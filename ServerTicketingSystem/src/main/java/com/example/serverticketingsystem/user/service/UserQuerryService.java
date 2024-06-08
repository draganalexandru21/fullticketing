package com.example.serverticketingsystem.user.service;

import com.example.serverticketingsystem.user.models.User;
import com.example.serverticketingsystem.user.repository.UserRepo;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserQuerryService {
    UserRepo userRepo;


    public UserQuerryService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public Optional<User> findByMail(String mail){
        Optional<User> user=userRepo.findByEmail(mail);
        if(user.isPresent()){
            return user;
        }else {
            throw new IllegalStateException("User does not exist");
        }
    }
}
