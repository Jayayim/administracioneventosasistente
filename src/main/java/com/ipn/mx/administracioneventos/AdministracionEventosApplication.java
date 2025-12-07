package com.ipn.mx.administracioneventos;

import com.ipn.mx.administracioneventos.util.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AdministracionEventosApplication implements CommandLineRunner {
    @Autowired
    private EmailService emailService;

    @Override
    public void run(String... args) throws Exception {
        /*String texto = "Texto de prueba para enviar un correo electronico desde Springboot";
        String to = "ale120201@gmail.com";
        String subject = "Correo de prueba desde springboot";

        emailService.sendEmail(to,subject,texto);*/
    }

    public static void main(String[] args) {
        SpringApplication.run(AdministracionEventosApplication.class, args);
    }

}
