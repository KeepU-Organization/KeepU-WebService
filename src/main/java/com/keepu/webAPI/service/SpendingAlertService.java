// src/main/java/com/keepu/webAPI/service/SpendingAlertService.java
package com.keepu.webAPI.service;

import com.keepu.webAPI.model.SpendingAlert;
import com.keepu.webAPI.repository.SpendingAlertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SpendingAlertService {

    private final SpendingAlertRepository alertRepository;

    public String checkAndSendAlert(Integer userId, String category, Double totalSpent) {
        return alertRepository.findByUserIdAndCategoryAndActiveTrue(userId, category)
                .map(alert -> {
                    if (totalSpent >= alert.getThreshold()) {
                        try {
                            System.out.println("¡Alerta! Has alcanzado el límite de gasto en " + category);
                            return "Alerta enviada con éxito.";
                        } catch (Exception e) {
                            return "Error al enviar la alerta. Por favor, revisa tu conexión o configuración de alertas.";
                        }
                    }
                    return "No se ha alcanzado el umbral de alerta.";
                })
                .orElse("No tienes alertas activas para esta categoría. Te invitamos a configurar una.");
    }
}