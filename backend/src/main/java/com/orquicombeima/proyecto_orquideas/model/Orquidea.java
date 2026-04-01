package com.orquicombeima.proyecto_orquideas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Orquidea hereda todos los campos de Producto (id, nombre, precio, stock, imageUrl, activo) y agrega sus propios campos específicos
// En la BD existirá una tabla "orquideas" con solo sus campos propios, conectada a "productos" por el mismo id
@Entity
@Table(name = "orquideas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@PrimaryKeyJoinColumn(name = "id")          // le dice a JPA que el id de orquideas es FK hacia productos

public class Orquidea extends Producto {

    @Column(nullable = false)
    private String variedad;

    @Column(name = "color_flor")
    private String colorFlor;

    @Column(nullable = false)
    private String tamanio;

    @Column(name = "nivel_cuidado")
    private String nivelCuidado;

    @Column(name = "tiempo_floracion")
    private String tiempoFloracion;
}