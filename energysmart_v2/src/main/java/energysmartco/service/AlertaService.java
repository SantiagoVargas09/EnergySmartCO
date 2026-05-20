package energysmartco.service;

import energysmartco.model.Alerta;

/**
 * Servicio que detecta si el consumo supera umbrales predefinidos
 * y genera alertas para el usuario.
 */
public class AlertaService {

    // Umbrales en kWh/mes (valores de referencia para un hogar colombiano)
    private static final double UMBRAL_MEDIO = 150.0;
    private static final double UMBRAL_ALTO  = 300.0;

    /**
     * Revisa si el consumo mensual justifica una alerta.
     *
     * @param consumoMensualKwh Consumo mensual en kWh
     * @return true si supera el umbral MEDIO o ALTO
     */
    public boolean detectarConsumoAlto(double consumoMensualKwh) {
        return consumoMensualKwh > UMBRAL_MEDIO;
    }

    /**
     * Genera una alerta descriptiva según el nivel de consumo.
     *
     * @param consumoMensualKwh Consumo mensual en kWh
     * @return Objeto Alerta con nivel y mensaje
     */
    public Alerta generarAlerta(double consumoMensualKwh) {
        if (consumoMensualKwh > UMBRAL_ALTO) {
            return new Alerta(
                String.format("⚠ Consumo CRÍTICO: %.1f kWh/mes. Revisa nevera, A/C o calentador.", consumoMensualKwh),
                "ALTO"
            );
        } else if (consumoMensualKwh > UMBRAL_MEDIO) {
            return new Alerta(
                String.format("⚡ Consumo ELEVADO: %.1f kWh/mes. Considera apagar equipos en desuso.", consumoMensualKwh),
                "MEDIO"
            );
        } else {
            return new Alerta(
                String.format("✅ Consumo NORMAL: %.1f kWh/mes. ¡Buen trabajo!", consumoMensualKwh),
                "BAJO"
            );
        }
    }

    /**
     * Envía una notificación (simulada por consola).
     */
    public void enviarNotificacion(Alerta alerta) {
        System.out.println("[NOTIFICACIÓN] Nivel " + alerta.getNivel() + ": " + alerta.getMensaje());
    }
}
