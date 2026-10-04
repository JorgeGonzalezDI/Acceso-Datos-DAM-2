package com.jorge.poo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Usa las 4 interfaces funcionales principales de los apuntes (seccion 13.1):
 * Predicate, Consumer y Supplier (Function se usa mucho en Streams, seccion 18).
 */
public class Biblioteca {

    private final List<Libro> libros = new ArrayList<>();

    public void anadir(Libro libro) {
        libros.add(libro);
    }

    public List<Libro> buscar(Predicate<Libro> criterio) {
        List<Libro> resultado = new ArrayList<>();
        for (Libro libro : libros) {
            if (criterio.test(libro)) {
                resultado.add(libro);
            }
        }
        return resultado;
    }

    public void paraCadaLibro(Consumer<Libro> accion) {
        for (Libro libro : libros) {
            accion.accept(libro);
        }
    }

    public Libro obtenerOCrear(Supplier<Libro> siNoHayNinguno) {
        return libros.isEmpty() ? siNoHayNinguno.get() : libros.get(0);
    }

    public int total() {
        return libros.size();
    }
}
