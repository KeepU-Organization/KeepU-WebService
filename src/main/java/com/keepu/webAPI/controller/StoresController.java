package com.keepu.webAPI.controller;

import com.keepu.webAPI.model.Stores;
import com.keepu.webAPI.service.StoresService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/stores")
public class StoresController {

    private final StoresService service;

    public StoresController(StoresService service) {
        this.service = service;
    }

    @GetMapping
    public List<Stores> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Stores getById(@PathVariable Integer id) {
        return service.findById(id);
    }

    @PostMapping
    public Stores save(@RequestBody Stores s) {
        return service.save(s);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.deleteById(id);
    }
}
