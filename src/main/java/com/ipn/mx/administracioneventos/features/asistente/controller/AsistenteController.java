package com.ipn.mx.administracioneventos.features.asistente.controller;

import com.ipn.mx.administracioneventos.core.domain.Asistente;
import com.ipn.mx.administracioneventos.features.asistente.service.AsistenteService;
import com.ipn.mx.administracioneventos.util.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ipn.mx.administracioneventos.util.service.EmailService;

@CrossOrigin(origins = {"*"}) /* Para recibir solicitudes CORS, es decir,
Sirve para que una aplicación web pueda hacer peticiones HTTP al backend de Spring Boot,
aunque estén en diferentes dominios o puertos.*/
@RestController
@RequestMapping("/api/v1/asistentes")

public class AsistenteController {
    @Autowired
    private AsistenteService asistenteService;
    @Autowired
    private EmailService emailService;

    // Creando endpoints
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Asistente> readAll() {
        return asistenteService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> readById(@PathVariable Long id){
        Asistente asistente = null;
        Map<String, Object> response = new HashMap<>();
        try {
            asistente = asistenteService.findById(id);
        } catch (DataAccessException ex) {
            response.put("Mensaje", "Error al hacer la consulta en la Base de datos");
            response.put("error:", ex.getMessage().concat(":").concat(ex.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (asistente == null) {
            response.put("mensaje", "El asistente con ID: ".concat(id.toString()
                    .concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Asistente>(asistente, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Asistente asistente) {


        Asistente a = null;
        Map<String, Object> response = new HashMap<>();
        try {
            a = asistenteService.save(asistente);
        } catch (DataAccessException ex) {
            response.put("mensaje", "Erro al insertar el evento en la bd");
            response.put("error:", ex.getMessage().concat(":")
                    .concat(ex.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "El asistente con ID: ".concat(a.toString()) + " se ha creado exitosamente");
        response.put("evento", a);
        String texto = "Se ha registrado este correo electronico exitosamente en nuestra base de datos ";
        String to = asistente.getEmail();
        String subject = "Registro en Administracion Eventos";

        emailService.sendEmail(to,subject,texto);

        return new ResponseEntity<Map<String,Object>>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> update(@RequestBody Asistente asistente, @PathVariable Long id) {
        Asistente a = asistenteService.findById(id);
        Asistente asistenteActualizado = null;
        Map<String, Object> response = new HashMap<>();
        if (a == null) {
            response.put("mensaje", "El asistente con ID: ".concat(id.toString())+ "no se puede editar pq no esta en la bd");
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
        }
        try {
            a.setEmail(asistente.getEmail());
            a.setNombre(asistente.getNombre());
            a.setPaterno(asistente.getPaterno());
            a.setMaterno(asistente.getMaterno());
            a.setIdEvento(asistente.getIdEvento());

            asistenteActualizado = asistenteService.save(a);
        } catch (DataAccessException ex) {
            response.put("mensaje", "Erro al insertar el asistente en la bd");
            response.put("error:", ex.getMessage().concat(":")
                    .concat(ex.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "El asistente con ID: ".concat(a.toString()) + " se ha creado exitosamente");
        response.put("evento", a);
        return new ResponseEntity<Map<String,Object>>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    // @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            if (asistenteService.findById(id) == null) {
                response.put("Error", "El asistente con ID: ".concat(id.toString()) + "no se encontró en base de datos");
            }
            response.put("Mensaje", asistenteService.findById(id) + "Encontrado");
            asistenteService.delete(id);
        } catch (DataAccessException e) {
            response.put("Mensaje", "Error al eliminar el asistente en la bd");
            response.put("error:", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("Mensaje:", "Se elimino el asistente con ID: ".concat(id.toString()));
        return new ResponseEntity<Map<String,Object>>(response, HttpStatus.CREATED);
    }
}
