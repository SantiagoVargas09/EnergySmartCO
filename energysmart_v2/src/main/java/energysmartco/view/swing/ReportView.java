package energysmartco.view.swing;

import energysmartco.controller.ReportController;
import energysmartco.dao.ElectrodomesticoDAO;
import energysmartco.model.ConsumoEnergetico;
import energysmartco.model.Electrodomestico;
import energysmartco.model.Reporte;
import energysmartco.model.Simulacion;
import energysmartco.service.ConsumoService;
import energysmartco.service.ReporteService;
import energysmartco.service.SimulacionService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

/**
 * ReportView — reportes, estadísticas y simulación.
 *
 * Lee siempre desde ElectrodomesticoDAO.getInstance()
 * → las simulaciones usan los dispositivos reales del usuario,
 *   incluyendo los que agregó en DeviceView.
 */
public class ReportView extends JFrame {

    private static final double TARIFA = 600.0;

    private JTextArea         areaReporte;
    private JComboBox<String> comboPeriodo;
    private JLabel            lblDisp;

    // ── Singleton compartido ───────────────────────────────────
    private final ElectrodomesticoDAO dao        = ElectrodomesticoDAO.getInstance();
    private final ConsumoService      consumoSvc  = new ConsumoService();
    private final ReportController    reportCtrl  = new ReportController();
    private final SimulacionService   simSvc      = new SimulacionService();
    private final ReporteService      reporteSvc  = new ReporteService();

    public ReportView() {
        setTitle("EnergySmart CO — Reportes");
        setSize(600, 520);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(0, 0));

        add(crearHeader(),  BorderLayout.NORTH);
        add(crearCentro(), BorderLayout.CENTER);
        add(crearPie(),    BorderLayout.SOUTH);
    }

    // ── Header ────────────────────────────────────────────────
    private JPanel crearHeader() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(new Color(39, 174, 96));

        JLabel l = new JLabel("📄  Reportes y Estadísticas");
        l.setFont(new Font("SansSerif", Font.BOLD, 17));
        l.setForeground(Color.WHITE);
        l.setBorder(new EmptyBorder(12, 14, 12, 0));
        p.add(l, BorderLayout.WEST);

        // Contador de dispositivos activos
        lblDisp = new JLabel();
        lblDisp.setFont(new Font("SansSerif", Font.ITALIC, 11));
        lblDisp.setForeground(new Color(200, 240, 210));
        lblDisp.setBorder(new EmptyBorder(0, 0, 0, 14));
        actualizarContador();
        p.add(lblDisp, BorderLayout.EAST);
        return p;
    }

    private void actualizarContador() {
        int n = dao.listar().size();
        lblDisp.setText(n + " dispositivo(s) en memoria");
    }

    // ── Área de texto ─────────────────────────────────────────
    private JPanel crearCentro() {
        JPanel p = new JPanel(new BorderLayout(8, 8));
        p.setBorder(new EmptyBorder(12, 14, 0, 14));
        p.setBackground(Color.WHITE);

        areaReporte = new JTextArea();
        areaReporte.setEditable(false);
        areaReporte.setFont(new Font("Monospaced", Font.PLAIN, 12));
        areaReporte.setBackground(new Color(250, 252, 255));
        areaReporte.setBorder(new EmptyBorder(8, 8, 8, 8));
        areaReporte.setText("Presiona un botón para generar reporte o simulación.\n" +
                            "Los cálculos usan los dispositivos actuales en memoria.\n");

        p.add(new JScrollPane(areaReporte), BorderLayout.CENTER);
        return p;
    }

    // ── Botones ───────────────────────────────────────────────
    private JPanel crearPie() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        p.setBackground(new Color(245, 248, 252));
        p.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(220, 220, 220)));

        comboPeriodo = new JComboBox<>(new String[]{"DIARIO", "SEMANAL", "MENSUAL"});
        comboPeriodo.setPreferredSize(new Dimension(110, 30));

        JButton btnReporte  = boton("📄 Generar Reporte",  new Color(39, 174, 96));
        JButton btnSimular  = boton("🔬 Simular consumo",  new Color(41, 182, 246));
        JButton btnExportar = boton("📤 Exportar datos",   new Color(142, 68, 173));
        JButton btnLimpiar  = boton("🗑 Limpiar",          new Color(127, 140, 141));

        btnReporte.addActionListener(e  -> generarReporte());
        btnSimular.addActionListener(e  -> simularConsumo());
        btnExportar.addActionListener(e -> exportarDatos());
        btnLimpiar.addActionListener(e  -> areaReporte.setText(""));

        p.add(btnReporte);
        p.add(new JLabel("Periodo:"));
        p.add(comboPeriodo);
        p.add(btnSimular);
        p.add(btnExportar);
        p.add(btnLimpiar);
        return p;
    }

    // ── Lógica ────────────────────────────────────────────────

    /** Lee la lista ACTUAL del singleton cada vez que se llama. */
    private List<Electrodomestico> obtenerDispositivos() {
        return dao.listar();
    }

    private ConsumoEnergetico calcularConsumo() {
        List<Electrodomestico> lista = obtenerDispositivos();
        double totalWh    = lista.stream().mapToDouble(Electrodomestico::calcularConsumo).sum();
        double kwhDiario  = totalWh / 1000.0;
        double kwhMensual = consumoSvc.calcularConsumoMensual(kwhDiario);
        double costo      = consumoSvc.calcularCosto(kwhMensual, TARIFA);
        return new ConsumoEnergetico(kwhDiario, kwhMensual, costo);
    }

    private void generarReporte() {
        actualizarContador();
        List<Electrodomestico> lista = obtenerDispositivos();
        ConsumoEnergetico consumo    = calcularConsumo();
        Reporte r = reportCtrl.generarReportePDF(consumo);

        areaReporte.append("─".repeat(52) + "\n");
        areaReporte.append(r.getDescripcion() + "\n\n");
        areaReporte.append("Dispositivos incluidos (" + lista.size() + "):\n");
        for (Electrodomestico e : lista) {
            areaReporte.append(String.format("  • %-22s %.0f Wh/día\n",
                e.getNombre(), e.calcularConsumo()));
        }
        areaReporte.append("\n");
        areaReporte.append(reporteSvc.generarEstadisticas(consumo) + "\n\n");
    }

    private void simularConsumo() {
        actualizarContador();
        String periodo   = (String) comboPeriodo.getSelectedItem();
        List<Electrodomestico> lista = obtenerDispositivos();
        Simulacion sim   = simSvc.simularConsumo(lista, periodo);
        double costo     = consumoSvc.calcularCosto(sim.getResultado(), TARIFA);

        areaReporte.append("─".repeat(52) + "\n");
        areaReporte.append(String.format(
            "SIMULACIÓN — Periodo: %s  |  Dispositivos: %d\n" +
            "  Consumo estimado : %.3f kWh\n" +
            "  Costo estimado   : $%,.0f COP\n\n",
            sim.getPeriodo(), lista.size(), sim.getResultado(), costo));
    }

    private void exportarDatos() {
        actualizarContador();
        ConsumoEnergetico consumo = calcularConsumo();
        reportCtrl.exportarDatos(consumo);
        areaReporte.append("─".repeat(52) + "\n");
        areaReporte.append("✅ Datos exportados en consola (modo simulación).\n\n");
    }

    private JButton boton(String texto, Color color) {
        JButton b = new JButton(texto);
        b.setBackground(color);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setFont(new Font("SansSerif", Font.BOLD, 12));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return b;
    }
}
