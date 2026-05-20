package energysmartco.dao;

import energysmartco.model.Usuario;

/**
 * DAO = Data Access Object (Objeto de Acceso a Datos).
 * Su trabajo es comunicarse con la base de datos.
 * Así separamos la lógica de guardar/buscar datos del resto del programa.
 */
public class UsuarioDAO {

    /**
     * Guarda un usuario en la base de datos.
     * Por ahora solo simula el guardado imprimiendo en consola.
     * 
     * En una versión real aquí iría la conexión a la BD con SQL:
     * INSERT INTO usuarios (nombre, correo, password) VALUES (?, ?, ?)
     *
     * @param usuario El usuario que queremos guardar
     */
    public void guardarUsuario(Usuario usuario) {
        if (usuario == null) {
            System.out.println("Error: no se puede guardar un usuario nulo.");
            return;
        }

        // Simulación de guardado - el toString nos muestra los datos del usuario
        System.out.println("Guardando en BD: " + usuario);
        System.out.println("Usuario guardado exitosamente.");
    }

    /**
     * Busca un usuario por su correo electrónico.
     * Por ahora devuelve null porque no hay base de datos conectada.
     *
     * @param correo El correo a buscar
     * @return El usuario si existe, null si no se encuentra
     */
    public Usuario buscarPorCorreo(String correo) {
        // Aquí iría: SELECT * FROM usuarios WHERE correo = ?
        System.out.println("Buscando usuario con correo: " + correo);
        return null; // Pendiente: implementar con BD real
    }
}
