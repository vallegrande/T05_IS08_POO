package pe.vallegrande.gamelibrary.controller;

import pe.vallegrande.gamelibrary.database.Conexion;
import pe.vallegrande.gamelibrary.model.Juego;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * CONTROLLER — JuegoController
 * Gestiona las operaciones CRUD de la tabla 'juegos'.
 */
public class JuegoController {

    // ── CREATE ────────────────────────────────────────────────────────────────
    public boolean insertar(Juego j) {
        String sql = """
            INSERT INTO juegos (titulo, desarrollador, anio_lanzamiento, plataforma, precio, disponible, categoria_id)
            VALUES (?, ?, ?, ?, ?, ?, ?)
            """;
        try (PreparedStatement ps = Conexion.getConexion().prepareStatement(sql)) {
            ps.setString(1, j.getTitulo());
            ps.setString(2, j.getDesarrollador());
            ps.setInt(3, j.getAnioLanzamiento());
            ps.setString(4, j.getPlataforma());
            ps.setDouble(5, j.getPrecio());
            ps.setBoolean(6, j.isDisponible());
            ps.setInt(7, j.getCategoriaId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("[JuegoController] Error insertar: " + e.getMessage());
            return false;
        }
    }

    // ── READ (todos con nombre de categoría) ──────────────────────────────────
    public List<Juego> listarTodos() {
        List<Juego> lista = new ArrayList<>();
        String sql = """
            SELECT j.id, j.titulo, j.desarrollador, j.anio_lanzamiento,
                   j.plataforma, j.precio, j.disponible, j.categoria_id,
                   c.nombre AS categoria_nombre
            FROM juegos j
            INNER JOIN categorias c ON j.categoria_id = c.id
            ORDER BY j.titulo
            """;
        try (Statement st = Conexion.getConexion().createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Juego j = new Juego(
                    rs.getInt("id"),
                    rs.getString("titulo"),
                    rs.getString("desarrollador"),
                    rs.getInt("anio_lanzamiento"),
                    rs.getString("plataforma"),
                    rs.getDouble("precio"),
                    rs.getBoolean("disponible"),
                    rs.getInt("categoria_id")
                );
                j.setCategoriaNombre(rs.getString("categoria_nombre"));
                lista.add(j);
            }
        } catch (SQLException e) {
            System.err.println("[JuegoController] Error listar: " + e.getMessage());
        }
        return lista;
    }

    // ── UPDATE ────────────────────────────────────────────────────────────────
    public boolean actualizar(Juego j) {
        String sql = """
            UPDATE juegos
            SET titulo = ?, desarrollador = ?, anio_lanzamiento = ?,
                plataforma = ?, precio = ?, disponible = ?, categoria_id = ?
            WHERE id = ?
            """;
        try (PreparedStatement ps = Conexion.getConexion().prepareStatement(sql)) {
            ps.setString(1, j.getTitulo());
            ps.setString(2, j.getDesarrollador());
            ps.setInt(3, j.getAnioLanzamiento());
            ps.setString(4, j.getPlataforma());
            ps.setDouble(5, j.getPrecio());
            ps.setBoolean(6, j.isDisponible());
            ps.setInt(7, j.getCategoriaId());
            ps.setInt(8, j.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("[JuegoController] Error actualizar: " + e.getMessage());
            return false;
        }
    }

    // ── DELETE ────────────────────────────────────────────────────────────────
    public boolean eliminar(int id) {
        String sql = "DELETE FROM juegos WHERE id = ?";
        try (PreparedStatement ps = Conexion.getConexion().prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("[JuegoController] Error eliminar: " + e.getMessage());
            return false;
        }
    }

    /** Verifica si un título ya existe (para validación de duplicados) */
    public boolean existeTitulo(String titulo, int excludeId) {
        String sql = "SELECT COUNT(*) FROM juegos WHERE titulo = ? AND id != ?";
        try (PreparedStatement ps = Conexion.getConexion().prepareStatement(sql)) {
            ps.setString(1, titulo);
            ps.setInt(2, excludeId);
            ResultSet rs = ps.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException e) {
            System.err.println("[JuegoController] Error existeTitulo: " + e.getMessage());
            return false;
        }
    }
}
