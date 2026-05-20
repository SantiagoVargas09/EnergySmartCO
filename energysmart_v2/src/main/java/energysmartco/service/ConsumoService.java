package energysmartco.service;

/**
 * Servicio que maneja los cálculos relacionados con el consumo energético.
 * Los "servicios" contienen la lógica del negocio, separada de la vista y los datos.
 */
public class ConsumoService {

    /**
     * Calcula el costo según el consumo y la tarifa eléctrica.
     *
     * @param consumo  Consumo en kWh
     * @param tarifa   Precio por kWh (en la moneda local)
     * @return El costo total a pagar
     */
    public double calcularCosto(double consumo, double tarifa) {
        // Si algún valor es negativo no tiene sentido calcularlo
        if (consumo < 0 || tarifa < 0) {
            System.out.println("Advertencia: consumo o tarifa no pueden ser negativos.");
            return 0;
        }
        return consumo * tarifa;
    }

    /**
     * Estima el consumo mensual dado un consumo diario.
     * Asumimos que un mes tiene 30 días (simplificación común en facturación).
     *
     * @param consumoDiario Consumo en kWh por día
     * @return Consumo estimado al mes
     */
    public double calcularConsumoMensual(double consumoDiario) {
        return consumoDiario * 30;
    }
}
