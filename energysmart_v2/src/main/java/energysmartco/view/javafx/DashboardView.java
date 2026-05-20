package energysmartco.view.javafx;

/**
 * Vista del panel principal (Dashboard).
 * Por ahora está preparada para integrarse con JavaFX cuando se configure la dependencia.
 * 
 * Para usar JavaFX hay que agregar la librería en NetBeans:
 * Propiedades del proyecto → Librerías → Agregar JAR/Folder → JavaFX SDK
 */
public class DashboardView {

    /**
     * Muestra el panel de control del usuario.
     * Aquí iría la lógica para mostrar gráficas de consumo, dispositivos, etc.
     */
    public void mostrarPanel() {
        // Por ahora imprimimos en consola para probar que el flujo funciona
        // Más adelante esto lanzaría una ventana JavaFX con gráficas reales
        System.out.println("Panel de control (Dashboard) cargado.");
        System.out.println("Aquí se mostrará el consumo energético del usuario.");
    }
}
