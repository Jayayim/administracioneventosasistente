package com.ipn.mx.administracioneventos.util.service.impl;

import com.ipn.mx.administracioneventos.util.service.EmailService;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.core.io.Resource;
import org.springframework.validation.annotation.Validated;

import java.io.File;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired(required = false)
    private JavaMailSender mailSender;

    @Value("classpath:static/diccionariodatos26_1.pdf") //Carpeta resourfes->static
    Resource resourceFile;
    @Override
    public void sendEmail(String to, String subject, String text) {
        if (mailSender == null) {
            System.out.println("mailSender no configurado: se omite el envío de correo.");
            return;
        }
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper;
        try{
            helper = new MimeMessageHelper(message, true, "UTF-8"); // Multipart para agregar archivos
            helper.setFrom(new InternetAddress("noreply@gmail.com"
                                            , "Administracion Eventos"));
            helper.addAttachment("Archivo", new File(resourceFile.getFile().toURI())); //Agregar un archivo adjunto llamado archivo
            helper.setSubject(subject);
            helper.setText(text);
            helper.setTo(to);
            mailSender.send(message);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
