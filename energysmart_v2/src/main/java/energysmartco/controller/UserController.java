package energysmartco.controller;

import energysmartco.utils.Validator;

/**
 * Controlador de usuarios.
 * Se encarga de la lógica relacionada con el login y manejo de cuentas.
 * El controlador es el intermediario entre la vista (pantalla) y los datos.
 */
public class UserController {

    /**
     * Verifica si el correo y la contraseña coinciden con un usuario válido.
     *
     * @param correo    El correo que escribió el usuario
     * @param password  La contraseña que escribió el usuario
     * @return true si las credenciales son correctas, false si no
     */
    public boolean login(String correo, String password) {
        // Primero revisamos que ningún campo esté vacío
        if (Validator.campoVacio(correo) || Validator.campoVacio(password)) {
            System.out.println("Login fallido: campos vacíos.");
            return false;
        }

        // Por ahora usamos credenciales fijas para pruebas
        // En una versión real, esto consultaría la base de datos
        boolean credencialesCorrectas = correo.equals("admin@energysmart.com")
                && password.equals("1234");

        if (credencialesCorrectas) {
            System.out.println("Login exitoso para: " + correo);
        } else {
            System.out.println("Login fallido: credenciales incorrectas.");
        }

        return credencialesCorrectas;
    }
}
