package com.example.demo.services;

import com.example.demo.model.Pila;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PilaService {
    private final Pila pila = new Pila();

    public void insertar(int valor) {
        pila.insertar(valor);
    }

    public Integer eliminar() {
        return pila.eliminar();
    }

    public List<Integer> getElementos() {
        return pila.getElementos();
    }

    public void reiniciar() {
        pila.reiniciar();
    }
}