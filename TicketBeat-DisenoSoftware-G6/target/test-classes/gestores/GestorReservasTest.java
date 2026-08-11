package com.ticketbeat.gestores;

import com.ticketbeat.estrategia_pago.PagoTarjetaStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

/**
 * Pruebas para GestorReservas.
 */
public class GestorReservasTest {

    private GestorReservas gestor;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        gestor = new GestorReservas();
        System.setOut(new PrintStream(outContent, true, StandardCharsets.UTF_8));
    }

    /**
     * Caso de prueba TC-GR-001 del plan de pruebas.
     */
    @Test
    public void testElegirCantidadYTipoDeEntrada_Tipico() {
        assertDoesNotThrow(() -> gestor.elegirCantidadYTipoDeEntrada());
        assertEquals("", outContent.toString(StandardCharsets.UTF_8),
                "El camino de disponibilidad confirmada no produce salida por consola (stubs vacíos).");
    }

    /**
     * Caso de prueba TC-GR-002 del plan de pruebas.
    */
    @Test
    public void testConfirmarCompra_Tipico() {
        gestor.setEstrategiaPago(new PagoTarjetaStrategy("Visa/Mastercard"));

        gestor.confirmarCompra(150.0, new HashMap<>());

        String output = outContent.toString(StandardCharsets.UTF_8);
        assertTrue(output.contains("Confirmación de pago (ID:"));
        assertTrue(output.contains("Confirmación de compra y boletos"));
    }

    /**
     * Caso de prueba TC-GR-003 del plan de pruebas.
     */
    @Test
    public void testConfirmarCompra_Error_EstrategiaNula() {
        gestor.confirmarCompra(100.0, new HashMap<>());

        String output = outContent.toString(StandardCharsets.UTF_8);
        assertTrue(output.contains("Error: No se ha seleccionado una estrategia de pago."));
    }

    /**
     * Caso de prueba TC-GR-004 del plan de pruebas.
     */
    @Test
    public void testConfirmarCompra_Limite_MontoCero() {
        gestor.setEstrategiaPago(new PagoTarjetaStrategy("Visa/Mastercard"));

        gestor.confirmarCompra(0.0, new HashMap<>());

        String output = outContent.toString(StandardCharsets.UTF_8);
        assertTrue(output.contains("Confirmación de compra y boletos"),
                "El pago se procesa igual como COMPLETADO, sin validar el monto.");
    }

    /**
     * Caso de prueba TC-GR-005 del plan de pruebas.
     */
    @Test
    public void testTiempoDeReservaExpirado_Tipico() {
        gestor.tiempoDeReservaExpirado();

        String output = outContent.toString(StandardCharsets.UTF_8);
        assertTrue(output.contains("Informar expiración de reserva"));
    }
}
