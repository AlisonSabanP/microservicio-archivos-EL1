package com.example.demo.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.Operacion;
import com.example.demo.services.ArchivoService;
import com.example.demo.util.ErrorResponse;

@RestController
@RequestMapping("/api/archivos")
public class ArchivoController {

    @Autowired
    private ArchivoService archivoService;

    @PostMapping("/cargar")
    public ResponseEntity<?> cargarArchivo(@RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body(new ErrorResponse("El archivo está vacío."));
            }

            if (!file.getOriginalFilename().endsWith(".csv")) {
                return ResponseEntity.badRequest().body(new ErrorResponse("Formato de archivo no válido. Solo se permiten archivos .csv"));
            }

            String nombreArchivo = archivoService.guardarArchivo(file);
            ArchivoCargadoDTO dto = new ArchivoCargadoDTO();
            dto.setNombre(nombreArchivo);
            dto.setMensaje("Archivo cargado exitosamente");

            return ResponseEntity.ok(dto);

        } catch (IOException e) {
            return ResponseEntity.status(500).body(new ErrorResponse("Error al guardar el archivo: " + e.getMessage()));
        }
    }

    @PostMapping("/procesar/{nombre}")
    public ResponseEntity<?> procesar(@PathVariable String nombre) {
        try {
            List<Operacion> ops = archivoService.procesarArchivo(nombre);
            return ResponseEntity.ok(ops);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(new ErrorResponse("Error al leer el archivo: " + e.getMessage()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }
}
