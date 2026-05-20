package energysmartco.view.swing;

import energysmartco.dao.ElectrodomesticoDAO;
import energysmartco.model.Electrodomestico;
import energysmartco.service.ConsumoService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

/**
 * DashboardSwingView — métricas y gráfica de consumo.
 *
 * Lee siempre desde ElectrodomesticoDAO.getInstance()
 * → refleja exactamente los dispositivos que el usuario
 *   agregó o eliminó en DeviceView.
 *
 * Botón "🔄 Actualizar" recalcula todo en tiempo real.
 */
public class DashboardSwingView extends JFrame {

    private static final double TARIFA = 600.0;

    private JPanel panelCentro;

    private final ElectrodomesticoDAO dao           = ElectrodomesticoDAO.getInstance();
    private final ConsumoService      consumoService = new ConsumoService();

    public DashboardSwingView() {
        setTitle("EnergySmart CO — Dashboard");
        setSize(640, 540);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(0, 0));

        add(crearHeader(), BorderLayout.NORTH);

        panelCentro = new JPanel(new BorderLayout());
        add(panelCentro, BorderLayout.CENTER);

        add(crearPie(), BorderLayout.SOUTH);

        renderizarDatos(); // carga inicial
    }

    // ── Header ────────────────────────────────────────────────
    private JPanel crearHeader() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 14, 12));
        p.setBackground(new Color(142, 68, 173));
        JLabel l = new JLabel("📊  Dashboard de Consumo Energético");
        l.setFont(new Font("SansSerif", Font.BOLD, 17));
        l.setForeground(Color.WHITE);
        p.add(l);
        return p;
    }

    // ── Pie con botón actualizar ───────────────────────────────
    private JPanel crearPie() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 8));
        p.setBackground(new Color(245, 248, 252));
        p.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(220, 220, 220)));

        JButton btnActualizar = new JButton("🔄 Actualizar datos");
        btnActualizar.setBackground(new Color(142, 68, 173));
        btnActualizar.setForeground(Color.WHITE);
        btnActualizar.setFocusPainted(false);
        btnActualizar.setFont(new Font("SansSerif", Font.BOLD, 12));
        btnActualizar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnActualizar.addActionListener(e -> renderizarDatos());
        p.add(btnActualizar);
        return p;
    }

    // ── Renderizado dinámico ───────────────────────────────────
    /**
     * Lee la lista actual del singleton y reconstruye
     * tarjetas + gráfica con los datos frescos.
     */
    private void renderizarDatos() {
        List<Electrodomestico> dispositivos = dao.listar();

        double totalWh    = dispositivos.stream()
                .mapToDouble(Electrodomestico::calcularConsumo).sum();
        double kwhDiario  = totalWh / 1000.0;
        double kwhMensual = consumoService.calcularConsumoMensual(kwhDiario);
        double costo      = consumoService.calcularCosto(kwhMensual, TARIFA);

        // Reconstruir el contenido del panel central
        panelCentro.removeAll();

        JPanel contenido = new JPanel(new BorderLayout(10, 10));
        contenido.setBackground(new Color(248, 250, 253));
        contenido.setBorder(new EmptyBorder(16, 20, 16, 20));

        // ── Tarjetas de resumen ──
        JPanel tarjetas = new JPanel(new GridLayout(1, 3, 14, 0));
        tarjetas.setBackground(new Color(248, 250, 253));

        tarjetas.add(tarjeta("Consumo diario",
                String.format("%.2f kWh", kwhDiario),
                new Color(41, 128, 185)));
        tarjetas.add(tarjeta("Consumo mensual",
                String.format("%.2f kWh", kwhMensual),
                new Color(142, 68, 173)));
        tarjetas.add(tarjeta("Costo estimado",
                String.format("$%,.0f COP", costo),
                new Color(39, 174, 96)));

        contenido.add(tarjetas, BorderLayout.NORTH);

        // ── Subtítulo gráfica ──
        JLabel lblInfo = new JLabel(
            "Dispositivos: " + dispositivos.size() + "   |   Consumo diario por dispositivo (Wh):",
            SwingConstants.LEFT);
        lblInfo.setFont(new Font("SansSerif", Font.BOLD, 12));
        lblInfo.setBorder(new EmptyBorder(10, 0, 6, 0));

        // ── Gráfica de barras ──
        GraficaBarras grafica = new GraficaBarras(dispositivos);
        grafica.setBackground(Color.WHITE);
        grafica.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));

        JPanel wrapGrafica = new JPanel(new BorderLayout(0, 4));
        wrapGrafica.setBackground(new Color(248, 250, 253));
        wrapGrafica.add(lblInfo,  BorderLayout.NORTH);
        wrapGrafica.add(grafica,  BorderLayout.CENTER);

        contenido.add(wrapGrafica, BorderLayout.CENTER);
        panelCentro.add(contenido);

        // Refrescar la UI
        panelCentro.revalidate();
        panelCentro.repaint();
    }

    // ── Tarjeta métrica ───────────────────────────────────────
    private JPanel tarjeta(String titulo, String valor, Color color) {
        JPanel p = new JPanel(new GridLayout(2, 1, 0, 4));
        p.setBackground(color);
        p.setBorder(new EmptyBorder(14, 14, 14, 14));
        p.setPreferredSize(new Dimension(0, 80));

        JLabel lblTitulo = new JLabel(titulo, SwingConstants.CENTER);
        lblTitulo.setFont(new Font("SansSerif", Font.PLAIN, 11));
        lblTitulo.setForeground(new Color(220, 240, 255));

        JLabel lblValor = new JLabel(valor, SwingConstants.CENTER);
        lblValor.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblValor.setForeground(Color.WHITE);

        p.add(lblTitulo);
        p.add(lblValor);
        return p;
    }

    // ── Gráfica de barras (canvas) ─────────────────────────────
    private static class GraficaBarras extends JPanel {

        private final List<Electrodomestico> datos;
        private final Color[] COLORES = {
            new Color(41, 128, 185), new Color(142, 68, 173),
            new Color(39, 174, 96),  new Color(230, 126, 34),
            new Color(231, 76, 60),  new Color(26, 188, 156),
            new Color(241, 196, 15), new Color(52, 73, 94)
        };

        GraficaBarras(List<Electrodomestico> datos) {
            this.datos = datos;
            // Alto dinámico según cantidad de dispositivos
            int alto = Math.max(180, datos.size() * 42 + 30);
            setPreferredSize(new Dimension(0, alto));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (datos.isEmpty()) {
                g.setColor(Color.GRAY);
                g.setFont(new Font("SansSerif", Font.ITALIC, 13));
                g.drawString("Sin dispositivos registrados.", 20, 40);
                return;
            }

            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                RenderingHints.VALUE_ANTIALIAS_ON);

            double maxConsumo = datos.stream()
                    .mapToDouble(Electrodomestico::calcularConsumo).max().orElse(1);

            int margenIzq  = 170;
            int margenDer  = 80;
            int margenTop  = 16;
            int altoBarra  = 26;
            int separacion = 16;
            int anchoDisp  = getWidth() - margenIzq - margenDer;

            for (int i = 0; i < datos.size(); i++) {
                Electrodomestico e = datos.get(i);
                double consumo = e.calcularConsumo();
                int y     = margenTop + i * (altoBarra + separacion);
                int ancho = (int) (consumo / maxConsumo * anchoDisp);
                ancho = Math.max(ancho, 4); // mínimo visible

                // Etiqueta nombre
                g2.setColor(Color.DARK_GRAY);
                g2.setFont(new Font("SansSerif", Font.PLAIN, 11));
                g2.drawString(e.getNombre(), 8, y + altoBarra - 7);

                // Barra
                g2.setColor(COLORES[i % COLORES.length]);
                g2.fillRoundRect(margenIzq, y, ancho, altoBarra, 8, 8);

                // Valor
                g2.setColor(Color.DARK_GRAY);
                g2.setFont(new Font("SansSerif", Font.BOLD, 10));
                g2.drawString(String.format("%.0f Wh", consumo),
                              margenIzq + ancho + 6, y + altoBarra - 7);
            }
        }
    }
}
