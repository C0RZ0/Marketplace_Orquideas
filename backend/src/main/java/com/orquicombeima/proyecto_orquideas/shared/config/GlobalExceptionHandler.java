package com.orquicombeima.proyecto_orquideas.shared.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

// Esta clase atrapa los errores que ocurren en cualquier controlador de la app
// En lugar de mostrar un error técnico feo, devuelve un JSON limpio con el mensaje del error
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // Atrapa errores 404: cuando no se encuentra un recurso (orquídea, maceta, etc.)
    // Devuelve un mensaje genérico al cliente para no exponer detalles internos
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleNotFound(RuntimeException ex) {
        log.warn("Recurso no encontrado: {}", ex.getMessage());
        Map<String, String> error = new HashMap<>();
        error.put("error", "El recurso solicitado no fue encontrado");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    // Atrapa errores 400: cuando el id que llega en la URL no es un número válido
    // Por ejemplo: /api/orquideas/abc en vez de /api/orquideas/1
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, String>> handleBadRequest(MethodArgumentTypeMismatchException ex) {
        log.warn("Parámetro inválido: {} = {}", ex.getName(), ex.getValue());
        Map<String, String> error = new HashMap<>();
        error.put("error", "El parámetro '" + ex.getName() + "' tiene un valor inválido");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    // Atrapa errores 500: cualquier error inesperado que no fue manejado antes
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneral(Exception ex) {
        log.error("Error interno del servidor", ex);
        Map<String, String> error = new HashMap<>();
        error.put("error", "Ocurrió un error interno en el servidor");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}