package com.keepu.webAPI.controller;

import com.keepu.webAPI.model.ParentChildren;
import com.keepu.webAPI.model.ParentChildrenId;
import com.keepu.webAPI.service.ParentChildrenService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/parent-children")
public class ParentsChildrenController {

    private final ParentChildrenService service;

    public ParentsChildrenController(ParentChildrenService service) {
        this.service = service;
    }

    @GetMapping
    public List<ParentChildren> getAll() {
        return service.findAll();
    }

    @GetMapping("/{parentId}/{childId}")
    public ParentChildren getById(@PathVariable Integer parentId, @PathVariable Integer childId) {
        return service.findById(new ParentChildrenId(parentId, childId));
    }

    @PostMapping
    public ParentChildren save(@RequestBody ParentChildren pc) {
        return service.save(pc);
    }

    @DeleteMapping("/{parentId}/{childId}")
    public void delete(@PathVariable Integer parentId, @PathVariable Integer childId) {
        service.deleteById(new ParentChildrenId(parentId, childId));
    }
}
