package com.ticketbeat.estrategia_pago;

import com.ticketbeat.modelo.Pago;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

/**
 * Pruebas para PagoTransferenciaStrategy.
 */
public class PagoTransferenciaStrategyTest {

    /**
     * Caso de prueba TC-PTrS-001 del plan de pruebas.
     */
    @Test
    public void testProcesarPago_Tipico() {
        PagoTransferenciaStrategy strategy = new PagoTransferenciaStrategy("Banco Pichincha");
        Pago pago = strategy.procesarPago(200.0, new HashMap<>());

        assertNotNull(pago);
        assertEquals("COMPLETADO", pago.getEstado());
    }

    /**
     * Caso de prueba TC-PTrS-002 del plan de pruebas.
     */
    @Test
    public void testRevertirPago_Tipico() {
        PagoTransferenciaStrategy strategy = new PagoTransferenciaStrategy("Banco Pichincha");
        assertTrue(strategy.revertirPago("ID-QUE-NO-EXISTE"));
    }

    /**
     * Caso de prueba TC-PTrS-003 del plan de pruebas.
     */
    @Test
    public void testProcesarPago_Limite_DatosNulos() {
        PagoTransferenciaStrategy strategy = new PagoTransferenciaStrategy("Banco Pichincha");
        assertDoesNotThrow(() -> {
            Pago pago = strategy.procesarPago(20.0, null);
            assertEquals("COMPLETADO", pago.getEstado());
        });
    }
}
