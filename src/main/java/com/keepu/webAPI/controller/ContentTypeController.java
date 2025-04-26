package com.keepu.webAPI.controller;

import com.keepu.webAPI.model.ContentType;
import com.keepu.webAPI.service.ContentTypeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/content-types")
public class ContentTypeController {

    private final ContentTypeService service;

    public ContentTypeController(ContentTypeService service) {
        this.service = service;
    }

    @GetMapping
    public List<ContentType> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ContentType getById(@PathVariable Integer id) {
        return service.findById(id);
    }

    @PostMapping
    public ContentType save(@RequestBody ContentType contentType) {
        return service.save(contentType);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.deleteById(id);
    }
}
