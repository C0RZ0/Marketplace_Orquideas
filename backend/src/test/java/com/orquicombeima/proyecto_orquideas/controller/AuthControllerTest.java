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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
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

    // Helper: arma un Authentication de prueba con email + rol, listo para inyectar al controller
    private UsernamePasswordAuthenticationToken authFor(String email, String rol) {
        return new UsernamePasswordAuthenticationToken(
                email, null, List.of(new SimpleGrantedAuthority("ROLE_" + rol)));
    }

    @Test
    void GET_me_devuelveDatosDelUsuarioAutenticado() throws Exception {
        when(usuarioRepository.findByEmail("rosa@test.com")).thenReturn(Optional.of(usuario));

        mockMvc.perform(get("/api/auth/me")
                        .with(authentication(authFor("rosa@test.com", "CLIENTE"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idUsuario").value(42))
                .andExpect(jsonPath("$.nombre").value("Rosa"))
                .andExpect(jsonPath("$.correo").value("rosa@test.com"))
                .andExpect(jsonPath("$.rol").value("CLIENTE"));
    }

    @Test
    void GET_me_paraAdmin_devuelveRolADMINISTRADOR() throws Exception {
        Usuario admin = new Usuario();
        admin.setId(1L);
        admin.setNombre("Admin");
        admin.setEmail("admin@test.com");
        admin.setRol(Rol.ADMINISTRADOR);

        when(usuarioRepository.findByEmail("admin@test.com")).thenReturn(Optional.of(admin));

        mockMvc.perform(get("/api/auth/me")
                        .with(authentication(authFor("admin@test.com", "ADMINISTRADOR"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rol").value("ADMINISTRADOR"));
    }

    @Test
    void GET_me_emailNoEnBD_devuelve500() throws Exception {
        // El controller lanza RuntimeException cuando el email del token no aparece en la BD
        // (caso raro pero posible: borraron el usuario después de emitir el JWT)
        when(usuarioRepository.findByEmail("fantasma@test.com")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/auth/me")
                        .with(authentication(authFor("fantasma@test.com", "CLIENTE"))))
                .andExpect(status().isInternalServerError());
    }
}