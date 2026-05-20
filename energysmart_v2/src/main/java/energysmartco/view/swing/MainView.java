package energysmartco.view.swing;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * MainView — menú principal de la aplicación.
 * Desde aquí el usuario navega a todas las secciones del sistema.
 */
public class MainView extends JFrame {

    // ─────────────────────────────────────────────
    public MainView() {
        configurarVentana();
        construirUI();
    }

    // ─────────────────────────────────────────────
    private void configurarVentana() {
        setTitle("EnergySmart CO — Menú principal");
        setSize(520, 460);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(new Color(245, 248, 252));
    }

    // ─────────────────────────────────────────────
    private void construirUI() {
        setLayout(new BorderLayout(10, 10));

        // ── Banner ──
        JPanel banner = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 16));
        banner.setBackground(new Color(34, 139, 87));
        banner.setPreferredSize(new Dimension(0, 65));

        JLabel titulo = new JLabel("⚡  EnergySmart CO — Panel Principal");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        titulo.setForeground(Color.WHITE);
        banner.add(titulo);
        add(banner, BorderLayout.NORTH);

        // ── Subtítulo ──
        JLabel subtitulo = new JLabel("Selecciona una opción para continuar",
                                      SwingConstants.CENTER);
        subtitulo.setFont(new Font("SansSerif", Font.ITALIC, 13));
        subtitulo.setForeground(Color.DARK_GRAY);
        subtitulo.setBorder(new EmptyBorder(10, 0, 0, 0));
        add(subtitulo, BorderLayout.CENTER);

        // ── Grid de botones ──
        JPanel grid = new JPanel(new GridLayout(3, 2, 14, 14));
        grid.setBorder(new EmptyBorder(4, 30, 30, 30));
        grid.setBackground(new Color(245, 248, 252));

        grid.add(crearBoton("🏠  Electrodomésticos",
                new Color(41, 128, 185), e -> abrirDispositivos()));
        grid.add(crearBoton("📊  Dashboard",
                new Color(142, 68, 173), e -> abrirDashboard()));
        grid.add(crearBoton("📄  Reportes",
                new Color(39, 174, 96), e -> abrirReportes()));
        grid.add(crearBoton("🔔  Alertas",
                new Color(230, 126, 34), e -> abrirAlertas()));
        grid.add(crearBoton("🔬  Simulación",
                new Color(41, 182, 246), e -> abrirSimulacion()));
        grid.add(crearBoton("🚪  Cerrar sesión",
                new Color(192, 57, 43), e -> cerrarSesion()));

        // Centramos el grid verticalmente usando BorderLayout
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(new Color(245, 248, 252));
        wrapper.add(subtitulo, BorderLayout.NORTH);
        wrapper.add(grid, BorderLayout.CENTER);
        add(wrapper, BorderLayout.CENTER);
    }

    // ─────────────────────────────────────────────
    /** Crea un botón estilizado con color y acción personalizados. */
    private JButton crearBoton(String texto, Color color,
                               java.awt.event.ActionListener accion) {
        JButton btn = new JButton(texto);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("SansSerif", Font.BOLD, 13));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(12, 10, 12, 10));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        // Efecto hover sutil
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBackground(color.darker());
            }
            @Override public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(color);
            }
        });
        btn.addActionListener(accion);
        return btn;
    }

    // ── Navegación ─────────────────────────────
    private void abrirDispositivos() {
        new DeviceView().setVisible(true);
    }

    private void abrirDashboard() {
        new DashboardSwingView().setVisible(true);
    }

    private void abrirReportes() {
        new ReportView().setVisible(true);
    }

    private void abrirAlertas() {
        new AlertView().setVisible(true);
    }

    private void abrirSimulacion() {
        JOptionPane.showMessageDialog(this,
            "Módulo de simulación disponible desde la vista Reportes.",
            "Simulación", JOptionPane.INFORMATION_MESSAGE);
    }

    private void cerrarSesion() {
        int confirm = JOptionPane.showConfirmDialog(this,
            "¿Deseas cerrar sesión?", "Cerrar sesión",
            JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            dispose();
            SwingUtilities.invokeLater(() -> new LoginView().setVisible(true));
        }
    }
}
