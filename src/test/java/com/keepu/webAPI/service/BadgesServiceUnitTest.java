// src/test/java/com/keepu/webAPI/service/BadgesServiceUnitTest.java
package com.keepu.webAPI.service;

import com.keepu.webAPI.exception.ProgresoNoEncontradoException;
import com.keepu.webAPI.mapper.BadgesMapper;
import com.keepu.webAPI.repository.BadgesRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class BadgesServiceUnitTest {

    private final BadgesRepository badgesRepository = Mockito.mock(BadgesRepository.class);
    private final BadgesMapper badgesMapper = Mockito.mock(BadgesMapper.class);
    private final BadgesService badgesService = new BadgesService(badgesRepository, badgesMapper);

    @Test
    @DisplayName("CP 8.1 - Mostrar progreso de medallas")
    void showBadgeProgress_withProgress_shouldReturnList() {
        List<String> result = badgesService.getBadgeProgress(1L);
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains("Ahorro: 80%"));
        assertTrue(result.contains("Gasto: 60%"));
    }

    @Test
    @DisplayName("CP 8.2 - Mostrar progreso vacío")
    void showBadgeProgress_noProgress_shouldThrowException() {
        ProgresoNoEncontradoException ex = assertThrows(
                ProgresoNoEncontradoException.class,
                () -> badgesService.getBadgeProgress(2L)
        );
        assertEquals("No hay progreso aún, ¡Sigue Aprendiendo!", ex.getMessage());
    }

    @Test
    @DisplayName("CP 8.3 - Error al mostrar progreso")
    void showBadgeProgress_error_shouldThrowRuntimeException() {
        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> badgesService.getBadgeProgress(3L)
        );
        assertEquals("Error al cargar el progreso. Intentalo mas tarde", ex.getMessage());
    }
}