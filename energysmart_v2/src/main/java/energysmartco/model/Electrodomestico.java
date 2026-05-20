package energysmartco.model;

/**
 * Representa un electrodoméstico registrado en el sistema.
 * Tiene nombre, potencia en watts y cuántas horas al día se usa.
 */
public class Electrodomestico {

    private int id;
    private String nombre;
    private double potencia;   // en Watts
    private double horasUso;   // horas por día

    // Constructor vacío
    public Electrodomestico() {}

    // Constructor completo
    public Electrodomestico(int id, String nombre, double potencia, double horasUso) {
        this.id = id;
        this.nombre = nombre;
        this.potencia = potencia;
        this.horasUso = horasUso;
    }

    /**
     * Calcula el consumo diario en Wh (Watt-hora).
     * Fórmula: potencia x horas de uso
     * Ejemplo: una nevera de 150W que funciona 24h = 3600 Wh al día
     */
    public double calcularConsumo() {
        return potencia * horasUso;
    }

    // --- Getters y Setters ---
    // En el original faltaban todos estos, lo que impedía usar el objeto desde otros archivos

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public double getPotencia() { return potencia; }
    public void setPotencia(double potencia) { this.potencia = potencia; }

    public double getHorasUso() { return horasUso; }
    public void setHorasUso(double horasUso) { this.horasUso = horasUso; }

    /**
     * Muestra el electrodoméstico de forma legible.
     * Sin esto, al imprimir un objeto solo salía algo como "Electrodomestico@3a5f3b"
     */
    @Override
    public String toString() {
        return "Electrodomestico{id=" + id
                + ", nombre='" + nombre + "'"
                + ", potencia=" + potencia + "W"
                + ", horasUso=" + horasUso + "h"
                + ", consumoDiario=" + String.format("%.2f", calcularConsumo()) + "Wh}";
    }
}
