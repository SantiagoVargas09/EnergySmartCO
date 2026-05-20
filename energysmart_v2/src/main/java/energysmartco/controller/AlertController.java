package energysmartco.controller;

import energysmartco.model.Alerta;
import energysmartco.service.AlertaService;

/**
 * Controlador de alertas.
 * Intermediario entre la vista AlertView y el servicio AlertaService.
 */
public class AlertController {

    private final AlertaService alertaService = new AlertaService();

    /**
     * Verifica si el consumo mensual supera los umbrales definidos.
     */
    public boolean verificarConsumo(double consumoMensualKwh) {
        return alertaService.detectarConsumoAlto(consumoMensualKwh);
    }

    /**
     * Genera y devuelve la alerta correspondiente al consumo.
     */
    public Alerta generarAlerta(double consumoMensualKwh) {
        Alerta alerta = alertaService.generarAlerta(consumoMensualKwh);
        alertaService.enviarNotificacion(alerta);
        return alerta;
    }
}
