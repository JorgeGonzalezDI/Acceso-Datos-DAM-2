package com.jorge.colecciones;

/**
 * Record (ver apuntes, seccion 15) con un constructor compacto que
 * valida la nota antes de crear el objeto.
 */
public record Alumno(String nombre, double nota) {

    public Alumno {
        if (nota < 0 || nota > 10) {
            throw new IllegalArgumentException("La nota debe estar entre 0 y 10, y llego: " + nota);
        }
    }

    public boolean aprobado() {
        return nota >= 5;
    }
}
