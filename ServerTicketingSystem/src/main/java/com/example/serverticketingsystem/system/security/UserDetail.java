package com.example.serverticketingsystem.system.security;

import com.example.serverticketingsystem.user.models.User;
import com.example.serverticketingsystem.user.repository.UserRepo;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

@Component
public class UserDetail implements UserDetailsService{
private UserRepo userRepo;
public UserDetail(UserRepo userRepo){
this.userRepo = userRepo;
}
    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
         Optional<User> user = userRepo.findByEmail(mail);
         if(user.isPresent()){
             User userOptional=user.get();
             return userOptional;
         }

         throw new UsernameNotFoundException("User not found");

    }
}
