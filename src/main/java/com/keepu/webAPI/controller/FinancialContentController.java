package com.keepu.webAPI.controller;

import com.keepu.webAPI.model.FinancialContent;
import com.keepu.webAPI.service.FinancialContentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/financial-content")
public class FinancialContentController {

    private final FinancialContentService service;

    public FinancialContentController(FinancialContentService service) {
        this.service = service;
    }

    @GetMapping
    public List<FinancialContent> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public FinancialContent getById(@PathVariable Integer id) {
        return service.findById(id);
    }

    @PostMapping
    public FinancialContent save(@RequestBody FinancialContent content) {
        return service.save(content);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.deleteById(id);
    }
}
