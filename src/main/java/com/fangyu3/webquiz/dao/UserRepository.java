package com.fangyu3.webquiz.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fangyu3.webquiz.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String userEmail);
}
