package com.example.sar.repo;

import com.example.sar.domain.UserLogin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<UserLogin, Long> {
    UserLogin findByUsername(String username);
    List<UserLogin> findAll();
}
