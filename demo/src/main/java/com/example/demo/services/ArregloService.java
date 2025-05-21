package com.example.demo.services;

import com.example.demo.model.Arreglo;
import org.springframework.stereotype.Service;


@Service
public class ArregloService {

    private final Arreglo arreglo = new Arreglo(5); 

    public void insertar(int valor) {
        arreglo.insertar(valor);
    }

    public Integer eliminar(int indice) {
        return arreglo.eliminar(indice);
    }

    public Integer[] getElementos() {
        return arreglo.getElementos();
    }

    public int getTamaño() {
        return arreglo.getTamanio();
    }

    public void reiniciar() {
        this.arreglo.reiniciar();
    }
}