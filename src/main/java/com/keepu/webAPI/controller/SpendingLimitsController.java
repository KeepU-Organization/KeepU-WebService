package com.keepu.webAPI.controller;

import com.keepu.webAPI.model.SpendingLimits;
import com.keepu.webAPI.service.SpendingLimitsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/spending-limits")
public class SpendingLimitsController {

    private final SpendingLimitsService service;

    public SpendingLimitsController(SpendingLimitsService service) {
        this.service = service;
    }

    @GetMapping
    public List<SpendingLimits> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public SpendingLimits getById(@PathVariable Integer id) {
        return service.findById(id);
    }

    @PostMapping
    public SpendingLimits save(@RequestBody SpendingLimits limit) {
        return service.save(limit);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.deleteById(id);
    }
}
