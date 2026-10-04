package com.jorge.colecciones;

/**
 * Excepcion propia (checked): representa un problema al interpretar una
 * linea del fichero de alumnos, no un error de programacion.
 */
public class NotaInvalidaException extends Exception {
    public NotaInvalidaException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
