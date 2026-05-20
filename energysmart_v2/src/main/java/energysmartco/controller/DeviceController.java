package energysmartco.controller;

import energysmartco.model.Electrodomestico;
import energysmartco.service.ConsumoService;

/**
 * Controlador de dispositivos (electrodomésticos).
 * Maneja las operaciones sobre los aparatos registrados en el sistema.
 */
public class DeviceController {

    // Usamos el servicio de consumo para calcular el costo energético
    private ConsumoService consumoService;

    public DeviceController() {
        this.consumoService = new ConsumoService();
    }

    /**
     * Registra un nuevo electrodoméstico en el sistema.
     * También muestra cuánto consumiría y cuánto costaría al día.
     *
     * @param e El electrodoméstico a agregar
     */
    public void agregarDispositivo(Electrodomestico e) {
        if (e == null) {
            System.out.println("Error: el dispositivo no puede ser nulo.");
            return;
        }

        // Gracias al toString() del modelo, esto ahora muestra info útil
        System.out.println("Dispositivo agregado: " + e);

        // Calculamos el costo diario con una tarifa de ejemplo ($/kWh)
        double consumoEnKwh = e.calcularConsumo() / 1000.0; // convertimos de Wh a kWh
        double tarifaEjemplo = 600.0; // pesos colombianos por kWh (valor de referencia)
        double costoEstimado = consumoService.calcularCosto(consumoEnKwh, tarifaEjemplo);

        System.out.printf("Consumo estimado: %.2f kWh/día → Costo: $%.2f/día%n",
                consumoEnKwh, costoEstimado);
    }
}
