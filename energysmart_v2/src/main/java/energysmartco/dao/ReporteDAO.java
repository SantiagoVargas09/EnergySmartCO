package energysmartco.dao;

import energysmartco.model.Reporte;

import java.util.ArrayList;
import java.util.List;

/**
 * DAO para reportes.
 * Simula guardado en memoria.
 */
public class ReporteDAO {

    private final List<Reporte> reportes = new ArrayList<>();

    /**
     * Guarda un reporte generado.
     */
    public void guardarReporte(Reporte reporte) {
        if (reporte == null) {
            System.out.println("Error: reporte nulo.");
            return;
        }
        reportes.add(reporte);
        System.out.println("Reporte guardado: " + reporte.getFecha());
    }

    /**
     * Lista todos los reportes guardados.
     */
    public List<Reporte> listarReportes() {
        return new ArrayList<>(reportes);
    }
}
