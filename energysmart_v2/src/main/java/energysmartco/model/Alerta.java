package energysmartco.model;

/**
 * Representa una alerta generada cuando el consumo supera el umbral normal.
 * nivel puede ser: "BAJO", "MEDIO", "ALTO"
 */
public class Alerta {

    private String mensaje;
    private String nivel;  // BAJO | MEDIO | ALTO

    public Alerta() {}

    public Alerta(String mensaje, String nivel) {
        this.mensaje = mensaje;
        this.nivel   = nivel;
    }

    // --- Getters y Setters ---
    public String getMensaje()       { return mensaje; }
    public void   setMensaje(String m){ this.mensaje = m; }

    public String getNivel()         { return nivel; }
    public void   setNivel(String n) { this.nivel = n; }

    @Override
    public String toString() {
        return "Alerta{nivel='" + nivel + "', mensaje='" + mensaje + "'}";
    }
}
