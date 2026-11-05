package com.jorge.apirest.exception;

/** Se lanza cuando no existe el cliente buscado -> HTTP 404. */
public class ClienteNotFoundException extends RuntimeException {
    public ClienteNotFoundException(String mensaje) {
        super(mensaje);
    }
}
