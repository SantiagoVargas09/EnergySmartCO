package energysmartco.view.swing;

import energysmartco.controller.DeviceController;
import energysmartco.dao.ElectrodomesticoDAO;
import energysmartco.model.Electrodomestico;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * DeviceView — CRUD de electrodomésticos.
 * Usa ElectrodomesticoDAO.getInstance() para compartir
 * la lista con Dashboard y Reportes.
 */
public class DeviceView extends JFrame {

    private DefaultTableModel modeloTabla;
    private JTable            tabla;

    private JTextField campoNombre;
    private JTextField campoPotencia;
    private JTextField campoHoras;

    // ── Singleton compartido ───────────────────────────────────
    private final ElectrodomesticoDAO dao       = ElectrodomesticoDAO.getInstance();
    private final DeviceController    deviceCtrl = new DeviceController();

    public DeviceView() {
        configurarVentana();
        construirUI();
        refrescarTabla(); // los datos ya vienen del singleton (precargados)
    }

    private void configurarVentana() {
        setTitle("EnergySmart CO — Electrodomésticos");
        setSize(640, 480);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);
    }

    private void construirUI() {
        setLayout(new BorderLayout(8, 8));

        // ── Encabezado ──
        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT, 14, 10));
        header.setBackground(new Color(41, 128, 185));
        JLabel lbl = new JLabel("🏠  Gestión de Electrodomésticos");
        lbl.setFont(new Font("SansSerif", Font.BOLD, 16));
        lbl.setForeground(Color.WHITE);
        header.add(lbl);
        add(header, BorderLayout.NORTH);

        // ── Tabla ──
        String[] columnas = {"ID", "Nombre", "Potencia (W)", "Horas/día", "Consumo/día (Wh)"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tabla = new JTable(modeloTabla);
        tabla.setRowHeight(22);
        tabla.getTableHeader().setBackground(new Color(41, 128, 185));
        tabla.getTableHeader().setForeground(Color.WHITE);
        tabla.setSelectionBackground(new Color(210, 234, 255));
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        // ── Panel inferior ──
        JPanel panelInferior = new JPanel(new BorderLayout(8, 8));
        panelInferior.setBorder(new EmptyBorder(8, 10, 10, 10));
        panelInferior.setBackground(Color.WHITE);

        JPanel form = new JPanel(new GridLayout(1, 6, 6, 0));
        form.setBackground(Color.WHITE);

        form.add(new JLabel("Nombre:"));
        campoNombre   = new JTextField();
        form.add(campoNombre);

        form.add(new JLabel("Potencia (W):"));
        campoPotencia = new JTextField();
        form.add(campoPotencia);

        form.add(new JLabel("Horas/día:"));
        campoHoras    = new JTextField();
        form.add(campoHoras);

        panelInferior.add(form, BorderLayout.CENTER);

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        botones.setBackground(Color.WHITE);

        JButton btnAgregar  = boton("Agregar",  new Color(39, 174, 96));
        JButton btnEliminar = boton("Eliminar", new Color(192, 57, 43));
        JButton btnLimpiar  = boton("Limpiar",  new Color(127, 140, 141));

        btnAgregar.addActionListener(e  -> agregarDispositivo());
        btnEliminar.addActionListener(e -> eliminarSeleccionado());
        btnLimpiar.addActionListener(e  -> limpiarFormulario());

        botones.add(btnAgregar);
        botones.add(btnEliminar);
        botones.add(btnLimpiar);

        panelInferior.add(botones, BorderLayout.SOUTH);
        add(panelInferior, BorderLayout.SOUTH);
    }

    private void refrescarTabla() {
        modeloTabla.setRowCount(0);
        for (Electrodomestico e : dao.listar()) {
            modeloTabla.addRow(new Object[]{
                e.getId(),
                e.getNombre(),
                e.getPotencia(),
                e.getHorasUso(),
                String.format("%.1f", e.calcularConsumo())
            });
        }
    }

    private void agregarDispositivo() {
        try {
            String nombre   = campoNombre.getText().trim();
            double potencia = Double.parseDouble(campoPotencia.getText().trim());
            double horas    = Double.parseDouble(campoHoras.getText().trim());

            if (nombre.isEmpty())             { mostrarError("El nombre no puede estar vacío."); return; }
            if (potencia <= 0 || horas <= 0)  { mostrarError("Potencia y horas deben ser positivos."); return; }

            Electrodomestico e = new Electrodomestico(0, nombre, potencia, horas);
            deviceCtrl.agregarDispositivo(e);
            dao.guardar(e); // guarda en el singleton compartido
            refrescarTabla();
            limpiarFormulario();

        } catch (NumberFormatException ex) {
            mostrarError("Potencia y Horas deben ser números válidos.");
        }
    }

    private void eliminarSeleccionado() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) { mostrarError("Selecciona un electrodoméstico primero."); return; }
        int id = (int) modeloTabla.getValueAt(fila, 0);
        dao.eliminar(id);
        refrescarTabla();
    }

    private void limpiarFormulario() {
        campoNombre.setText("");
        campoPotencia.setText("");
        campoHoras.setText("");
        campoNombre.requestFocus();
    }

    private void mostrarError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
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
