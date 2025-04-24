package com.keepu.webAPI.service;

import com.keepu.webAPI.model.UserEntity;
import com.keepu.webAPI.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public UserEntity getUserById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    public UserEntity saveUser(UserEntity user) {
        return userRepository.save(user);
    }

    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }
}