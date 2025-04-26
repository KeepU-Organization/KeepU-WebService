package com.keepu.webAPI.service;

import com.keepu.webAPI.model.UserTypes;
import com.keepu.webAPI.repository.UserTypesRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserTypesService {

    private final UserTypesRepository userTypesRepository;

    public UserTypesService(UserTypesRepository userTypesRepository) {
        this.userTypesRepository = userTypesRepository;
    }

    public List<UserTypes> findAll() {
        return userTypesRepository.findAll();
    }

    public UserTypes findById(Integer id) {
        return userTypesRepository.findById(id).orElse(null);
    }

    public UserTypes save(UserTypes type) {
        return userTypesRepository.save(type);
    }

    public void deleteById(Integer id) {
        userTypesRepository.deleteById(id);
    }
}
