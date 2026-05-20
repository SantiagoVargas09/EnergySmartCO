package energysmartco.model;

import java.util.Date;

/**
 * Representa un reporte generado para un usuario.
 * Contiene la fecha de generación y una descripción del resumen energético.
 */
public class Reporte {

    private Date   fecha;
    private String descripcion;

    public Reporte() {}

    public Reporte(Date fecha, String descripcion) {
        this.fecha       = fecha;
        this.descripcion = descripcion;
    }

    // --- Getters y Setters ---
    public Date   getFecha()       { return fecha; }
    public void   setFecha(Date f) { this.fecha = f; }

    public String getDescripcion()        { return descripcion; }
    public void   setDescripcion(String d){ this.descripcion = d; }

    @Override
    public String toString() {
        return "Reporte{fecha=" + fecha + ", descripcion='" + descripcion + "'}";
    }
}
