package com.ipn.mx.administracioneventos.features.evento.controller;

import com.ipn.mx.administracioneventos.core.domain.Evento;
import com.ipn.mx.administracioneventos.features.evento.service.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"*"}) /* Para recibir solicitudes CORS, es decir,
Sirve para que una aplicación web pueda hacer peticiones HTTP al backend de Spring Boot,
aunque estén en diferentes dominios o puertos.*/

@RestController
@RequestMapping("/api/v1/eventos")
public class EventoController {
    @Autowired
    private EventoService service;

    // Creando controladores
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Evento> readAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    // @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> readById(@PathVariable Long id) {
        Evento evento = null;
        Map<String, Object> response = new HashMap<>();
        try {
            evento = service.findById(id);
        } catch (DataAccessException e) {
            response.put("Mensaje", "Error al hacer la consulta en la Base de datos");
            response.put("error:", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (evento == null){
        response.put("mensaje", "El evento con ID: ".concat(id.toString()
                .concat(" no existe en la base de datos")));
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Evento>(evento, HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> create(@RequestBody Evento evento) {
        Evento e = null;
        Map<String, Object> response = new HashMap<>();
        try {
            e = service.save(evento);
        } catch (DataAccessException ex) {
            response.put("mensaje", "Erro al insertar el evento en la bd");
            response.put("error:", ex.getMessage().concat(":")
                    .concat(ex.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "El evento con ID: ".concat(e.toString()) + " se ha creado exitosamente");
        response.put("evento", e);
        return new ResponseEntity<Map<String,Object>>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> update(@RequestBody Evento evento, @PathVariable Long id) {
        Evento e = service.findById(id);
        Evento eventoActualizado = null;
        Map<String, Object> response = new HashMap<>();
        if (e == null) {
            response.put("mensaje", "El evento con ID: ".concat(id.toString())+ "no se puede editar pq no esta en la bd");
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
        }
        try {
            e.setNombreEvento(evento.getNombreEvento());
            e.setDescripcionEvento(evento.getDescripcionEvento());
            e.setFechaInicio(evento.getFechaInicio());
            e.setFechaFin(evento.getFechaFin());

            eventoActualizado = service.save(e);
        } catch (DataAccessException ex) {
            response.put("mensaje", "Erro al insertar el evento en la bd");
            response.put("error:", ex.getMessage().concat(":")
                    .concat(ex.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "El evento con ID: ".concat(e.toString()) + " se ha creado exitosamente");
        response.put("evento", e);
        return new ResponseEntity<Map<String,Object>>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    // @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            if (service.findById(id) == null) {
                response.put("Error", "El evento con ID: ".concat(id.toString()) + "no se encontró en base de datos");
            }
            response.put("Mensaje", service.findById(id) + "Encontrado");
            service.delete(id);
        } catch (DataAccessException e) {
            response.put("Mensaje", "Error al eliminar el evento en la bd");
            response.put("error:", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("Mensaje:", "Se elimino el evento con ID: ".concat(id.toString()));
        return new ResponseEntity<Map<String,Object>>(response, HttpStatus.CREATED);
    }

}
