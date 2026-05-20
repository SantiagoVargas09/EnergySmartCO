package energysmartco.model;

/**
 * Representa el resultado de una simulación de consumo energético.
 * periodo puede ser "DIARIO", "SEMANAL" o "MENSUAL".
 */
public class Simulacion {

    private String periodo;
    private double resultado;  // kWh estimados para el periodo

    public Simulacion() {}

    public Simulacion(String periodo, double resultado) {
        this.periodo   = periodo;
        this.resultado = resultado;
    }

    // --- Getters y Setters ---
    public String getPeriodo()         { return periodo; }
    public void   setPeriodo(String p) { this.periodo = p; }

    public double getResultado()          { return resultado; }
    public void   setResultado(double r)  { this.resultado = r; }

    @Override
    public String toString() {
        return String.format("Simulacion{periodo='%s', resultado=%.3f kWh}", periodo, resultado);
    }
}
