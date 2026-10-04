# 02 - Colecciones, Streams, Excepciones y Ficheros

Proyecto Maven sin dependencias externas.

## Que ensena
- **`record` con validacion** (`Alumno`): lanza una excepcion si la nota no esta entre 0 y 10.
- **Excepcion propia**: `NotaInvalidaException`, para lineas de fichero mal formadas.
- **Lectura y escritura de ficheros** con `try-with-resources` (`GestorAlumnos`).
- **Streams**: `filter`, `mapToDouble`, `average`, `sorted`, `Comparator`, referencias a metodos (`Alumno::nota`, `System.out::println`).

## Como ejecutarlo
Ejecuta `Main.java`. Crea automaticamente un fichero `alumnos.txt` de
ejemplo (con una linea corrupta a proposito, para ver como se gestiona
el error) en la carpeta del proyecto, lo lee, y genera
`alumnos_aprobados.txt` con los aprobados.
