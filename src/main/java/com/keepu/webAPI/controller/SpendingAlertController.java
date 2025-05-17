package com.keepu.webAPI.controller;

import com.keepu.webAPI.model.SpendingAlert;
import com.keepu.webAPI.repository.SpendingAlertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alerts")
@RequiredArgsConstructor
public class SpendingAlertController {

    private final SpendingAlertRepository alertRepository;

    @PostMapping
    public SpendingAlert createAlert(@RequestBody SpendingAlert alert) {
        alert.setActive(true);
        return alertRepository.save(alert);
    }

    @GetMapping("/{userId}")
    public List<SpendingAlert> getAlerts(@PathVariable Integer userId) {
        return alertRepository.findAll()
                .stream()
                .filter(a -> a.getUserId().equals(userId) && Boolean.TRUE.equals(a.getActive()))
                .toList();
    }

    @PutMapping("/{id}/deactivate")
    public SpendingAlert deactivateAlert(@PathVariable Long id) {
        SpendingAlert alert = alertRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alerta no encontrada"));
        alert.setActive(false);
        return alertRepository.save(alert);
    }
}