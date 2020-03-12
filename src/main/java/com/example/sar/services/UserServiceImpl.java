package com.example.sar.services;

import com.example.sar.domain.Role;
import com.example.sar.domain.UserLogin;
import com.example.sar.repo.RoleRepository;
import com.example.sar.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public void save(UserLogin user){
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        //user.setRoles(new HashSet<>(roleRepository.findAll()));
        HashSet<Role> hashset =  new HashSet<Role>();
        final boolean role_user = hashset.add(roleRepository.findRoleByName("ROLE_USER"));
        user.setRoles(hashset);
        userRepository.save(user);
    }

    public  UserLogin findByUsername(String username){
        return userRepository.findByUsername(username);
    }
}
