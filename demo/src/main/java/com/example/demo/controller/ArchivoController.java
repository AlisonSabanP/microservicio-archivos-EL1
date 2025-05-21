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
import com.example.demo.util.SuccessResponse;

@RestController
@RequestMapping("/api/archivos")
public class ArchivoController {

    @Autowired
    private ArchivoService archivoService;

    @PostMapping("/cargar")
    public ResponseEntity<?> cargar(@RequestParam("file") MultipartFile file) {
        try {
            String nombre = archivoService.guardarArchivo(file);
            return ResponseEntity.ok(new SuccessResponse("Archivo cargado exitosamente", nombre));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage(), "Verifica el archivo"));
        }
    }

    @PostMapping("/procesar/{nombre}")
    public ResponseEntity<List<Operacion>> procesar(@PathVariable String nombre) throws IOException {
        return ResponseEntity.ok(archivoService.procesarArchivo(nombre));
    }
}
