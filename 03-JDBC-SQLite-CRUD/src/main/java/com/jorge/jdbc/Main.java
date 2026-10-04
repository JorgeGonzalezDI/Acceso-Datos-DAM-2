package com.jorge.jdbc;

import com.jorge.jdbc.controller.AlumnoController;
import com.jorge.jdbc.service.AlumnoService;

public class Main {
    public static void main(String[] args) {
        // alumnos.db se crea solo, en la carpeta del proyecto, la primera vez que se ejecuta.
        AlumnoService service = new AlumnoService("alumnos.db");
        AlumnoController controller = new AlumnoController(service);
        controller.ejecutarDemoCompleta();
    }
}
