package com.orquicombeima.proyecto_orquideas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatbotRequestDTO {

    private String mensaje;
    private List<MensajeHistorialDTO> historial;
}
