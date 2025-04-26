package com.keepu.webAPI.controller;

import com.keepu.webAPI.model.Children;
import com.keepu.webAPI.service.ChildrenService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/children")
public class ChildrenController {

    private final ChildrenService childrenService;

    public ChildrenController(ChildrenService childrenService) {
        this.childrenService = childrenService;
    }

    @GetMapping
    public List<Children> getAll() {
        return childrenService.findAll();
    }

    @GetMapping("/{id}")
    public Children getById(@PathVariable Integer id) {
        return childrenService.findById(id);
    }

    @PostMapping
    public Children save(@RequestBody Children child) {
        return childrenService.save(child);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        childrenService.deleteById(id);
    }
}
