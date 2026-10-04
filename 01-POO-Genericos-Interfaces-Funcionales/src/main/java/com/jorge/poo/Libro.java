package com.jorge.poo;

import java.util.Objects;

public class Libro implements Prestable {

    private final String titulo;
    private final String autor;
    private final int anio;
    private EstadoLibro estado;

    public Libro(String titulo, String autor, int anio) {
        this.titulo = titulo;
        this.autor = autor;
        this.anio = anio;
        this.estado = EstadoLibro.DISPONIBLE;
    }

    public String getTitulo() { return titulo; }
    public String getAutor() { return autor; }
    public int getAnio() { return anio; }
    public EstadoLibro getEstado() { return estado; }

    @Override
    public boolean estaDisponible() {
        return estado == EstadoLibro.DISPONIBLE;
    }

    @Override
    public void prestar() {
        if (!estaDisponible()) {
            throw new IllegalStateException("El libro \"" + titulo + "\" ya esta prestado o reservado");
        }
        estado = EstadoLibro.PRESTADO;
    }

    @Override
    public void devolver() {
        estado = EstadoLibro.DISPONIBLE;
    }

    // equals() y hashCode() van SIEMPRE juntos (ver apuntes, seccion 12):
    // dos libros son "iguales" si tienen el mismo titulo, autor y anio.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Libro otro = (Libro) o;
        return anio == otro.anio
                && Objects.equals(titulo, otro.titulo)
                && Objects.equals(autor, otro.autor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(titulo, autor, anio);
    }

    @Override
    public String toString() {
        return "Libro{\"" + titulo + "\" de " + autor + " (" + anio + "), " + estado + "}";
    }
}
