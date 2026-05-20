package energysmartco.view.swing;

import energysmartco.controller.UserController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * LoginView — ventana de inicio de sesión.
 *
 * Credenciales de prueba:
 *   correo   : admin@energysmart.com
 *   contraseña: 1234
 *
 * Al autenticarse correctamente abre el MainView (menú principal).
 */
public class LoginView extends JFrame {

    private JTextField     campoCorreo;
    private JPasswordField campoPassword;
    private JLabel         lblMensaje;

    private final UserController userController = new UserController();

    // ─────────────────────────────────────────────
    public LoginView() {
        configurarVentana();
        construirUI();
    }

    // ─────────────────────────────────────────────
    private void configurarVentana() {
        setTitle("EnergySmart CO — Iniciar sesión");
        setSize(420, 340);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(new Color(245, 248, 252));
    }

    // ─────────────────────────────────────────────
    private void construirUI() {
        setLayout(new BorderLayout());

        // ── Banner superior ──
        JPanel banner = new JPanel();
        banner.setBackground(new Color(34, 139, 87));
        banner.setPreferredSize(new Dimension(0, 70));
        banner.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 18));

        JLabel titulo = new JLabel("⚡  EnergySmart CO");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 20));
        titulo.setForeground(Color.WHITE);
        banner.add(titulo);

        add(banner, BorderLayout.NORTH);

        // ── Panel central con formulario ──
        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(new Color(245, 248, 252));
        form.setBorder(new EmptyBorder(20, 40, 10, 40));
        GridBagConstraints g = new GridBagConstraints();
        g.fill = GridBagConstraints.HORIZONTAL;
        g.insets = new Insets(6, 0, 6, 0);

        // Correo
        g.gridx = 0; g.gridy = 0;
        form.add(new JLabel("Correo electrónico:"), g);

        g.gridy = 1;
        campoCorreo = new JTextField("admin@energysmart.com");
        campoCorreo.setPreferredSize(new Dimension(300, 30));
        form.add(campoCorreo, g);

        // Contraseña
        g.gridy = 2;
        form.add(new JLabel("Contraseña:"), g);

        g.gridy = 3;
        campoPassword = new JPasswordField("1234");
        form.add(campoPassword, g);

        // Botón
        g.gridy = 4;
        g.insets = new Insets(14, 0, 6, 0);
        JButton btnLogin = new JButton("Iniciar sesión");
        btnLogin.setBackground(new Color(34, 139, 87));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        btnLogin.setFont(new Font("SansSerif", Font.BOLD, 13));
        btnLogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnLogin.addActionListener(e -> intentarLogin());
        form.add(btnLogin, g);

        // Mensaje de error/éxito
        g.gridy = 5;
        g.insets = new Insets(4, 0, 0, 0);
        lblMensaje = new JLabel(" ");
        lblMensaje.setHorizontalAlignment(SwingConstants.CENTER);
        lblMensaje.setFont(new Font("SansSerif", Font.PLAIN, 12));
        form.add(lblMensaje, g);

        add(form, BorderLayout.CENTER);

        // ── Pie ──
        JLabel pie = new JLabel("Sistema de Gestión de Consumo Energético  |  v1.0",
                                SwingConstants.CENTER);
        pie.setFont(new Font("SansSerif", Font.ITALIC, 10));
        pie.setForeground(Color.GRAY);
        pie.setBorder(new EmptyBorder(0, 0, 8, 0));
        add(pie, BorderLayout.SOUTH);

        // Enter en contraseña también hace login
        campoPassword.addActionListener(e -> intentarLogin());
    }

    // ─────────────────────────────────────────────
    private void intentarLogin() {
        String correo   = campoCorreo.getText().trim();
        String password = new String(campoPassword.getPassword());

        if (correo.isEmpty() || password.isEmpty()) {
            mostrarMensaje("Completa todos los campos.", Color.RED);
            return;
        }

        boolean ok = userController.login(correo, password);

        if (ok) {
            mostrarMensaje("✅ Autenticado correctamente…", new Color(34, 139, 87));
            // Pequeña pausa visual antes de abrir la siguiente pantalla
            Timer t = new Timer(700, ev -> abrirMenuPrincipal());
            t.setRepeats(false);
            t.start();
        } else {
            mostrarMensaje("❌ Credenciales incorrectas.", Color.RED);
        }
    }

    private void mostrarMensaje(String texto, Color color) {
        lblMensaje.setText(texto);
        lblMensaje.setForeground(color);
    }

    private void abrirMenuPrincipal() {
        dispose();
        SwingUtilities.invokeLater(() -> new MainView().setVisible(true));
    }
}
