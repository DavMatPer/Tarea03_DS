package com.ticketbeat.estrategia_pago;

import com.ticketbeat.modelo.Pago;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Pruebas para PagoTarjetaStrategy.
 */
public class PagoTarjetaStrategyTest {

    /**
     * Caso de prueba TC-PTS-001 del plan de pruebas.
     */
    @Test
    public void testProcesarPago_Tipico() {
        PagoTarjetaStrategy strategy = new PagoTarjetaStrategy("Visa/Mastercard");
        Map<String, String> datos = new HashMap<>();
        datos.put("numero", "4111111111111111");

        Pago pago = strategy.procesarPago(100.0, datos);

        assertNotNull(pago);
        assertEquals("COMPLETADO", pago.getEstado());
    }

    /**
     * Caso de prueba TC-PTS-002 del plan de pruebas.
     */
    @Test
    public void testProcesarPago_Error_MontoNegativo() {
        PagoTarjetaStrategy strategy = new PagoTarjetaStrategy("Visa/Mastercard");
        Pago pago = strategy.procesarPago(-100.0, new HashMap<>());

        assertEquals(-100.0, pago.getMonto(), "No hay validación de signo del monto.");
        assertEquals("COMPLETADO", pago.getEstado(), "El pago se marca COMPLETADO igual, sin validar el monto.");
    }

    /**
     * Caso de prueba TC-PTS-003 del plan de pruebas.
     */
    @Test
    public void testProcesarPago_Limite_DatosNulos() {
        PagoTarjetaStrategy strategy = new PagoTarjetaStrategy("Visa/Mastercard");
        assertDoesNotThrow(() -> {
            Pago pago = strategy.procesarPago(50.0, null);
            assertEquals("COMPLETADO", pago.getEstado());
        });
    }
}
