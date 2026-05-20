package energysmartco.app;

import energysmartco.view.swing.LoginView;

/**
 * Punto de entrada de EnergySmart CO.
 *
 * Flujo de navegación:
 *   LoginView → MainView → DeviceView / DashboardSwingView / ReportView / AlertView
 *
 * Credenciales de prueba:
 *   correo   : admin@energysmart.com
 *   contraseña: 1234
 */
public class MainApp {

    public static void main(String[] args) {
        System.out.println("EnergySmart CO iniciando...");

        // Aseguramos que la GUI se cree en el hilo de eventos de Swing (EDT)
        javax.swing.SwingUtilities.invokeLater(() -> {
            LoginView login = new LoginView();
            login.setVisible(true);
        });
    }
}
