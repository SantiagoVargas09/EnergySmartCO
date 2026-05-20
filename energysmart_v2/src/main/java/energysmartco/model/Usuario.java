package energysmartco.model;

/**
 * Representa a un usuario del sistema.
 * Guarda sus datos básicos: id, nombre, correo y contraseña.
 */
public class Usuario {

    private int id;
    private String nombre;
    private String correo;
    private String password;

    // Constructor vacío, útil cuando creamos un usuario y luego le ponemos los datos uno por uno
    public Usuario() {}

    // Constructor con todos los datos de una sola vez
    public Usuario(int id, String nombre, String correo, String password) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.password = password;
    }

    // --- Getters y Setters ---
    // Son los métodos para leer y modificar los atributos privados

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    /**
     * Devuelve una versión legible del usuario.
     * Esto es útil para imprimir en consola o mostrar en logs.
     * No mostramos la contraseña por seguridad.
     */
    @Override
    public String toString() {
        return "Usuario{id=" + id + ", nombre='" + nombre + "', correo='" + correo + "'}";
    }
}
