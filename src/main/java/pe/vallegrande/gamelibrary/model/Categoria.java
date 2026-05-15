package pe.vallegrande.gamelibrary.model;

/**
 * MODEL — Categoria
 * Representa una categoría de videojuego en la base de datos.
 */
public class Categoria {

    private int     id;
    private String  nombre;
    private String  descripcion;
    private boolean activo;

    // ── Constructores ─────────────────────────────────────────────────────────

    public Categoria() {}

    public Categoria(int id, String nombre, String descripcion, boolean activo) {
        this.id          = id;
        this.nombre      = nombre;
        this.descripcion = descripcion;
        this.activo      = activo;
    }

    /** Constructor sin ID para INSERT */
    public Categoria(String nombre, String descripcion, boolean activo) {
        this.nombre      = nombre;
        this.descripcion = descripcion;
        this.activo      = activo;
    }

    // ── Getters / Setters ─────────────────────────────────────────────────────

    public int     getId()          { return id; }
    public void    setId(int id)    { this.id = id; }

    public String  getNombre()              { return nombre; }
    public void    setNombre(String nombre) { this.nombre = nombre; }

    public String  getDescripcion()                   { return descripcion; }
    public void    setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public boolean isActivo()              { return activo; }
    public void    setActivo(boolean activo) { this.activo = activo; }

    /** Para mostrar en JComboBox */
    @Override
    public String toString() {
        return nombre;
    }
}
