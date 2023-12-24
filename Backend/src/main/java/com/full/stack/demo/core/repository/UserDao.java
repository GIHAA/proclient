package com.full.stack.demo.core.repository;

import com.full.stack.demo.core.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDao extends JpaRepository<User, Long> {

    // Since email is unique, we'll find users by email
    Optional<User> findByEmail(String email);
}
