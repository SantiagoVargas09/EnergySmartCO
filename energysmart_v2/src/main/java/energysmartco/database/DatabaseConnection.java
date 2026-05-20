package energysmartco.database;

/**
 * Clase que maneja la conexión a la base de datos.
 * 
 * Usamos el patrón Singleton: esto significa que solo puede existir
 * UNA sola conexión a la BD en todo el programa. Es como tener una sola
 * llave para abrir la misma puerta — no necesitas varias llaves.
 */
public class DatabaseConnection {

    // La única instancia que va a existir de esta clase
    private static DatabaseConnection instancia;

    // El constructor es privado para que nadie pueda crear otra instancia desde afuera
    private DatabaseConnection() {}

    /**
     * Devuelve la instancia única de la conexión.
     * Si no existe todavía, la crea. Si ya existe, devuelve la misma.
     */
    public static DatabaseConnection getInstancia() {
        if (instancia == null) {
            instancia = new DatabaseConnection();
        }
        return instancia;
    }

    /**
     * Simula la conexión a la base de datos.
     * En una versión real, aquí se conectaría con JDBC a MySQL, PostgreSQL, etc.
     * 
     * Ejemplo con MySQL:
     * Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/energydb", user, pass);
     */
    public void conectar() {
        System.out.println("Conexión a la base de datos establecida.");
        System.out.println("(En producción aquí iría la conexión JDBC real)");
    }

    /**
     * Cierra la conexión con la base de datos.
     * Siempre hay que cerrarla cuando ya no la necesitemos para liberar recursos.
     */
    public void desconectar() {
        System.out.println("Conexión a la base de datos cerrada.");
    }
}
