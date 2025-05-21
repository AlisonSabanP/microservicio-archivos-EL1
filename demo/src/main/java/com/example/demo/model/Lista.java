package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Lista{
    private List<Integer> elementos;

    public Lista() {
        this.elementos = new ArrayList<>();
    }

    public void insertar(int valor) {
        elementos.add(valor);
    }

    public Integer eliminar(int indice) {
        if (indice < 0 || indice >= elementos.size()) {
            return null;
        }
        return elementos.remove(indice);
    }

    public void Reiniciar() {
        elementos.clear();
    }
}