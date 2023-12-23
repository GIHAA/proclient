package com.full.stack.demo.core.repository;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDao {

    Optional<user> findByEmail()
}
