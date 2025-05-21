package com.example.demo.services;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.Operacion;

@Service
public class ArchivoService {

    @Autowired private OperacionService operacionService;

    private final Path rutaArchivos = Paths.get("./archivos");
    private final Path rutaImagenes = Paths.get("./imagenes");

    public ArchivoService() throws IOException {
        Files.createDirectories(rutaArchivos);
        Files.createDirectories(rutaImagenes);
    }

    public String guardarArchivo(MultipartFile file) throws IOException {
        String nombre = UUID.randomUUID().toString() + ".csv";
        Path destino = rutaArchivos.resolve(nombre);
        Files.copy(file.getInputStream(), destino);
        return nombre;
    }

    public List<Operacion> procesarArchivo(String nombreArchivo) throws IOException {
        List<Operacion> operaciones = leerCSV(nombreArchivo);

        Path dirImagenes = rutaImagenes.resolve(nombreArchivo);
        Files.createDirectories(dirImagenes);

        operacionService.procesarOperaciones(nombreArchivo, operaciones, dirImagenes);
        return operaciones;
    }

    public List<Operacion> leerCSV(String nombreArchivo) throws IOException {
        Path path = rutaArchivos.resolve(nombreArchivo);
        List<Operacion> ops = new ArrayList<>();

        try (Reader reader = Files.newBufferedReader(path)) {
            CSVParser parser = new CSVParser(reader,
                CSVFormat.DEFAULT.builder()
                    .setHeader("estructura", "operacion", "valor")
                    .setSkipHeaderRecord(true)
                    .build());

            for (CSVRecord record : parser) {
                Operacion op = new Operacion();
                op.setEstructura(record.get("estructura"));
                op.setTipoOperacion(record.get("operacion"));

                if (record.isSet("valor") && !record.get("valor").isEmpty()) {
                    op.setValor(Integer.parseInt(record.get("valor")));
                }

                ops.add(op);
            }
        }

        return ops;
    }
}