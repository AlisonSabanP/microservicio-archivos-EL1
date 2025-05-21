package com.example.demo.component;

import guru.nidi.graphviz.attribute.Color;
import guru.nidi.graphviz.attribute.Shape;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.Graph;
import guru.nidi.graphviz.model.Node;
import org.springframework.stereotype.Component;
import java.io.File;
import java.io.IOException;
import java.util.List;
import static guru.nidi.graphviz.model.Factory.*;

@Component
public class GraphvizGenerator {

    public void generarImagenPila(List<Integer> elementos, String rutaDestino) throws IOException {
        Graph g = graph("pila").directed();
        Node nodoAnterior = null;

        for (int i = 0; i < elementos.size(); i++) {
            Node nodo = node(String.valueOf(elementos.get(i)));
            if (nodoAnterior == null) {
                g = g.with(nodo);
            } else {
                g = g.with(nodo.link(to(nodoAnterior)));
            }
            nodoAnterior = nodo;
        }

        Graphviz.fromGraph(g)
                .width(300)
                .render(Format.PNG)
                .toFile(new File(rutaDestino));
    }

    public void generarImagenCola(List<Integer> elementos, String rutaDestino) throws IOException {
        Graph g = graph("cola").directed();
        Node nodoAnterior = null;
        Node primerNodo = null;

        for (int i = 0; i < elementos.size(); i++) {
            Node nodo = node(String.valueOf(elementos.get(i)));
            if (nodoAnterior == null) {
                primerNodo = nodo;
                g = g.with(nodo);
            } else {
                g = g.with(nodoAnterior.link(to(nodo)));
            }
            nodoAnterior = nodo;
        }

        if (primerNodo != null) {
            Node frenteLabel = node("FRENTE").with(Color.RED, Shape.ELLIPSE);
            g = g.with(frenteLabel.link(to(primerNodo)));
        }
        if (nodoAnterior != null && nodoAnterior != primerNodo) {
            Node finalLabel = node("FINAL").with(Color.BLUE, Shape.ELLIPSE);
            g = g.with(finalLabel.link(to(nodoAnterior)));
        }

        Graphviz.fromGraph(g)
                .width(500)
                .render(Format.PNG)
                .toFile(new File(rutaDestino));
    }

    public void generarImagenLista(List<Integer> elementos, String rutaDestino) throws IOException {
        Graph g = graph("lista").directed();
        Node nodoAnterior = null;

        for (int i = 0; i < elementos.size(); i++) {
            Node nodo = node(String.valueOf(elementos.get(i))).with("tooltip", "Índice: " + i);
            if (nodoAnterior == null) {
                g = g.with(nodo);
            } else {
                g = g.with(nodoAnterior.link(to(nodo)));
            }
            nodoAnterior = nodo;
        }

        Graphviz.fromGraph(g)
                .width(800)
                .render(Format.PNG)
                .toFile(new File(rutaDestino));
    }

    public void generarImagenArreglo(Integer[] elementos, String rutaDestino) throws IOException {
        Graph g = graph("arreglo").directed();

        for (int i = 0; i < elementos.length; i++) {
            String valor = elementos[i] != null ? elementos[i].toString() : "null";
            Node nodo = node("índice " + i + ": " + valor);
            g = g.with(nodo);

            if (i > 0) {
                Node nodoAnterior = node("índice " + (i-1) + ": " + 
                    (elementos[i-1] != null ? elementos[i-1].toString() : "null"));
                g = g.with(nodoAnterior.link(to(nodo)));
            }
        }

        Graphviz.fromGraph(g)
                .width(800)
                .render(Format.PNG)
                .toFile(new File(rutaDestino));
    }
}