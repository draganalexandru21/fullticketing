package com.example.serverticketingsystem.user.repository;

import com.example.serverticketingsystem.user.models.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);


    @Query("SELECT u FROM User u WHERE u.mail = ?1")
    Optional<User> findByEmail(String email);


    @Query("SELECT u FROM User u WHERE u.mail = ?1")
    Optional<User> findAllByEmail(String email);


    @Transactional
    @Modifying
    @Query("DELETE FROM User u WHERE u.mail = ?1")
    void deleteAllByMail(String email);

}
