package energysmartco;

import energysmartco.controller.AlertController;
import energysmartco.controller.DeviceController;
import energysmartco.controller.ReportController;
import energysmartco.controller.UserController;
import energysmartco.model.*;
import energysmartco.service.ConsumoService;
import energysmartco.service.SimulacionService;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

class EnergySmartTest {

    @Test
    void electrodomesticoCalculaConsumo() {
        Electrodomestico nevera = new Electrodomestico(1, "Nevera", 150, 24);
        assertEquals(3600.0, nevera.calcularConsumo(), 0.001);
    }

    @Test
    void usuarioGettersSetters() {
        Usuario u = new Usuario(1, "Ana", "ana@test.com", "pass");
        assertEquals("Ana", u.getNombre());
        assertEquals("ana@test.com", u.getCorreo());
    }

    @Test
    void alertaNivelesCorrectamente() {
        Alerta alta = new Alerta("Consumo alto", "ALTO");
        assertEquals("ALTO", alta.getNivel());
    }

    @Test
    void simulacionGuardaPeriodo() {
        Simulacion sim = new Simulacion("MENSUAL", 150.5);
        assertEquals("MENSUAL", sim.getPeriodo());
        assertEquals(150.5, sim.getResultado(), 0.001);
    }

    @Test
    void consumoServiceCalculaCosto() {
        ConsumoService svc = new ConsumoService();
        assertEquals(60000.0, svc.calcularCosto(100, 600), 0.001);
    }

    @Test
    void consumoServiceMensual() {
        ConsumoService svc = new ConsumoService();
        assertEquals(300.0, svc.calcularConsumoMensual(10), 0.001);
    }

    @Test
    void consumoNegativoDevuelveCero() {
        ConsumoService svc = new ConsumoService();
        assertEquals(0.0, svc.calcularCosto(-10, 600), 0.001);
    }

    @Test
    void simulacionServicePeriodos() {
        SimulacionService svc = new SimulacionService();
        List<Electrodomestico> lista = Arrays.asList(
            new Electrodomestico(1, "Nevera", 150, 24)
        );
        assertEquals(3.6,   svc.simularConsumo(lista, "DIARIO").getResultado(),  0.01);
        assertEquals(25.2,  svc.simularConsumo(lista, "SEMANAL").getResultado(), 0.01);
        assertEquals(108.0, svc.simularConsumo(lista, "MENSUAL").getResultado(), 0.01);
    }

    @Test
    void userControllerLoginCorrecto() {
        assertTrue(new UserController().login("admin@energysmart.com", "1234"));
    }

    @Test
    void userControllerLoginIncorrecto() {
        assertFalse(new UserController().login("otro@mail.com", "wrong"));
    }

    @Test
    void userControllerCamposVacios() {
        assertFalse(new UserController().login("", ""));
    }

    @Test
    void alertControllerNivelesAlerta() {
        AlertController ctrl = new AlertController();
        assertEquals("BAJO",  ctrl.generarAlerta(50).getNivel());
        assertEquals("MEDIO", ctrl.generarAlerta(200).getNivel());
        assertEquals("ALTO",  ctrl.generarAlerta(400).getNivel());
    }

    @Test
    void deviceControllerAceptaDispositivo() {
        assertDoesNotThrow(() ->
            new DeviceController().agregarDispositivo(
                new Electrodomestico(1, "Ventilador", 75, 8)));
    }

    @Test
    void reportControllerGeneraReporte() {
        ReportController ctrl = new ReportController();
        Reporte r = ctrl.generarReportePDF(new ConsumoEnergetico(3.6, 108.0, 64800.0));
        assertNotNull(r);
        assertTrue(r.getDescripcion().contains("REPORTE"));
    }
}
