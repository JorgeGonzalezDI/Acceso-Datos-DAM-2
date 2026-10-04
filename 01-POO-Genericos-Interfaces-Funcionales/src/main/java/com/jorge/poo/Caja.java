package com.jorge.poo;

/**
 * Clase generica de ejemplo (ver apuntes, seccion 16): "T" se decide
 * cada vez que se usa la clase, no aqui dentro.
 */
public class Caja<T> {

    private T contenido;

    public void guardar(T contenido) {
        this.contenido = contenido;
    }

    public T sacar() {
        return contenido;
    }

    public boolean estaVacia() {
        return contenido == null;
    }
}
