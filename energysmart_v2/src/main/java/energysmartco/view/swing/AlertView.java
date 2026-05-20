package energysmartco.view.swing;

import energysmartco.controller.AlertController;
import energysmartco.controller.UserController;
import energysmartco.dao.ElectrodomesticoDAO;
import energysmartco.model.Alerta;
import energysmartco.model.Electrodomestico;
import energysmartco.service.ConsumoService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

/**
 * AlertView — alertas basadas en los dispositivos reales del singleton.
 */
public class AlertView extends JFrame {

    private JTextField     campoCorreo;
    private JPasswordField campoPassword;
    private JTextArea      areaAlertas;
    private JTextField     campoConsumo;

    private final UserController      userCtrl   = new UserController();
    private final AlertController     alertCtrl  = new AlertController();
    private final ConsumoService      consumoSvc = new ConsumoService();
    private final ElectrodomesticoDAO dao        = ElectrodomesticoDAO.getInstance();

    public AlertView() {
        setTitle("EnergySmart CO — Alertas");
        setSize(480, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(0, 6));
        getContentPane().setBackground(new Color(245, 248, 252));

        add(crearHeader(),     BorderLayout.NORTH);
        add(crearPanelLogin(), BorderLayout.CENTER);
        add(crearPie(),        BorderLayout.SOUTH);
    }

    public void mostrarAlertas() {
        abrirPanelAlertas();
    }

    private JPanel crearHeader() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 14, 12));
        p.setBackground(new Color(230, 126, 34));
        JLabel l = new JLabel("🔔  Sistema de Alertas — EnergySmart CO");
        l.setFont(new Font("SansSerif", Font.BOLD, 16));
        l.setForeground(Color.WHITE);
        p.add(l);
        return p;
    }

    private JPanel crearPanelLogin() {
        JPanel p = new JPanel(new GridBagLayout());
        p.setBackground(new Color(245, 248, 252));
        p.setBorder(new EmptyBorder(20, 50, 10, 50));
        GridBagConstraints g = new GridBagConstraints();
        g.fill = GridBagConstraints.HORIZONTAL;
        g.insets = new Insets(6, 0, 6, 0);
        g.gridx = 0;

        g.gridy = 0;
        JLabel info = new JLabel("Inicia sesión para ver las alertas del sistema.");
        info.setFont(new Font("SansSerif", Font.ITALIC, 12));
        info.setForeground(Color.GRAY);
        p.add(info, g);

        g.gridy = 1; p.add(new JLabel("Correo:"), g);
        g.gridy = 2;
        campoCorreo = new JTextField("admin@energysmart.com");
        p.add(campoCorreo, g);

        g.gridy = 3; p.add(new JLabel("Contraseña:"), g);
        g.gridy = 4;
        campoPassword = new JPasswordField("1234");
        p.add(campoPassword, g);

        g.gridy = 5; g.insets = new Insets(14, 0, 0, 0);
        JButton btn = new JButton("Verificar y ver alertas");
        btn.setBackground(new Color(230, 126, 34));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("SansSerif", Font.BOLD, 13));
        btn.addActionListener(e -> intentarLoginAlertas());
        p.add(btn, g);
        return p;
    }

    private JPanel crearPie() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 8));
        p.setBackground(new Color(245, 248, 252));
        p.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(220, 220, 220)));

        p.add(new JLabel("Consumo manual (kWh/mes):"));
        campoConsumo = new JTextField("280", 8);
        p.add(campoConsumo);

        JButton btn = new JButton("Verificar alerta");
        btn.setBackground(new Color(230, 126, 34));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.addActionListener(e -> verificarConsumoManual());
        p.add(btn);
        return p;
    }

    private void intentarLoginAlertas() {
        String correo   = campoCorreo.getText().trim();
        String password = new String(campoPassword.getPassword());
        if (userCtrl.login(correo, password)) {
            abrirPanelAlertas();
        } else {
            JOptionPane.showMessageDialog(this,
                "Credenciales incorrectas.\n(correo: admin@energysmart.com / pass: 1234)",
                "Acceso denegado", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirPanelAlertas() {
        getContentPane().remove(1);

        areaAlertas = new JTextArea();
        areaAlertas.setEditable(false);
        areaAlertas.setFont(new Font("Monospaced", Font.PLAIN, 12));
        areaAlertas.setBackground(new Color(250, 252, 255));
        areaAlertas.setBorder(new EmptyBorder(10, 10, 10, 10));

        JScrollPane scroll = new JScrollPane(areaAlertas);
        scroll.setBorder(new EmptyBorder(8, 10, 0, 10));
        getContentPane().add(scroll, BorderLayout.CENTER);
        revalidate(); repaint();

        generarAlertaAutomatica();
    }

    private void generarAlertaAutomatica() {
        // Lee desde el singleton → dispositivos reales del usuario
        List<Electrodomestico> dispositivos = dao.listar();
        double totalWh    = dispositivos.stream()
                .mapToDouble(Electrodomestico::calcularConsumo).sum();
        double kwhMensual = consumoSvc.calcularConsumoMensual(totalWh / 1000.0);
        Alerta alerta     = alertCtrl.generarAlerta(kwhMensual);

        areaAlertas.append("=== ALERTAS DEL SISTEMA ===\n\n");
        areaAlertas.append(String.format(
            "Dispositivos en memoria : %d\n" +
            "Consumo mensual total   : %.2f kWh\n\n",
            dispositivos.size(), kwhMensual));
        areaAlertas.append("Nivel   : " + alerta.getNivel() + "\n");
        areaAlertas.append("Mensaje : " + alerta.getMensaje() + "\n\n");

        areaAlertas.append("─── Detalle por dispositivo ───\n");
        for (Electrodomestico e : dispositivos) {
            double kwh     = e.calcularConsumo() / 1000.0;
            double mensual = consumoSvc.calcularConsumoMensual(kwh);
            areaAlertas.append(String.format(
                "  %-22s %.3f kWh/día  →  %.1f kWh/mes\n",
                e.getNombre(), kwh, mensual));
        }
        areaAlertas.append("\nSistema de alertas listo.\n");
    }

    private void verificarConsumoManual() {
        if (areaAlertas == null) {
            JOptionPane.showMessageDialog(this, "Inicia sesión primero.",
                "Sin sesión", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            double kwh    = Double.parseDouble(campoConsumo.getText().trim());
            Alerta alerta = alertCtrl.generarAlerta(kwh);
            areaAlertas.append("\n─── Verificación manual (" + kwh + " kWh/mes) ───\n");
            areaAlertas.append("Nivel   : " + alerta.getNivel() + "\n");
            areaAlertas.append("Mensaje : " + alerta.getMensaje() + "\n");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ingresa un número válido.",
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
