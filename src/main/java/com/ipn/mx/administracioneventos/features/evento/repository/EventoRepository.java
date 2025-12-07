package com.ipn.mx.administracioneventos.features.evento.repository;

import com.ipn.mx.administracioneventos.core.domain.Evento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventoRepository extends JpaRepository<Evento, Long> {
    // Crear consulta en JPQL que me devuelva un evento por nombre
}
