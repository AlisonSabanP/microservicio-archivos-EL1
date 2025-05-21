package com.example.demo.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.model.Lista;

@Service
public class ListaService {

    private final Lista lista = new Lista();

    public void insertar(int valor) {
        lista.insertar(valor);
    }

    public Integer eliminar(int indice) {
        return lista.eliminar(indice);
    }

    public List<Integer> getElementos() {
        return lista.getElementos();
    }

    public void reiniciar() {
        lista.Reiniciar();
    }
}