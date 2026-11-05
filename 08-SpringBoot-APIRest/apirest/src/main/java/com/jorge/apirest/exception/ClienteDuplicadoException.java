package com.jorge.apirest.exception;

/** Se lanza cuando el código de cliente ya existe -> HTTP 409. */
public class ClienteDuplicadoException extends RuntimeException {
    public ClienteDuplicadoException(String mensaje) {
        super(mensaje);
    }
}
