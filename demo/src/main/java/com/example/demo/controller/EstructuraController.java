package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Operacion;
import com.example.demo.services.OperacionService;

@RestController
@RequestMapping("/api/operaciones")
public class EstructuraController {

    @Autowired private OperacionService operacionService;

    @GetMapping("/{nombre}/{estructura}")
    public ResponseEntity<List<Operacion>> getOperaciones(
            @PathVariable String nombre,
            @PathVariable String estructura) {
        List<Operacion> ops = operacionService.getOperacionesPorEstructura(nombre, estructura);
        return ops.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(ops);
    }
}