package com.jorge.apirest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Punto de entrada. @SpringBootApplication = configuración automática + escaneo de
 * componentes de este paquete y subpaquetes (entity, repository, service, controller...).
 * Es el equivalente al "main" de una app Java normal, pero arranca Tomcat y todo Spring.
 */
@SpringBootApplication
public class ApiRestApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiRestApplication.class, args);
    }
}
