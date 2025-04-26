package com.keepu.webAPI.controller;

import com.keepu.webAPI.model.UserContentProgress;
import com.keepu.webAPI.service.UserContentProgressService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user-content-progress")
public class UserContentProgressController {

    private final UserContentProgressService service;

    public UserContentProgressController(UserContentProgressService service) {
        this.service = service;
    }

    @GetMapping
    public List<UserContentProgress> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public UserContentProgress getById(@PathVariable Integer id) {
        return service.findById(id);
    }

    @PostMapping
    public UserContentProgress save(@RequestBody UserContentProgress progress) {
        return service.save(progress);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.deleteById(id);
    }
}
