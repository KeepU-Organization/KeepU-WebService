package com.keepu.webAPI.service;

import com.keepu.webAPI.model.EducationalModule;
import com.keepu.webAPI.repository.EducationalModuleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EducationalModuleServiceUnitTest {

    private EducationalModuleRepository repository;
    private EducationalModuleService service;

    @BeforeEach
    void setUp() {
        repository = mock(EducationalModuleRepository.class);
        service = new EducationalModuleService(repository);
    }

    // ---------------- ESCENARIO 1 ----------------
    @Test
    @DisplayName("CP01 - Acceso exitoso a un módulo educativo")
    void getModuleById_shouldReturnModule_whenExists() {
        // Arrange
        Integer moduleId = 1;
        EducationalModule module = new EducationalModule();
        module.setId(moduleId);
        module.setTitle("Conceptos básicos de ahorro");
        module.setContent("Contenido educativo sobre ahorro...");

        when(repository.findById(moduleId)).thenReturn(Optional.of(module));

        // Act
        Optional<EducationalModule> result = service.getModuleById(moduleId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Conceptos básicos de ahorro", result.get().getTitle());
        assertEquals("Contenido educativo sobre ahorro...", result.get().getContent());
        verify(repository, times(1)).findById(moduleId);
    }

    // ---------------- ESCENARIO 2 ----------------
    @Test
    @DisplayName("CP02 - Error técnico al acceder al contenido del módulo")
    void getModuleById_shouldThrowException_whenTechnicalErrorOccurs() {
        // Arrange
        Integer moduleId = 99;
        when(repository.findById(moduleId)).thenThrow(new RuntimeException("Error técnico al cargar módulo"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            service.getModuleById(moduleId);
        });

        assertEquals("Error técnico al cargar módulo", exception.getMessage());
        verify(repository, times(1)).findById(moduleId);
    }

    // ---------------- ESCENARIO 3 ----------------
    @Test
    @DisplayName("CP03 - No hay módulos educativos cargados")
    void getAllModules_shouldReturnEmptyList_whenNoModulesExist() {
        // Arrange
        when(repository.findAll()).thenReturn(Collections.emptyList());

        // Act
        List<EducationalModule> result = service.getAllModules();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty(), "La lista debe estar vacía si no hay módulos disponibles");
        verify(repository, times(1)).findAll();
    }
}
