package com.jorge.poo;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        System.out.println("=== 1. Herencia, polimorfismo y abstraccion ===");
        List<Figura> figuras = List.of(new Circulo(3), new Rectangulo(4, 5));
        for (Figura f : figuras) {
            f.describir(); // polimorfismo: cada una ejecuta SU area()
        }

        System.out.println("\n=== 2. Encapsulacion, enums e interfaces (Prestable) ===");
        Libro libro1 = new Libro("Cien anios de soledad", "Gabriel Garcia Marquez", 1967);
        Libro libro2 = new Libro("1984", "George Orwell", 1949);
        System.out.println(libro1 + " -> disponible: " + libro1.estaDisponible());
        libro1.prestar();
        System.out.println(libro1 + " -> disponible: " + libro1.estaDisponible());

        System.out.println("\n=== 3. equals()/hashCode(): dos libros con los mismos datos ===");
        Libro libro2Duplicado = new Libro("1984", "George Orwell", 1949);
        System.out.println("libro2.equals(libro2Duplicado) = " + libro2.equals(libro2Duplicado));
        System.out.println("mismo hashCode = " + (libro2.hashCode() == libro2Duplicado.hashCode()));

        System.out.println("\n=== 4. Genericos: Caja<T> ===");
        Caja<String> cajaTexto = new Caja<>();
        cajaTexto.guardar("Un texto cualquiera");
        System.out.println("Contenido de la caja: " + cajaTexto.sacar());

        System.out.println("\n=== 5. Interfaces funcionales con la Biblioteca ===");
        Biblioteca biblioteca = new Biblioteca();
        biblioteca.anadir(libro1);
        biblioteca.anadir(libro2);
        biblioteca.anadir(libro2Duplicado);

        // Predicate<Libro>: filtra por anio posterior a 1950
        List<Libro> modernos = biblioteca.buscar(libro -> libro.getAnio() > 1950);
        System.out.println("Libros posteriores a 1950: " + modernos);

        // Consumer<Libro>: imprime cada libro
        System.out.println("Listado completo:");
        biblioteca.paraCadaLibro(libro -> System.out.println("  - " + libro));

        // Supplier<Libro>: crea un libro "por defecto" solo si hiciera falta
        Libro primero = biblioteca.obtenerOCrear(() -> new Libro("Libro por defecto", "Anonimo", 2000));
        System.out.println("Primer libro (o el de por defecto): " + primero);
    }
}
