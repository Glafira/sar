package com.example.sar.services;

import com.example.sar.domain.UserLogin;

public interface UserService {
    public void save(UserLogin user);
    public  UserLogin findByUsername(String username);
}
