package com.fangyu3.webquizengine.repositories;

import com.fangyu3.webquizengine.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByEmail(String userEmail);
}
