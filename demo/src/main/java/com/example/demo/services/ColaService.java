package com.example.demo.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.model.Cola;

@Service
public class ColaService {

    private final Cola cola = new Cola();

    public void insertar(int valor) {
        cola.insertar(valor);
    }

    public Integer eliminar() {
        return cola.eliminar();
    }

    public List<Integer> getElementos() {
        return cola.getElementos();
    }

    public void reiniciar() {
        cola.reiniciar();
    }
}