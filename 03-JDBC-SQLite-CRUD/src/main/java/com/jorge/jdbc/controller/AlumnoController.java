package com.jorge.jdbc.controller;

import com.jorge.jdbc.model.Alumno;
import com.jorge.jdbc.service.AlumnoService;

import java.util.List;

/**
 * Coordina el flujo del programa: llama al service y decide que mostrar.
 * No sabe nada de SQL ni de como se guardan los datos por dentro.
 */
public class AlumnoController {

    private final AlumnoService service;

    public AlumnoController(AlumnoService service) {
        this.service = service;
    }

    public void ejecutarDemoCompleta() {
        System.out.println("=== 1. CREATE: guardando 3 alumnos ===");
        Alumno ana = service.guardar(new Alumno("Ana", 8.5));
        Alumno luis = service.guardar(new Alumno("Luis", 4.0));
        Alumno marta = service.guardar(new Alumno("Marta", 9.2));
        System.out.println("Guardados con id " + ana.getId() + ", " + luis.getId() + " y " + marta.getId());

        System.out.println("\n=== 2. READ: listando todos los alumnos ===");
        service.listarTodos().forEach(System.out::println);

        System.out.println("\n=== 3. UPDATE: Luis sube su nota a 5.5 ===");
        service.actualizarNota(luis.getId(), 5.5);
        service.listarTodos().forEach(System.out::println);

        System.out.println("\n=== 4. DELETE: eliminamos a Marta ===");
        service.eliminar(marta.getId());
        service.listarTodos().forEach(System.out::println);
    }
}
