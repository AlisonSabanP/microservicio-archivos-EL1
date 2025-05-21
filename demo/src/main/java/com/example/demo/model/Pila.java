package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;


@Data
public class Pila{
        private List<Integer> elementos;

    public Pila() {
        this.elementos = new ArrayList<>();
    }

    public void insertar(int valor) {
        elementos.add(valor);
    }

    public Integer eliminar() {
        if (elementos.isEmpty()) {
            return null;
        }
        return elementos.remove(elementos.size() - 1);
    }


    public void reiniciar() {
        elementos.clear();
    }
}


