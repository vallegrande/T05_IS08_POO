package pe.vallegrande.gamelibrary.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    // ── Configuración Docker ───────────────────────────────
    private static final String HOST    = "localhost";
    private static final String PUERTO  = "3307";   // 👈 CAMBIO IMPORTANTE
    private static final String BASE    = "db_gamelibrary";
    private static final String USUARIO = "root";
    private static final String CLAVE   = "1234";   // 👈 TU NUEVA CONTRASEÑA

    private static final String URL =
            "jdbc:mysql://" + HOST + ":" + PUERTO + "/" + BASE
                    + "?useSSL=false&serverTimezone=America/Lima&allowPublicKeyRetrieval=true";

    private static Connection instancia = null;

    private Conexion() {}

    public static Connection getConexion() {
        try {
            if (instancia == null || instancia.isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                instancia = DriverManager.getConnection(URL, USUARIO, CLAVE);
                System.out.println("[DB] Conexión establecida con db_gamelibrary");
            }
        } catch (ClassNotFoundException e) {
            System.err.println("[DB] Driver MySQL no encontrado: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("[DB] Error de conexión: " + e.getMessage());
        }
        return instancia;
    }

    public static void cerrar() {
        try {
            if (instancia != null && !instancia.isClosed()) {
                instancia.close();
                System.out.println("[DB] Conexión cerrada.");
            }
        } catch (SQLException e) {
            System.err.println("[DB] Error al cerrar: " + e.getMessage());
        }
    }
}