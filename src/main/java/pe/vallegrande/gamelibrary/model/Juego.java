package pe.vallegrande.gamelibrary.model;

/**
 * MODEL — Juego
 * Representa un videojuego en la base de datos.
 */
public class Juego {

    private int       id;
    private String    titulo;
    private String    desarrollador;
    private int       anioLanzamiento;
    private String    plataforma;
    private double    precio;
    private boolean   disponible;
    private int       categoriaId;
    private String    categoriaNombre; // campo de apoyo para JTable

    // ── Constructores ─────────────────────────────────────────────────────────

    public Juego() {}

    public Juego(int id, String titulo, String desarrollador,
                 int anioLanzamiento, String plataforma,
                 double precio, boolean disponible, int categoriaId) {
        this.id               = id;
        this.titulo           = titulo;
        this.desarrollador    = desarrollador;
        this.anioLanzamiento  = anioLanzamiento;
        this.plataforma       = plataforma;
        this.precio           = precio;
        this.disponible       = disponible;
        this.categoriaId      = categoriaId;
    }

    // ── Getters / Setters ─────────────────────────────────────────────────────

    public int     getId()              { return id; }
    public void    setId(int id)        { this.id = id; }

    public String  getTitulo()                  { return titulo; }
    public void    setTitulo(String titulo)     { this.titulo = titulo; }

    public String  getDesarrollador()                       { return desarrollador; }
    public void    setDesarrollador(String desarrollador)   { this.desarrollador = desarrollador; }

    public int     getAnioLanzamiento()                         { return anioLanzamiento; }
    public void    setAnioLanzamiento(int anioLanzamiento)      { this.anioLanzamiento = anioLanzamiento; }

    public String  getPlataforma()                      { return plataforma; }
    public void    setPlataforma(String plataforma)     { this.plataforma = plataforma; }

    public double  getPrecio()                  { return precio; }
    public void    setPrecio(double precio)     { this.precio = precio; }

    public boolean isDisponible()                   { return disponible; }
    public void    setDisponible(boolean disponible) { this.disponible = disponible; }

    public int     getCategoriaId()                     { return categoriaId; }
    public void    setCategoriaId(int categoriaId)      { this.categoriaId = categoriaId; }

    public String  getCategoriaNombre()                         { return categoriaNombre; }
    public void    setCategoriaNombre(String categoriaNombre)   { this.categoriaNombre = categoriaNombre; }

    @Override
    public String toString() { return titulo; }
}
