package com.keepu.webAPI.controller;

import com.keepu.webAPI.model.Parent;
import com.keepu.webAPI.service.ParentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parents")
public class ParentController {

    private final ParentService parentService;

    public ParentController(ParentService parentService) {
        this.parentService = parentService;
    }

    @GetMapping
    public List<Parent> getAll() {
        return parentService.findAll();
    }

    @GetMapping("/{id}")
    public Parent getById(@PathVariable Integer id) {
        return parentService.findById(id);
    }

    @PostMapping
    public Parent save(@RequestBody Parent parent) {
        return parentService.save(parent);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        parentService.deleteById(id);
    }
}
