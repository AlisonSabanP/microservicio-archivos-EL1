package com.example.demo.model;

public class Arreglo{
    private Integer[] elementos;
    private int tamanio;
    private int capacidad;

    public Arreglo(int capacidadInicial) {
        this.capacidad = capacidadInicial;
        this.elementos = new Integer[capacidad];
        this.tamanio = 0;
    }

    public void insertar(int valor) {
        if (tamanio == capacidad) {
            ampliarCapacidad();
        }
        elementos[tamanio++] = valor;
    }

    public Integer eliminar(int indice) {
        if (indice < 0 || indice >= tamanio) {
            return null;
        }

        Integer valorEliminado = elementos[indice];

        for (int i = indice; i < tamanio - 1; i++) {
            elementos[i] = elementos[i + 1];
        }

        elementos[tamanio - 1] = null;
        tamanio--;

        return valorEliminado;
    }

    private void ampliarCapacidad() {
        Integer[] nuevoArreglo = new Integer[capacidad * 2];
        System.arraycopy(elementos, 0, nuevoArreglo, 0, capacidad);
        elementos = nuevoArreglo;
        capacidad *= 2;
    }

    public Integer[] getElementos() {
        Integer[] copia = new Integer[tamanio];
        System.arraycopy(elementos, 0, copia, 0, tamanio);
        return copia;
    }

    public void reiniciar() {
        this.tamanio = 0;
        this.elementos = new Integer[capacidad];
    }

    public int getTamanio() {
        return tamanio;
    }
}