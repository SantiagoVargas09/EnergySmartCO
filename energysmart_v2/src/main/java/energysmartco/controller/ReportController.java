package energysmartco.controller;

import energysmartco.dao.ReporteDAO;
import energysmartco.model.ConsumoEnergetico;
import energysmartco.model.Reporte;
import energysmartco.service.ReporteService;

/**
 * Controlador de reportes.
 * Coordina la generación, estadísticas y persistencia de reportes.
 */
public class ReportController {

    private final ReporteService reporteService = new ReporteService();
    private final ReporteDAO     reporteDAO     = new ReporteDAO();

    /**
     * Genera un reporte PDF (simulado) y lo guarda.
     *
     * @param consumo Datos de consumo energético
     * @return El reporte generado
     */
    public Reporte generarReportePDF(ConsumoEnergetico consumo) {
        Reporte r = reporteService.crearReporte(consumo);
        reporteDAO.guardarReporte(r);
        System.out.println("Reporte PDF generado (simulado).");
        return r;
    }

    /**
     * Exporta los datos de consumo (simulado como texto en consola).
     *
     * @param consumo Datos a exportar
     */
    public void exportarDatos(ConsumoEnergetico consumo) {
        String stats = reporteService.generarEstadisticas(consumo);
        System.out.println("== EXPORTACIÓN DE DATOS ==");
        System.out.println(stats);
    }
}
