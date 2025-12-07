package com.ipn.mx.administracioneventos.features.asistente.service.impl;

import com.ipn.mx.administracioneventos.core.domain.Asistente;
import com.ipn.mx.administracioneventos.features.asistente.repository.AsistenteRepository;
import com.ipn.mx.administracioneventos.features.asistente.service.AsistenteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
@Service
@Transactional
public class AsistenteServiceImpl implements AsistenteService {
    @Autowired
    private AsistenteRepository asistenteRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Asistente> findAll() {
        return asistenteRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Asistente findById(Long id) {
        return asistenteRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = false)
    public Asistente save(Asistente asistente) {
        return asistenteRepository.save(asistente);
    }

    @Override
    public void delete(Long id) {
        asistenteRepository.deleteById(id);
    }

    @Override

    public ByteArrayInputStream reportePDF(List<Asistente> listaAsistente) {
        Document documento = new Document();
        ByteArrayOutputStream salida = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(documento, salida);
            documento.open();

            // Título
            Font tituloFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, BaseColor.LIGHT_GRAY);
            Paragraph parrafo = new Paragraph("Lista de asistentes", tituloFont);
            parrafo.setAlignment(Element.ALIGN_CENTER);
            documento.add(parrafo);
            documento.add(Chunk.NEWLINE);

            // Tabla
            Font textFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, BaseColor.BLACK);
            PdfPTable table = new PdfPTable(7);
            table.setWidthPercentage(100);

            Stream.of("idAsistente", "email", "Fecha registro",
                            "A. Paterno", "A. Materno", "Nombre", "idEvento")
                    .forEach(headertitle -> {
                        PdfPCell encabezadoTabla = new PdfPCell();
                        Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9, BaseColor.RED);

                        encabezadoTabla.setBackgroundColor(BaseColor.LIGHT_GRAY);
                        encabezadoTabla.setHorizontalAlignment(Element.ALIGN_CENTER);
                        encabezadoTabla.setVerticalAlignment(Element.ALIGN_CENTER);
                        encabezadoTabla.setBorder(2);
                        encabezadoTabla.setPhrase(new Phrase(headertitle, headFont));
                        table.addCell(encabezadoTabla);
                    });

            for (Asistente asistente : listaAsistente) {
                PdfPCell celdaIdAsistente =
                        new PdfPCell(new Phrase(String.valueOf(asistente.getIdAsistente()), textFont));
                celdaIdAsistente.setPadding(1);
                celdaIdAsistente.setVerticalAlignment(Element.ALIGN_MIDDLE);
                celdaIdAsistente.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(celdaIdAsistente);

                PdfPCell celdaEmail =
                        new PdfPCell(new Phrase(asistente.getEmail(), textFont));
                celdaEmail.setPadding(1);
                celdaEmail.setVerticalAlignment(Element.ALIGN_MIDDLE);
                celdaEmail.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(celdaEmail);

                PdfPCell celdaFechaRegistro =
                        new PdfPCell(new Phrase(String.valueOf(asistente.getFechaRegistro()), textFont));
                celdaFechaRegistro.setPadding(1);
                celdaFechaRegistro.setVerticalAlignment(Element.ALIGN_MIDDLE);
                celdaFechaRegistro.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(celdaFechaRegistro);

                PdfPCell celdaPaterno =
                        new PdfPCell(new Phrase(asistente.getPaterno(), textFont));
                celdaPaterno.setPadding(1);
                celdaPaterno.setVerticalAlignment(Element.ALIGN_MIDDLE);
                celdaPaterno.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(celdaPaterno);

                PdfPCell celdaMaterno =
                        new PdfPCell(new Phrase(asistente.getMaterno(), textFont));
                celdaMaterno.setPadding(1);
                celdaMaterno.setVerticalAlignment(Element.ALIGN_MIDDLE);
                celdaMaterno.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(celdaMaterno);

                PdfPCell celdaNombre =
                        new PdfPCell(new Phrase(asistente.getNombre(), textFont));
                celdaNombre.setPadding(1);
                celdaNombre.setVerticalAlignment(Element.ALIGN_MIDDLE);
                celdaNombre.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(celdaNombre);

                PdfPCell celdaIdEvento =
                        new PdfPCell(new Phrase(String.valueOf(asistente.getIdEvento()), textFont));
                celdaIdEvento.setPadding(1);
                celdaIdEvento.setVerticalAlignment(Element.ALIGN_MIDDLE);
                celdaIdEvento.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(celdaIdEvento);
            }

            documento.add(table);
            documento.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return new ByteArrayInputStream(salida.toByteArray());
    }

}
