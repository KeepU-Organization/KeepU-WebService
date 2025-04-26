package com.keepu.webAPI.controller;

import com.keepu.webAPI.model.UserTypes;
import com.keepu.webAPI.service.UserTypesService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user-types")
public class UserTypesController {

    private final UserTypesService userTypesService;

    public UserTypesController(UserTypesService userTypesService) {
        this.userTypesService = userTypesService;
    }

    @GetMapping
    public List<UserTypes> getAll() {
        return userTypesService.findAll();
    }

    @GetMapping("/{id}")
    public UserTypes getById(@PathVariable Integer id) {
        return userTypesService.findById(id);
    }

    @PostMapping
    public UserTypes save(@RequestBody UserTypes type) {
        return userTypesService.save(type);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        userTypesService.deleteById(id);
    }
}
