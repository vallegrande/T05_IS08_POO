package pe.vallegrande.gamelibrary.controller;

import pe.vallegrande.gamelibrary.database.Conexion;
import pe.vallegrande.gamelibrary.model.Categoria;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * CONTROLLER — CategoriaController
 * Gestiona las operaciones CRUD de la tabla 'categorias'.
 */
public class CategoriaController {

    // ── CREATE ────────────────────────────────────────────────────────────────
    public boolean insertar(Categoria c) {
        String sql = "INSERT INTO categorias (nombre, descripcion, activo) VALUES (?, ?, ?)";
        try (PreparedStatement ps = Conexion.getConexion().prepareStatement(sql)) {
            ps.setString(1, c.getNombre());
            ps.setString(2, c.getDescripcion());
            ps.setBoolean(3, c.isActivo());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("[CategoriaController] Error insertar: " + e.getMessage());
            return false;
        }
    }

    // ── READ (todos) ──────────────────────────────────────────────────────────
    public List<Categoria> listarTodos() {
        List<Categoria> lista = new ArrayList<>();
        String sql = "SELECT id, nombre, descripcion, activo FROM categorias ORDER BY nombre";
        try (Statement st = Conexion.getConexion().createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Categoria(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("descripcion"),
                    rs.getBoolean("activo")
                ));
            }
        } catch (SQLException e) {
            System.err.println("[CategoriaController] Error listar: " + e.getMessage());
        }
        return lista;
    }

    // ── READ (solo activos, para ComboBox en Juegos) ──────────────────────────
    public List<Categoria> listarActivos() {
        List<Categoria> lista = new ArrayList<>();
        String sql = "SELECT id, nombre, descripcion, activo FROM categorias WHERE activo = 1 ORDER BY nombre";
        try (Statement st = Conexion.getConexion().createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Categoria(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("descripcion"),
                    rs.getBoolean("activo")
                ));
            }
        } catch (SQLException e) {
            System.err.println("[CategoriaController] Error listarActivos: " + e.getMessage());
        }
        return lista;
    }

    // ── UPDATE ────────────────────────────────────────────────────────────────
    public boolean actualizar(Categoria c) {
        String sql = "UPDATE categorias SET nombre = ?, descripcion = ?, activo = ? WHERE id = ?";
        try (PreparedStatement ps = Conexion.getConexion().prepareStatement(sql)) {
            ps.setString(1, c.getNombre());
            ps.setString(2, c.getDescripcion());
            ps.setBoolean(3, c.isActivo());
            ps.setInt(4, c.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("[CategoriaController] Error actualizar: " + e.getMessage());
            return false;
        }
    }

    // ── DELETE ────────────────────────────────────────────────────────────────
    public boolean eliminar(int id) {
        // Verificar si tiene juegos asociados
        String checkSql = "SELECT COUNT(*) FROM juegos WHERE categoria_id = ?";
        try (PreparedStatement ps = Conexion.getConexion().prepareStatement(checkSql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                return false; // no se puede eliminar, tiene juegos
            }
        } catch (SQLException e) {
            System.err.println("[CategoriaController] Error verificar juegos: " + e.getMessage());
            return false;
        }

        String sql = "DELETE FROM categorias WHERE id = ?";
        try (PreparedStatement ps = Conexion.getConexion().prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("[CategoriaController] Error eliminar: " + e.getMessage());
            return false;
        }
    }

    /** Verifica si un nombre ya existe (para validación de duplicados) */
    public boolean existeNombre(String nombre, int excludeId) {
        String sql = "SELECT COUNT(*) FROM categorias WHERE nombre = ? AND id != ?";
        try (PreparedStatement ps = Conexion.getConexion().prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setInt(2, excludeId);
            ResultSet rs = ps.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException e) {
            System.err.println("[CategoriaController] Error existeNombre: " + e.getMessage());
            return false;
        }
    }
}
