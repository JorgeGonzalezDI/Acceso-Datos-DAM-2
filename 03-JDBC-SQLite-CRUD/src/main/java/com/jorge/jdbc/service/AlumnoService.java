package com.jorge.jdbc.service;

import com.jorge.jdbc.model.Alumno;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Capa de acceso a datos: aqui vive TODO el SQL. El resto de la
 * aplicacion (Main, Controller) nunca escribe una consulta directamente,
 * solo llama a estos metodos. Si un dia cambiamos SQLite por MySQL, solo
 * hay que tocar esta clase (ver apuntes, seccion 21: programar contra
 * interfaces / capas).
 */
public class AlumnoService {

    private final String urlBaseDeDatos;

    public AlumnoService(String rutaFichero) {
        this.urlBaseDeDatos = "jdbc:sqlite:" + rutaFichero;
        crearTablaSiNoExiste();
    }

    private void crearTablaSiNoExiste() {
        String sql = "CREATE TABLE IF NOT EXISTS alumnos ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "nombre TEXT NOT NULL, "
                + "nota REAL NOT NULL"
                + ")";
        try (Connection con = DriverManager.getConnection(urlBaseDeDatos);
             Statement st = con.createStatement()) {
            st.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException("No se pudo crear la tabla 'alumnos'", e);
        }
    }

    public Alumno guardar(Alumno alumno) {
        String sql = "INSERT INTO alumnos (nombre, nota) VALUES (?, ?)";
        try (Connection con = DriverManager.getConnection(urlBaseDeDatos);
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, alumno.getNombre());
            ps.setDouble(2, alumno.getNota());
            ps.executeUpdate();

            try (ResultSet claves = ps.getGeneratedKeys()) {
                if (claves.next()) {
                    alumno.setId(claves.getInt(1));
                }
            }
            return alumno;
        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar el alumno", e);
        }
    }

    public List<Alumno> listarTodos() {
        String sql = "SELECT id, nombre, nota FROM alumnos ORDER BY id";
        List<Alumno> resultado = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(urlBaseDeDatos);
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                resultado.add(new Alumno(rs.getInt("id"), rs.getString("nombre"), rs.getDouble("nota")));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar los alumnos", e);
        }
        return resultado;
    }

    public boolean actualizarNota(int id, double nuevaNota) {
        String sql = "UPDATE alumnos SET nota = ? WHERE id = ?";
        try (Connection con = DriverManager.getConnection(urlBaseDeDatos);
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDouble(1, nuevaNota);
            ps.setInt(2, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar la nota", e);
        }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM alumnos WHERE id = ?";
        try (Connection con = DriverManager.getConnection(urlBaseDeDatos);
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar el alumno", e);
        }
    }
}
