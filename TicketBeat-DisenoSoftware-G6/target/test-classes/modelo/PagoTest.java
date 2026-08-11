package com.ticketbeat.modelo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas para la clase Pago (POJO).
 */
public class PagoTest {

    /**
     * Caso de prueba TC-P-001 del plan de pruebas.
     */
    @Test
    public void testConstructor_Error_MontoNegativo() {
        Pago pago = new Pago("PAGO-001", -50.0, "COMPLETADO");
        assertEquals(-50.0, pago.getMonto(), "El constructor no valida el signo del monto.");
    }
}
