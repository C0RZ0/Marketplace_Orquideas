package com.orquicombeima.proyecto_orquideas.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.orquicombeima.proyecto_orquideas.dto.AgregarItemRequestDTO;
import com.orquicombeima.proyecto_orquideas.dto.CarritoDTO;
import com.orquicombeima.proyecto_orquideas.dto.ItemCarritoDTO;
import com.orquicombeima.proyecto_orquideas.service.CarritoService;
import com.orquicombeima.proyecto_orquideas.shared.config.GlobalExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.method.annotation.AuthenticationPrincipalArgumentResolver;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// Usamos standaloneSetup como en AuthControllerTest porque CarritoController usa
// @AuthenticationPrincipal String email. Con .principal(...) y el AuthenticationPrincipalArgumentResolver
// el resolver desempaqueta el principal del token y lo inyecta como String.
@ExtendWith(MockitoExtension.class)
class CarritoControllerTest {

    @Mock private CarritoService service;

    @InjectMocks private CarritoController controller;

    private MockMvc mockMvc;
    private final ObjectMapper mapper = new ObjectMapper();
    private CarritoDTO carritoDTO;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setCustomArgumentResolvers(new AuthenticationPrincipalArgumentResolver())
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();

        ItemCarritoDTO item = ItemCarritoDTO.builder()
                .id(50L)
                .idProducto(10L)
                .nombreProducto("Cattleya")
                .precioUnitario(50000.0)
                .cantidad(2)
                .subtotal(100000.0)
                .imagenUrl("https://cdn/cattleya.jpg")
                .build();

        carritoDTO = CarritoDTO.builder()
                .id(100L)
                .idUsuario(1L)
                .items(List.of(item))
                .total(100000.0)
                .build();
    }

    // El principal que inyectamos: el AuthenticationPrincipalArgumentResolver toma este
    // UsernamePasswordAuthenticationToken, saca su principal (el String) y lo pasa al controller
    private UsernamePasswordAuthenticationToken principalDe(String email) {
        return new UsernamePasswordAuthenticationToken(
                email, null, List.of(new SimpleGrantedAuthority("ROLE_CLIENTE")));
    }

    @Test
    void GET_carrito_devuelve200ConCarrito() throws Exception {
        when(service.obtenerCarrito("rosa@test.com")).thenReturn(carritoDTO);

        mockMvc.perform(get("/api/carrito").principal(principalDe("rosa@test.com")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(100))
                .andExpect(jsonPath("$.total").value(100000.0))
                .andExpect(jsonPath("$.items[0].nombreProducto").value("Cattleya"));
    }

    @Test
    void POST_agregar_devuelve200ConCarritoActualizado() throws Exception {
        AgregarItemRequestDTO request = new AgregarItemRequestDTO(10L, 2);
        when(service.agregarItem(any(AgregarItemRequestDTO.class), eq("rosa@test.com"))).thenReturn(carritoDTO);

        mockMvc.perform(post("/api/carrito/agregar")
                        .principal(principalDe("rosa@test.com"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items[0].cantidad").value(2));
    }

    @Test
    void PUT_actualizarCantidad_devuelve200() throws Exception {
        when(service.actualizarCantidad(eq(50L), eq(5), eq("rosa@test.com"))).thenReturn(carritoDTO);

        mockMvc.perform(put("/api/carrito/50/cantidad")
                        .principal(principalDe("rosa@test.com"))
                        .param("cantidad", "5"))
                .andExpect(status().isOk());

        verify(service).actualizarCantidad(50L, 5, "rosa@test.com");
    }

    @Test
    void DELETE_eliminarItem_devuelve200() throws Exception {
        when(service.eliminarItem(eq(50L), eq("rosa@test.com"))).thenReturn(carritoDTO);

        mockMvc.perform(delete("/api/carrito/50").principal(principalDe("rosa@test.com")))
                .andExpect(status().isOk());

        verify(service).eliminarItem(50L, "rosa@test.com");
    }

    @Test
    void DELETE_vaciar_devuelve204() throws Exception {
        mockMvc.perform(delete("/api/carrito/vaciar").principal(principalDe("rosa@test.com")))
                .andExpect(status().isNoContent());

        verify(service).vaciarCarrito("rosa@test.com");
    }
}