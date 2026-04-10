package com.orquicombeima.proyecto_orquideas.dto;

import com.orquicombeima.proyecto_orquideas.model.enums.Rol;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Lo que devuelve GET /api/auth/me: datos básicos del usuario autenticado
@Data
@AllArgsConstructor
@NoArgsConstructor

public class UsuarioAuthDTO {
    private Long idUsuario;
    private String nombre;
    private String correo;
    private Rol rol;
}