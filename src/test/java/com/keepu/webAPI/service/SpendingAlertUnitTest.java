package com.keepu.webAPI.service;

import com.keepu.webAPI.model.SpendingAlert;
import com.keepu.webAPI.repository.SpendingAlertRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.PrintStream;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SpendingAlertUnitTest {

    private SpendingAlertRepository alertRepository;
    private SpendingAlertService spendingAlertService;

    @BeforeEach
    void setUp() {
        alertRepository = mock(SpendingAlertRepository.class);
        spendingAlertService = new SpendingAlertService(alertRepository);
    }

    @Test
    @DisplayName("CP 9.1: Alerta enviada al superar límite")
    void CP_9_1_alertaEnviadaAlSuperarLimite() {
        SpendingAlert alert = SpendingAlert.builder()
                .id(1)
                .userId(10)
                .category("comida")
                .threshold(100.0)
                .active(true)
                .build();

        when(alertRepository.findByUserIdAndCategoryAndActiveTrue(10, "comida"))
                .thenReturn(Optional.of(alert));

        String result = spendingAlertService.checkAndSendAlert(10, "comida", 150.0);

        assertEquals("Alerta enviada con éxito.", result);
    }

    @Test
    @DisplayName("CP 9.2: Falla al enviar la alerta")
    void CP_9_2_falloEnEnvioDeAlerta() {
        SpendingAlert alert = SpendingAlert.builder()
                .id(1)
                .userId(10)
                .category("comida")
                .threshold(100.0)
                .active(true)
                .build();

        when(alertRepository.findByUserIdAndCategoryAndActiveTrue(10, "comida"))
                .thenReturn(Optional.of(alert));
        PrintStream originalOut = System.out;
        PrintStream mockOut = mock(PrintStream.class);
        doThrow(new RuntimeException("Error")).when(mockOut).println(anyString());
        System.setOut(mockOut);

        String result = spendingAlertService.checkAndSendAlert(10, "comida", 150.0);

        assertEquals("Error al enviar la alerta. Por favor, revisa tu conexión o configuración de alertas.", result);
        System.setOut(originalOut);
    }

    @Test
    @DisplayName("CP 9.3: No hay alertas configuradas")
    void CP_9_3_noHayAlertasConfiguradas() {
        when(alertRepository.findByUserIdAndCategoryAndActiveTrue(10, "comida"))
                .thenReturn(Optional.empty());

        String result = spendingAlertService.checkAndSendAlert(10, "comida", 120.0);

        assertEquals("No tienes alertas activas para esta categoría. Te invitamos a configurar una.", result);
    }
}