package com.ticketbeat.estrategia_pago;

import com.ticketbeat.modelo.Pago;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

/**
 * Pruebas para PagoMovilStrategy.
 */
public class PagoMovilStrategyTest {

    /**
     * Caso de prueba TC-PMS-001 del plan de pruebas.
     */
    @Test
    public void testProcesarPago_Tipico() {
        PagoMovilStrategy strategy = new PagoMovilStrategy("PayPhone");
        Pago pago = strategy.procesarPago(75.0, new HashMap<>());

        assertNotNull(pago);
        assertEquals("COMPLETADO", pago.getEstado());
    }

    /**
     * Caso de prueba TC-PMS-002 del plan de pruebas.
     *
     * CORREGIDO: "datos" es un parámetro que el método nunca usa.
     */
    @Test
    public void testProcesarPago_Limite_DatosNulos() {
        PagoMovilStrategy strategy = new PagoMovilStrategy("PayPhone");
        assertDoesNotThrow(() -> {
            Pago pago = strategy.procesarPago(30.0, null);
            assertEquals("COMPLETADO", pago.getEstado());
        });
    }
}
