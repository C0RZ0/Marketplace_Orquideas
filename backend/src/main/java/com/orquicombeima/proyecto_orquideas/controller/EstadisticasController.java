package com.orquicombeima.proyecto_orquideas.controller;

import com.orquicombeima.proyecto_orquideas.dto.EstadisticasDTO;
import com.orquicombeima.proyecto_orquideas.service.EstadisticasService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMINISTRADOR')")
public class EstadisticasController {

    private final EstadisticasService estadisticasService;

    @GetMapping("/estadisticas")
    public ResponseEntity<EstadisticasDTO> obtenerEstadisticas() {
        return ResponseEntity.ok(estadisticasService.obtenerEstadisticas());
    }

}
