package com.orquicombeima.proyecto_orquideas.controller;

import com.orquicombeima.proyecto_orquideas.model.Usuario;
import com.orquicombeima.proyecto_orquideas.model.enums.Rol;
import com.orquicombeima.proyecto_orquideas.repository.UsuarioRepository;
import com.orquicombeima.proyecto_orquideas.security.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = AuthController.class)
@AutoConfigureMockMvc(addFilters = false)  // desactivamos filtros; @WithMockUser inyecta el Authentication directo
class AuthControllerTest {

    @Autowired private MockMvc mockMvc;

    @MockitoBean private UsuarioRepository usuarioRepository;
    @MockitoBean private JwtService jwtService;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId(42L);
        usuario.setNombre("Rosa");
        usuario.setEmail("rosa@test.com");
        usuario.setRol(Rol.CLIENTE);
    }

    @Test
    @WithMockUser(username = "rosa@test.com", roles = "CLIENTE")
    void GET_me_devuelveDatosDelUsuarioAutenticado() throws Exception {
        when(usuarioRepository.findByEmail("rosa@test.com")).thenReturn(Optional.of(usuario));

        mockMvc.perform(get("/api/auth/me"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idUsuario").value(42))
                .andExpect(jsonPath("$.nombre").value("Rosa"))
                .andExpect(jsonPath("$.correo").value("rosa@test.com"))
                .andExpect(jsonPath("$.rol").value("CLIENTE"));
    }

    @Test
    @WithMockUser(username = "admin@test.com", roles = "ADMINISTRADOR")
    void GET_me_paraAdmin_devuelveRolADMINISTRADOR() throws Exception {
        Usuario admin = new Usuario();
        admin.setId(1L);
        admin.setNombre("Admin");
        admin.setEmail("admin@test.com");
        admin.setRol(Rol.ADMINISTRADOR);

        when(usuarioRepository.findByEmail("admin@test.com")).thenReturn(Optional.of(admin));

        mockMvc.perform(get("/api/auth/me"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rol").value("ADMINISTRADOR"));
    }

    @Test
    @WithMockUser(username = "fantasma@test.com", roles = "CLIENTE")
    void GET_me_emailNoEnBD_devuelve500() throws Exception {
        // El controller lanza RuntimeException cuando el email del token no aparece en la BD
        // (caso raro pero posible: borraron el usuario después de emitir el JWT)
        when(usuarioRepository.findByEmail("fantasma@test.com")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/auth/me"))
                .andExpect(status().isInternalServerError());
    }
}