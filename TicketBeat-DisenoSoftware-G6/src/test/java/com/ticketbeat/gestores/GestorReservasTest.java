package com.ticketbeat.gestores;

import com.ticketbeat.estrategia_pago.PagoTarjetaStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;

public class GestorReservasTest {

    private GestorReservas gestorReservas;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        gestorReservas = new GestorReservas();
        System.setOut(new PrintStream(outContent));
    }

    /**
     * Test case TC-GR-001 from plan_pruebas.md
     * The private methods are stubs, so this test can only verify that no exceptions are thrown.
     */
    @Test
    public void testElegirCantidadYTipoDeEntrada_Típico() {
        // Since verificarDisponibilidad() is hardcoded to true, this path will always be taken.
        assertDoesNotThrow(() -> {
            gestorReservas.elegirCantidadYTipoDeEntrada();
        });
        // We can't verify the private method calls without refactoring or a mocking framework.
    }

    /**
     * Test case TC-GR-002 from plan_pruebas.md
     */
    @Test
    public void testConfirmarCompra_Típico() {
        gestorReservas.setEstrategiaPago(new PagoTarjetaStrategy("VISA"));
        gestorReservas.confirmarCompra(100.0, new HashMap<>());
        String output = outContent.toString();
        assertTrue(output.contains("Confirmación de pago"));
        assertTrue(output.contains("Confirmación de compra y boletos"));
        assertFalse(output.contains("Pago rechazado"));
    }

    /**
     * Test case TC-GR-003 from plan_pruebas.md
     */
    @Test
    public void testConfirmarCompra_Error_EstrategiaNula() {
        gestorReservas.confirmarCompra(100.0, new HashMap<>());
        String output = outContent.toString();
        assertTrue(output.contains("Error: No se ha seleccionado una estrategia de pago."));
        assertFalse(output.contains("Confirmación de pago"));
    }

    /**
     * Test case TC-GR-004 from plan_pruebas.md
     */
    @Test
    public void testConfirmarCompra_Límite_MontoCero() {
        gestorReservas.setEstrategiaPago(new PagoTarjetaStrategy("VISA"));
        gestorReservas.confirmarCompra(0.0, new HashMap<>());
        String output = outContent.toString();
        assertTrue(output.contains("Confirmación de pago"));
        assertTrue(output.contains("Confirmación de compra y boletos"));
    }

    /**
     * Test case TC-GR-005 from plan_pruebas.md
     */
    @Test
    public void testTiempoDeReservaExpirado_Típico() {
        gestorReservas.tiempoDeReservaExpirado();
        String output = outContent.toString();
        assertTrue(output.contains("Informar expiración de reserva"));
    }
}
