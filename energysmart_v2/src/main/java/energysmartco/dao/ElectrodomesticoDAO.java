package energysmartco.dao;

import energysmartco.model.Electrodomestico;

import java.util.ArrayList;
import java.util.List;

/**
 * ElectrodomesticoDAO — Singleton.
 *
 * Una única instancia vive en memoria durante toda la sesión.
 * DeviceView, DashboardSwingView y ReportView comparten
 * exactamente la misma lista → los cambios se reflejan en tiempo real.
 */
public class ElectrodomesticoDAO {

    // ── Singleton ──────────────────────────────────────────────
    private static ElectrodomesticoDAO instancia;

    /** Devuelve siempre la misma instancia. */
    public static ElectrodomesticoDAO getInstance() {
        if (instancia == null) {
            instancia = new ElectrodomesticoDAO();
            instancia.cargarPrecargados(); // carga los 4 iniciales una sola vez
        }
        return instancia;
    }

    // ── Estado interno ─────────────────────────────────────────
    private final List<Electrodomestico> repositorio = new ArrayList<>();
    private int contadorId = 1;

    // Constructor privado: nadie puede hacer new ElectrodomesticoDAO()
    private ElectrodomesticoDAO() {}

    /** Carga los 4 electrodomésticos de ejemplo. */
    private void cargarPrecargados() {
        guardar(new Electrodomestico(0, "Nevera",               150,  24.0));
        guardar(new Electrodomestico(0, "Televisor",            120,   6.0));
        guardar(new Electrodomestico(0, "Lavadora",             500,   1.5));
        guardar(new Electrodomestico(0, "Aire acondicionado",   900,   5.0));
    }

    // ── CRUD ───────────────────────────────────────────────────

    public void guardar(Electrodomestico e) {
        if (e == null) return;
        e.setId(contadorId++);
        repositorio.add(e);
        System.out.println("[DAO] Guardado: " + e);
    }

    public List<Electrodomestico> listar() {
        return new ArrayList<>(repositorio); // copia defensiva
    }

    public boolean actualizar(Electrodomestico actualizado) {
        for (int i = 0; i < repositorio.size(); i++) {
            if (repositorio.get(i).getId() == actualizado.getId()) {
                repositorio.set(i, actualizado);
                return true;
            }
        }
        return false;
    }

    public boolean eliminar(int id) {
        return repositorio.removeIf(e -> e.getId() == id);
    }
}
