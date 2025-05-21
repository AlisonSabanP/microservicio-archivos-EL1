package com.example.demo.services;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.component.GraphvizGenerator;
import com.example.demo.model.Operacion;

@Service
public class OperacionService {

    @Autowired private PilaService pilaService;
    @Autowired private ColaService colaService;
    @Autowired private ListaService listaService;
    @Autowired private ArregloService arregloService;
    @Autowired private GraphvizGenerator graphvizGenerator;

    private Map<String, Map<String, List<Operacion>>> operacionesPorArchivo = new HashMap<>();

    public void procesarOperaciones(String nombre, List<Operacion> operaciones, Path dirImagenes) throws IOException {
        pilaService.reiniciar();
        colaService.reiniciar();
        listaService.reiniciar();
        arregloService.reiniciar();

        Map<String, List<Operacion>> porEstructura = new HashMap<>();

        for (Operacion op : operaciones) {
            String estructura = op.getEstructura();
            porEstructura.computeIfAbsent(estructura, k -> new ArrayList<>()).add(op);

            String ruta = dirImagenes.resolve(estructura).resolve(op.hashCode() + ".png").toString();

            switch (estructura.toLowerCase()) {
                case "pila": ejecutarPila(op, ruta); break;
                case "cola": ejecutarCola(op, ruta); break;
                case "lista": ejecutarLista(op, ruta); break;
                case "arreglo": ejecutarArreglo(op, ruta); break;
            }

            op.setRutaImagen(ruta);
        }

        operacionesPorArchivo.put(nombre, porEstructura);
    }

    private void ejecutarPila(Operacion op, String ruta) throws IOException {
        if ("insertar".equals(op.getTipoOperacion()) && op.getValor() != null) {
            pilaService.insertar(op.getValor());
        } else if ("eliminar".equals(op.getTipoOperacion())) {
            pilaService.eliminar();
        }
        graphvizGenerator.generarImagenPila(pilaService.getElementos(), ruta);
    }

    private void ejecutarCola(Operacion op, String ruta) throws IOException {
        if ("insertar".equals(op.getTipoOperacion()) && op.getValor() != null) {
            colaService.insertar(op.getValor());
        } else if ("eliminar".equals(op.getTipoOperacion())) {
            colaService.eliminar();
        }
        graphvizGenerator.generarImagenCola(colaService.getElementos(), ruta);
    }

    private void ejecutarLista(Operacion op, String ruta) throws IOException {
        if ("insertar".equals(op.getTipoOperacion()) && op.getValor() != null) {
            listaService.insertar(op.getValor());
        } else if ("eliminar".equals(op.getTipoOperacion())) {
            listaService.eliminar(listaService.getElementos().size() - 1);
        }
        graphvizGenerator.generarImagenLista(listaService.getElementos(), ruta);
    }

    private void ejecutarArreglo(Operacion op, String ruta) throws IOException {
        if ("insertar".equals(op.getTipoOperacion()) && op.getValor() != null) {
            arregloService.insertar(op.getValor());
        } else if ("eliminar".equals(op.getTipoOperacion())) {
            arregloService.eliminar(arregloService.getTamaño() - 1);
        }
        graphvizGenerator.generarImagenArreglo(arregloService.getElementos(), ruta);
    }

    public List<Operacion> getOperacionesPorEstructura(String nombre, String estructura) {
        return operacionesPorArchivo.getOrDefault(nombre, Collections.emptyMap()).getOrDefault(estructura, Collections.emptyList());
    }
}