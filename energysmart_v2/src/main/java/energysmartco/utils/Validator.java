package energysmartco.utils;

/**
 * Clase de utilidades para validar datos del usuario.
 * Centralizar las validaciones aquí evita repetir el mismo código en varios lugares.
 */
public class Validator {

    /**
     * Revisa si un texto está vacío o solo tiene espacios.
     * Esto pasa mucho cuando el usuario no llena un campo del formulario.
     *
     * @param texto El texto a revisar
     * @return true si está vacío, false si tiene contenido
     */
    public static boolean campoVacio(String texto) {
        return texto == null || texto.trim().isEmpty();
    }

    /**
     * Verifica que un correo tenga formato válido (básico).
     * Solo revisa que tenga @ y un punto después — no es perfecta pero sirve para empezar.
     *
     * @param correo El correo a validar
     * @return true si parece válido, false si no
     */
    public static boolean correoValido(String correo) {
        if (campoVacio(correo)) return false;
        // Revisamos que tenga "@" y al menos un "." después del @
        return correo.contains("@") && correo.indexOf(".") > correo.indexOf("@");
    }

    /**
     * Verifica que un número sea positivo.
     * Útil para validar potencia de electrodomésticos, tarifas, etc.
     *
     * @param numero El número a validar
     * @return true si es mayor que cero, false si no
     */
    public static boolean esPositivo(double numero) {
        return numero > 0;
    }
}
