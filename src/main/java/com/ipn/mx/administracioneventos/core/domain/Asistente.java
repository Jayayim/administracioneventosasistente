package com.ipn.mx.administracioneventos.core.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Asistente", schema = "public")
@ToString(exclude = "idEvento")
public class Asistente implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAsistente;

    @NotBlank(message = "No puede estar en blanco")
    @Size(min = 2, max = 50, message = "El nombre debe tener 2-50 caracteres")
    @Column(name = "nombre", length = 50, nullable = false)
    private String nombre;

    @Size(min = 2, max = 50, message = "El paterno debe tener 2-50 caracteres")
    @Column(name = "paterno", length = 50, nullable = false)
    private String paterno;

    @Size(min = 2, max = 50, message = "El materno debe tener 2-50 caracteres")
    @Column(name = "materno", length = 50, nullable = false)
    private String materno;

    @Email(message = "Escribe un correo electronico válido") // Podemos poner una expresion regular para correos más estrictos
    @Column(name = "email", length = 100, nullable = false)
    private String email;

    @Temporal(TemporalType.DATE)
    @Column(name = "fechaRegistro", nullable = false)
    private Date fechaRegistro;


    @ManyToOne
    @JoinColumn(name = "idEvento", nullable = false)
    @JsonBackReference
    private Evento idEvento;

    // Hacer que cuando un asistente se registre, nos envíe un mail

}
