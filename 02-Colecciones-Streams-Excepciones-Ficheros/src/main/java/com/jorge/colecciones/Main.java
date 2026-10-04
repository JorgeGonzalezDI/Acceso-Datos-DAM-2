package com.jorge.colecciones;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        Path fichero = Path.of("alumnos.txt");

        // Creamos un fichero de ejemplo con una linea corrupta a proposito
        List<String> lineas = List.of(
                "Ana;8.5",
                "Luis;4.0",
                "Marta;9.2",
                "Pedro;esto-no-es-un-numero", // linea corrupta a proposito
                "Sofia;6.0"
        );
        Files.write(fichero, lineas);

        GestorAlumnos gestor = new GestorAlumnos();
        System.out.println("=== Cargando alumnos desde " + fichero.toAbsolutePath() + " ===");
        gestor.cargarDesdeFichero(fichero.toString());

        System.out.println("\n=== Todos los alumnos cargados ===");
        gestor.getAlumnos().forEach(System.out::println);

        System.out.printf("%n=== Media general: %.2f ===%n", gestor.mediaGeneral());

        System.out.println("\n=== Aprobados (Stream + filter + method reference) ===");
        gestor.aprobados().forEach(System.out::println);

        System.out.println("\n=== Ordenados de mayor a menor nota ===");
        gestor.ordenadosPorNota().forEach(System.out::println);

        Path salida = Path.of("alumnos_aprobados.txt");
        GestorAlumnos soloAprobados = new GestorAlumnos();
        gestor.aprobados().forEach(soloAprobados::anadir);
        soloAprobados.guardarEnFichero(salida.toString());
        System.out.println("\nGuardado el listado de aprobados en " + salida.toAbsolutePath());
    }
}
