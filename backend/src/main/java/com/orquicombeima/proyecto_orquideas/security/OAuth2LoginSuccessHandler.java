package com.orquicombeima.proyecto_orquideas.security;

import com.orquicombeima.proyecto_orquideas.model.Usuario;
import com.orquicombeima.proyecto_orquideas.model.enums.Rol;
import com.orquicombeima.proyecto_orquideas.repository.UsuarioRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

// Se ejecuta justo después de que Google confirma quién es el usuario
@Component
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;

    @Value("${admin.email}")
    private String adminEmail;

    @Value("${app.frontend-url}")
    private String frontendUrl;

    public OAuth2LoginSuccessHandler(UsuarioRepository usuarioRepository, JwtService jwtService) {
        this.usuarioRepository = usuarioRepository;
        this.jwtService = jwtService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        // Obtenemos los datos que mandó Google
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");
        String nombre = oAuth2User.getAttribute("name");

        // Buscamos si este usuario ya existe en la BD
        Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(email);

        Usuario usuario;
        if (usuarioExistente.isPresent()) {
            // Ya existe → solo actualizamos nombre por si cambiaron en Google
            usuario = usuarioExistente.get();
            usuario.setNombre(nombre);
        } else {
            // Es nuevo → lo creamos y le asignamos rol según su correo
            usuario = new Usuario();
            usuario.setEmail(email);
            usuario.setNombre(nombre);

            if (email.equals(adminEmail)) {
                usuario.setRol(Rol.ADMINISTRADOR);
            } else {
                usuario.setRol(Rol.CLIENTE);
            }
        }

        usuarioRepository.save(usuario);

        // Generamos el JWT y mandamos al frontend con el token en la URL
        String token = jwtService.generarToken(usuario);
        String redirectUrl = frontendUrl + "/auth/callback?token=" + token;

        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }
}