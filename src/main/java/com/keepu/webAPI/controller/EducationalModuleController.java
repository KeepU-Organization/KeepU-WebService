package com.keepu.webAPI.controller;

import com.keepu.webAPI.model.EducationalModule;
import com.keepu.webAPI.service.EducationalModuleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/education")
public class EducationalModuleController {
    private final EducationalModuleService service;

    public EducationalModuleController(EducationalModuleService service) {
        this.service = service;
    }

    @GetMapping("/modules")
    public ResponseEntity<?> getAllModules() {
        List<EducationalModule> modules = service.getAllModules();
        if (modules.isEmpty()) {
            return ResponseEntity.ok("No hay módulos disponibles por el momento. Vuelve más tarde.");
        }
        return ResponseEntity.ok(modules);
    }

    @GetMapping("/modules/{id}")
    public ResponseEntity<Object> getModule(@PathVariable Integer id) {
        return service.getModuleById(id)
                .<ResponseEntity<Object>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(404).body("No se encontró el módulo solicitado."));
    }

    @PostMapping("/modules")
    public ResponseEntity<EducationalModule> addModule(@RequestBody EducationalModule module) {
        EducationalModule savedModule = service.addModule(module);
        return ResponseEntity.status(201).body(savedModule);
    }

}
