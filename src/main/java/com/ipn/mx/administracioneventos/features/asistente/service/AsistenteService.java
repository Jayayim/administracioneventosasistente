package com.ipn.mx.administracioneventos.features.asistente.service;

import com.ipn.mx.administracioneventos.core.domain.Asistente;

import java.io.ByteArrayInputStream;
import java.util.List;

public interface AsistenteService {
    public List<Asistente> findAll();
    public Asistente findById(Long id);
    public Asistente save(Asistente asistente); // Para crear o actualizar
    public void delete(Long id);

    public ByteArrayInputStream reportePDF(List<Asistente> listaEventos);
}
