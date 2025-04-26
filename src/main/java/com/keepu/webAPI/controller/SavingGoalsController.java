package com.keepu.webAPI.controller;

import com.keepu.webAPI.model.SavingGoals;
import com.keepu.webAPI.service.SavingGoalsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/saving-goals")
public class SavingGoalsController {

    private final SavingGoalsService service;

    public SavingGoalsController(SavingGoalsService service) {
        this.service = service;
    }

    @GetMapping
    public List<SavingGoals> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public SavingGoals getById(@PathVariable Integer id) {
        return service.findById(id);
    }

    @PostMapping
    public SavingGoals save(@RequestBody SavingGoals goal) {
        return service.save(goal);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.deleteById(id);
    }
}
