package com.sneekicks.sneekicks.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sneekicks.sneekicks.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Buscar usuario por email
    User findByEmail(String email);
}
