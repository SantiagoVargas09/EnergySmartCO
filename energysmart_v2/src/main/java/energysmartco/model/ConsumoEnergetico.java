package energysmartco.model;

/**
 * Representa el consumo energético calculado de un electrodoméstico.
 * Agrupa los valores diarios, mensuales y el costo estimado.
 */
public class ConsumoEnergetico {

    private double consumoDiario;    // kWh por día
    private double consumoMensual;   // kWh por mes (30 días)
    private double costoEstimado;    // en pesos colombianos

    public ConsumoEnergetico() {}

    public ConsumoEnergetico(double consumoDiario, double consumoMensual, double costoEstimado) {
        this.consumoDiario  = consumoDiario;
        this.consumoMensual = consumoMensual;
        this.costoEstimado  = costoEstimado;
    }

    // --- Getters y Setters ---
    public double getConsumoDiario()  { return consumoDiario; }
    public void   setConsumoDiario(double v)  { this.consumoDiario = v; }

    public double getConsumoMensual() { return consumoMensual; }
    public void   setConsumoMensual(double v) { this.consumoMensual = v; }

    public double getCostoEstimado()  { return costoEstimado; }
    public void   setCostoEstimado(double v)  { this.costoEstimado = v; }

    @Override
    public String toString() {
        return String.format("ConsumoEnergetico{diario=%.3f kWh, mensual=%.3f kWh, costo=$%.2f}",
                consumoDiario, consumoMensual, costoEstimado);
    }
}
