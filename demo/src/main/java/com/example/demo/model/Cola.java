
package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Cola{
    private List<Integer> elementos;

    public Cola() {
        this.elementos = new ArrayList<>();
    }

    public void insertar(int valor) {
        elementos.add(valor);
    }

    public Integer eliminar() {
        if (elementos.isEmpty()) {
            return null;
        }
        return elementos.remove(0);
    }

    public void reiniciar() {
        elementos.clear();
    }
}