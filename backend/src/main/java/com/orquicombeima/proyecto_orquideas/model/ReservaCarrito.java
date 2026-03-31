package com.orquicombeima.proyecto_orquideas.model;

import com.orquicombeima.proyecto_orquideas.model.enums.EstadoReserva;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "reservas_carrito")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservaCarrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "carrito_id", nullable = false, unique = true)
    private Carrito carrito;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoReserva estado;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_expiracion")
    private LocalDateTime fechaExpiracion;
}