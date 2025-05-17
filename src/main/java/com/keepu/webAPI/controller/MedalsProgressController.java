// MedalsProgressController.java
package com.keepu.webAPI.controller;

import com.keepu.webAPI.dto.request.CreateMedalsProgressRequest;
import com.keepu.webAPI.dto.response.MedalsProgressResponse;
import com.keepu.webAPI.service.MedalsProgressService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/progress")
public class MedalsProgressController {

    private final MedalsProgressService medalsProgressService;

    public MedalsProgressController(MedalsProgressService medalsProgressService) {
        this.medalsProgressService = medalsProgressService;
    }

    @PostMapping
    public ResponseEntity<?> getMedalsProgress(@RequestBody CreateMedalsProgressRequest request) {
        try {
            MedalsProgressResponse response = medalsProgressService.getMedalsProgress(request);

            boolean noMedals = response.medals().isEmpty();
            boolean noNews = response.news().isEmpty();

            if (noMedals && noNews) {
                return ResponseEntity.status(404).body("No se encontró progreso de medallas ni noticias para el usuario.");
            }

            // Armamos una respuesta detallada si falta alguno de los dos
            StringBuilder info = new StringBuilder();
            if (noMedals) info.append("No hay medallas disponibles para este usuario. ");
            if (noNews) info.append("No hay noticias disponibles para este usuario.");

            // Si hay al menos uno de los dos, lo devolvemos junto con el mensaje si aplica
            if (info.length() > 0) {
                return ResponseEntity.ok().body(new Object() {
                    public final String mensaje = info.toString().trim();
                    public final MedalsProgressResponse datos = response;
                });
            }

            // Si tiene ambos, se retorna tal cual
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Ocurrió un error al obtener el progreso de medallas y noticias: " + e.getMessage());
        }
    }
}
