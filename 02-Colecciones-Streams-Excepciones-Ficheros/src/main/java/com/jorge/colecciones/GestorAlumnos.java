package com.jorge.colecciones;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GestorAlumnos {

    private final List<Alumno> alumnos = new ArrayList<>();

    public void anadir(Alumno alumno) {
        alumnos.add(alumno);
    }

    public List<Alumno> getAlumnos() {
        return alumnos;
    }

    public void cargarDesdeFichero(String ruta) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String linea;
            int numeroLinea = 0;
            while ((linea = br.readLine()) != null) {
                numeroLinea++;
                try {
                    alumnos.add(parsearLinea(linea));
                } catch (NotaInvalidaException e) {
                    System.out.println("Aviso: se ignora la linea " + numeroLinea + " (" + e.getMessage() + ")");
                }
            }
        }
    }

    private Alumno parsearLinea(String linea) throws NotaInvalidaException {
        String[] partes = linea.split(";");
        if (partes.length != 2) {
            throw new NotaInvalidaException("formato incorrecto: \"" + linea + "\"", null);
        }
        try {
            String nombre = partes[0].trim();
            double nota = Double.parseDouble(partes[1].trim());
            return new Alumno(nombre, nota);
        } catch (NumberFormatException e) {
            throw new NotaInvalidaException("la nota no es un numero: \"" + linea + "\"", e);
        } catch (IllegalArgumentException e) {
            throw new NotaInvalidaException(e.getMessage(), e);
        }
    }

    public void guardarEnFichero(String ruta) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ruta))) {
            for (Alumno alumno : alumnos) {
                bw.write(alumno.nombre() + ";" + alumno.nota());
                bw.newLine();
            }
        }
    }

    public double mediaGeneral() {
        return alumnos.stream()
                .mapToDouble(Alumno::nota)
                .average()
                .orElse(0.0);
    }

    public List<Alumno> aprobados() {
        return alumnos.stream()
                .filter(Alumno::aprobado)
                .toList();
    }

    public List<Alumno> ordenadosPorNota() {
        return alumnos.stream()
                .sorted(Comparator.comparingDouble(Alumno::nota).reversed())
                .toList();
    }
}
