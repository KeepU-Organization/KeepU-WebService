package com.keepu.webAPI.controller;

import com.keepu.webAPI.model.InvitationCodes;
import com.keepu.webAPI.service.InvitationCodesService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/invitation-codes")
public class InvitationCodesController {

    private final InvitationCodesService service;

    public InvitationCodesController(InvitationCodesService service) {
        this.service = service;
    }

    @GetMapping
    public List<InvitationCodes> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public InvitationCodes getById(@PathVariable Integer id) {
        return service.findById(id);
    }

    @PostMapping
    public InvitationCodes save(@RequestBody InvitationCodes code) {
        return service.save(code);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.deleteById(id);
    }
}
