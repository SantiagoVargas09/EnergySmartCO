package energysmartco.service;

import energysmartco.model.Electrodomestico;
import energysmartco.model.Simulacion;

import java.util.List;

/**
 * Servicio que simula el consumo energético de una lista de electrodomésticos.
 * Permite estimar cuánto consumirá el hogar en distintos periodos.
 */
public class SimulacionService {

    private static final double TARIFA_COP_KWH = 600.0; // ~$600 COP por kWh (referencia 2024)

    /**
     * Simula el consumo total de una lista de electrodomésticos.
     *
     * @param dispositivos Lista de electrodomésticos del usuario
     * @param periodo      "DIARIO", "SEMANAL" o "MENSUAL"
     * @return Objeto Simulacion con el resultado estimado en kWh
     */
    public Simulacion simularConsumo(List<Electrodomestico> dispositivos, String periodo) {
        double totalWh = 0;
        for (Electrodomestico e : dispositivos) {
            totalWh += e.calcularConsumo(); // Wh diarios
        }

        double kwhDiario = totalWh / 1000.0;
        double resultado;

        switch (periodo.toUpperCase()) {
            case "SEMANAL":  resultado = kwhDiario * 7;  break;
            case "MENSUAL":  resultado = kwhDiario * 30; break;
            default:         resultado = kwhDiario;       break; // DIARIO
        }

        return new Simulacion(periodo, resultado);
    }
}
