package com.jorge.jdbc.model;

import java.util.Objects;

public class Alumno {

    private int id; // 0 mientras no se ha guardado todavia en la base de datos
    private String nombre;
    private double nota;

    public Alumno(String nombre, double nota) {
        this(0, nombre, nota);
    }

    public Alumno(int id, String nombre, double nota) {
        this.id = id;
        this.nombre = nombre;
        this.nota = nota;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public double getNota() { return nota; }
    public void setNota(double nota) { this.nota = nota; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Alumno otro = (Alumno) o;
        return id == otro.id && Double.compare(nota, otro.nota) == 0 && Objects.equals(nombre, otro.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, nota);
    }

    @Override
    public String toString() {
        return "Alumno{id=" + id + ", nombre='" + nombre + "', nota=" + nota + "}";
    }
}
