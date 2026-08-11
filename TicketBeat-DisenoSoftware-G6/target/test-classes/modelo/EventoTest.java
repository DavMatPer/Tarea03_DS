package com.ticketbeat.modelo;

import com.ticketbeat.boletos.creaciones.BoletoGeneral;
import com.ticketbeat.interfaces.IBoleto;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas para la clase Evento (POJO).
 */
public class EventoTest {

    /**
     * Caso de prueba TC-E-001 del plan de pruebas.
     */
    @Test
    public void testAgregarBoleto_Tipico() {
        Evento evento = new Evento();
        IBoleto boleto = new BoletoGeneral();
        evento.agregarBoleto(boleto);
        assertEquals(1, evento.getBoletos().size());
        assertSame(boleto, evento.getBoletos().get(0));
    }

    /**
     * Caso de prueba TC-E-002 del plan de pruebas.
     */
    @Test
    public void testAgregarBoleto_Error_BoletoNulo() {
        Evento evento = new Evento();
        assertDoesNotThrow(() -> evento.agregarBoleto(null));
        assertEquals(1, evento.getBoletos().size());
        assertNull(evento.getBoletos().get(0));
    }
}
