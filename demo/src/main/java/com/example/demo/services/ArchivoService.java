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
    // Validación adicional por seguridad
    String originalFilename = file.getOriginalFilename();
    if (originalFilename == null || !originalFilename.endsWith(".csv")) {
        throw new IOException("Extensión de archivo no válida.");
    }

    String nombre = UUID.randomUUID().toString() + ".csv";
    Path destino = rutaArchivos.resolve(nombre);
    Files.copy(file.getInputStream(), destino);
    return nombre;
}

public List<Operacion> procesarArchivo(String nombreArchivo) throws IOException {
    Path path = rutaArchivos.resolve(nombreArchivo);

    if (!Files.exists(path)) {
        throw new IllegalArgumentException("El archivo no existe: " + nombreArchivo);
    }

    List<Operacion> operaciones = leerCSV(nombreArchivo);

    if (operaciones.isEmpty()) {
        throw new IllegalArgumentException("El archivo está vacío o no tiene registros válidos.");
    }

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

        boolean headerValidated = false;
        for (CSVRecord record : parser) {
            if (!headerValidated) {
                // Verificar encabezados
                if (!record.isConsistent()
                    || !record.isMapped("estructura")
                    || !record.isMapped("operacion")) {
                    throw new IllegalArgumentException("Formato del CSV no válido. Faltan columnas obligatorias.");
                }
                headerValidated = true;
            }

            Operacion op = new Operacion();
            op.setEstructura(record.get("estructura"));
            op.setTipoOperacion(record.get("operacion"));

            if (record.isSet("valor") && !record.get("valor").isEmpty()) {
                try {
                    op.setValor(Integer.parseInt(record.get("valor")));
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Campo 'valor' contiene un valor no numérico.");
                }
            }

            ops.add(op);
        }
    }

    return ops;
}
}