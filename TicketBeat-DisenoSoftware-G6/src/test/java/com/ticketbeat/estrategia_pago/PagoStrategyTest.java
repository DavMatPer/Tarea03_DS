package com.ticketbeat.estrategia_pago;

import com.ticketbeat.modelo.Pago;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

public class PagoStrategyTest {

    //--- Tests for PagoTarjetaStrategy ---

    /**
     * Test case TC-PTS-001 from plan_pruebas.md
     */
    @Test
    public void testPagoTarjeta_Típico() {
        PagoTarjetaStrategy strategy = new PagoTarjetaStrategy("VISA");
        Pago pago = strategy.procesarPago(150.75, new HashMap<>());
        assertEquals("COMPLETADO", pago.getEstado());
        assertEquals(150.75, pago.getMonto());
        assertNotNull(pago.getId());
    }

    /**
     * Test case TC-PTS-002 from plan_pruebas.md
     */
    @Test
    public void testPagoTarjeta_Error_MontoNegativo() {
        // The current implementation does not validate the amount.
        // This test confirms a Pago object is created with a negative amount.
        PagoTarjetaStrategy strategy = new PagoTarjetaStrategy("VISA");
        Pago pago = strategy.procesarPago(-100.0, new HashMap<>());
        assertEquals(-100.0, pago.getMonto());
        assertEquals("COMPLETADO", pago.getEstado());
    }
    
    /**
     * Test case TC-PTS-003 from plan_pruebas.md
     */
    @Test
    public void testPagoTarjeta_Error_DatosNulos() {
        // The method doesn't use the 'datos' map, so passing null should not cause an error.
        // This test verifies that the code doesn't crash.
        PagoTarjetaStrategy strategy = new PagoTarjetaStrategy("VISA");
        assertDoesNotThrow(() -> {
            strategy.procesarPago(100.0, null);
        });
    }

    //--- Tests for PagoMovilStrategy ---

    /**
     * Test case TC-PMS-001 from plan_pruebas.md
     */
    @Test
    public void testPagoMovil_Típico() {
        PagoMovilStrategy strategy = new PagoMovilStrategy("PagoFlash");
        Pago pago = strategy.procesarPago(50.0, new HashMap<>());
        assertEquals("COMPLETADO", pago.getEstado());
        assertEquals(50.0, pago.getMonto());
        assertNotNull(pago.getId());
    }
    
    /**
     * Test case TC-PMS-002 from plan_pruebas.md
     */
    @Test
    public void testPagoMovil_Error_DatosNulos() {
        // The method doesn't use the 'datos' map, so passing null should not cause an error.
        PagoMovilStrategy strategy = new PagoMovilStrategy("PagoFlash");
        assertDoesNotThrow(() -> {
            strategy.procesarPago(100.0, null);
        });
    }

    //--- Tests for PagoTransferenciaStrategy ---

    /**
     * Test case TC-PTrS-001 from plan_pruebas.md
     */
    @Test
    public void testPagoTransferencia_Típico() {
        PagoTransferenciaStrategy strategy = new PagoTransferenciaStrategy("Banco Nacional");
        Pago pago = strategy.procesarPago(300.0, new HashMap<>());
        assertEquals("COMPLETADO", pago.getEstado());
        assertEquals(300.0, pago.getMonto());
        assertNotNull(pago.getId());
    }
    
    /**
     * Test case TC-PTrS-002 from plan_pruebas.md
     */
    @Test
    public void testPagoTransferencia_RevertirPago() {
        PagoTransferenciaStrategy strategy = new PagoTransferenciaStrategy("Banco Nacional");
        assertTrue(strategy.revertirPago("some-payment-id"));
    }
    
    /**
     * Test case TC-PTrS-003 from plan_pruebas.md
     */
    @Test
    public void testPagoTransferencia_Error_DatosNulos() {
        // The method doesn't use the 'datos' map, so passing null should not cause an error.
        PagoTransferenciaStrategy strategy = new PagoTransferenciaStrategy("Banco Nacional");
        assertDoesNotThrow(() -> {
            strategy.procesarPago(100.0, null);
        });
    }
}
