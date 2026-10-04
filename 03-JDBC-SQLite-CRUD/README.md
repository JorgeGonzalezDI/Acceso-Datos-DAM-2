# 03 - JDBC con SQLite (CRUD real)

## Dependencias
Este proyecto SI tiene dependencias de Maven (se descargan solas la
primera vez que IntelliJ lo importe; hace falta conexion a internet):
- `org.xerial:sqlite-jdbc` - driver JDBC para SQLite.
- `org.junit.jupiter:junit-jupiter` - para el test.

## Que ensena
- **Arquitectura en capas**: `model.Alumno` / `service.AlumnoService` / `controller.AlumnoController`.
- **JDBC real**: `Connection`, `PreparedStatement`, `ResultSet`, con SQLite (no necesita servidor, todo vive en un fichero `alumnos.db`).
- **CRUD completo**: Create, Read, Update, Delete.
- **Testing con JUnit**: `AlumnoServiceTest`, con `@BeforeEach`/`@AfterEach` para partir de una base de datos limpia en cada test.

## Como ejecutarlo
1. Abre la carpeta del proyecto en IntelliJ (File -> Open) y espera a que Maven descargue las dependencias (barra de progreso abajo a la derecha).
2. Ejecuta `Main.java`: crea `alumnos.db` en la carpeta del proyecto y hace el CRUD completo por consola.
3. Ejecuta `AlumnoServiceTest` (boton Run en la clase, dentro de `src/test/java`) para ver los 4 tests en verde.
