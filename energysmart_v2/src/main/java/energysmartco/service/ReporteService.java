package energysmartco.service;

import energysmartco.model.ConsumoEnergetico;
import energysmartco.model.Reporte;

import java.util.Date;

/**
 * Servicio encargado de generar reportes de consumo y estadísticas.
 */
public class ReporteService {

    private static final double TARIFA_COP_KWH = 600.0;

    /**
     * Crea un reporte a partir del consumo energético calculado.
     *
     * @param consumo Objeto con los datos de consumo diario/mensual/costo
     * @return Reporte listo para mostrar o guardar
     */
    public Reporte crearReporte(ConsumoEnergetico consumo) {
        String descripcion = String.format(
            "REPORTE DE CONSUMO ENERGÉTICO\n" +
            "================================\n" +
            "Consumo diario : %.3f kWh\n" +
            "Consumo mensual: %.3f kWh\n" +
            "Costo estimado : $%.2f COP/mes\n" +
            "Tarifa aplicada: $%.0f COP/kWh",
            consumo.getConsumoDiario(),
            consumo.getConsumoMensual(),
            consumo.getCostoEstimado(),
            TARIFA_COP_KWH
        );
        return new Reporte(new Date(), descripcion);
    }

    /**
     * Genera estadísticas resumidas como texto.
     *
     * @param consumo Objeto de consumo
     * @return String con estadísticas formateadas
     */
    public String generarEstadisticas(ConsumoEnergetico consumo) {
        double ahorroPotencial = consumo.getCostoEstimado() * 0.15; // 15% de ahorro posible
        return String.format(
            "Estadísticas rápidas:\n" +
            "  • Costo diario estimado : $%.2f COP\n" +
            "  • Ahorro potencial (15%%): $%.2f COP/mes\n" +
            "  • Equivalente en árboles: %.1f árboles/año compensados",
            consumo.getCostoEstimado() / 30.0,
            ahorroPotencial,
            consumo.getConsumoMensual() * 12 / 21.77  // 1 árbol absorbe ~21.77 kg CO2/año
        );
    }
}
