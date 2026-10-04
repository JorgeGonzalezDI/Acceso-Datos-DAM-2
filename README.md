# Acceso a Datos · DAM 2

**Alumno:** Jorge González Gómez
**Ciclo:** 2º DAM — Desarrollo de Aplicaciones Multiplataforma (Universidad Nebrija)
**Curso:** 2026-2027

---


Tres proyectos pequenos, para tener ya montada la base antes de que se vea
en clase. Abrelos en IntelliJ uno a uno (File -> Open, seleccionando la
carpeta de cada proyecto, la que contiene su propio `pom.xml`).

## 01 - POO, Genericos e Interfaces funcionales
Biblioteca con libros, clases abstractas (`Figura`), interfaces
(`Prestable`), un generico propio (`Caja<T>`) y las 4 interfaces
funcionales principales (`Predicate`, `Consumer`, `Supplier`) aplicadas a
la propia biblioteca. Tambien equals()/hashCode()/toString() en `Libro`.

## 02 - Colecciones, Streams, Excepciones y Ficheros
Gestion de notas de alumnos leyendo y escribiendo un fichero de texto.
Usa un `record` con validacion, una excepcion propia
(`NotaInvalidaException`) y Streams (`filter`, `map`, `sorted`,
referencias a metodos) para calcular medias y aprobados.

## 03 - JDBC con SQLite (acceso a datos real)
El proyecto mas importante de la carpeta: un CRUD completo
(Create/Read/Update/Delete) contra una base de datos SQLite real, con
capas **Model / Service / Controller** y un test JUnit
(`AlumnoServiceTest`). No necesita instalar ningun servidor de base de
datos: SQLite guarda todo en un fichero (`alumnos.db`) que se crea solo
la primera vez que se ejecuta.

Cada proyecto individual tiene, ademas, su propio README con mas detalle
y con las instrucciones exactas para ejecutarlo.
