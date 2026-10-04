package com.jorge.jdbc.service;

import com.jorge.jdbc.model.Alumno;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AlumnoServiceTest {

    private static final String FICHERO_TEST = "test_alumnos.db";
    private AlumnoService service;

    @BeforeEach
    void crearServicioConBaseDeDatosLimpia() {
        new File(FICHERO_TEST).delete(); // por si quedo de una ejecucion anterior
        service = new AlumnoService(FICHERO_TEST);
    }

    @AfterEach
    void borrarBaseDeDatosDePrueba() {
        new File(FICHERO_TEST).delete();
    }

    @Test
    void guardarAsignaUnIdMayorQueCero() {
        // Arrange + Act
        Alumno alumno = service.guardar(new Alumno("Ana", 8.5));
        // Assert
        assertTrue(alumno.getId() > 0);
    }

    @Test
    void listarTodosDevuelveLosAlumnosGuardados() {
        service.guardar(new Alumno("Ana", 8.5));
        service.guardar(new Alumno("Luis", 4.0));

        List<Alumno> alumnos = service.listarTodos();

        assertEquals(2, alumnos.size());
    }

    @Test
    void actualizarNotaCambiaElValorGuardado() {
        Alumno alumno = service.guardar(new Alumno("Luis", 4.0));

        service.actualizarNota(alumno.getId(), 5.5);

        List<Alumno> alumnos = service.listarTodos();
        assertEquals(5.5, alumnos.get(0).getNota());
    }

    @Test
    void eliminarQuitaElAlumnoDeLaLista() {
        Alumno alumno = service.guardar(new Alumno("Marta", 9.2));

        boolean eliminado = service.eliminar(alumno.getId());

        assertTrue(eliminado);
        assertTrue(service.listarTodos().isEmpty());
    }
}
