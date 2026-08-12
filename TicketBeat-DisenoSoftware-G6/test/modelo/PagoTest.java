package com.ticketbeat.modelo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PagoTest {

    @Test
    public void testConstructor_Error_MontoNegativo() {
        Pago pago = new Pago("PAGO-001", -50.0, EstadoPago.COMPLETADO);
        assertEquals(-50.0, pago.getMonto(), "El constructor no valida el signo del monto.");
    }

    @Test
    public void testEstaCompletado_Tipico() {
        Pago completado = new Pago("PAGO-002", 100.0, EstadoPago.COMPLETADO);
        assertTrue(completado.estaCompletado());

        Pago rechazado = new Pago("PAGO-003", 100.0, EstadoPago.RECHAZADO);
        assertFalse(rechazado.estaCompletado());
    }
}
