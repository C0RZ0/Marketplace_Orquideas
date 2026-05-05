package com.orquicombeima.proyecto_orquideas.controller;

import com.orquicombeima.proyecto_orquideas.dto.RecomendacionDTO;
import com.orquicombeima.proyecto_orquideas.service.RecomendacionAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/recomendaciones")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMINISTRADOR')") // Solo los usuarios con rol ADMIN pueden acceder a este controlador
public class RecomendacionAdminController {

    private final RecomendacionAdminService recomendacionAdminService;

    @GetMapping
    public ResponseEntity<List<RecomendacionDTO>> listarTodas() {
        return ResponseEntity.ok(recomendacionAdminService.listarTodas());
    }
}
