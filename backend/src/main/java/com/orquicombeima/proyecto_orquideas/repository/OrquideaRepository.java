package com.orquicombeima.proyecto_orquideas.repository;

import com.orquicombeima.proyecto_orquideas.model.Orquidea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrquideaRepository extends JpaRepository<Orquidea, Long> {
}