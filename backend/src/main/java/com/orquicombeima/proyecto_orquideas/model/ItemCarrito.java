package com.orquicombeima.proyecto_orquideas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Esta clase representa cada producto dentro de un carrito
@Entity
@Table(name = "items_carrito")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class ItemCarrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // A qué carrito pertenece este item
    @ManyToOne
    @JoinColumn(name = "carrito_id", nullable = false)
    private Carrito carrito;

    @NotNull
    @Column(name = "producto_id", nullable = false)
    private Long productoId;

    @Min(1)                         // No tiene sentido agregar 0 productos
    @Column(nullable = false)
    private Integer cantidad;
}