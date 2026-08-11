package com.ticketbeat.boletos.creaciones;

import com.ticketbeat.modelo.EstadoBoleto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas para BoletoReservado.
 */
public class BoletoReservadoTest {

    private BoletoReservado boleto;

    @BeforeEach
    public void setUp() {
        boleto = new BoletoReservado();
    }

    /**
     * Caso de prueba TC-BR-001 del plan de pruebas.
     */
    @Test
    public void testSetNumeroAsiento_Tipico() {
        boleto.setNumeroAsiento("101");
        assertEquals("101", boleto.getNumeroAsiento());
    }

    /**
     * Caso de prueba TC-BR-002 del plan de pruebas.
     */
    @Test
    public void testSetNumeroAsiento_Error_NumeroNulo() {
        boleto.setNumeroAsiento(null);
        assertNull(boleto.getNumeroAsiento());
    }

    /**
     * Caso de prueba TC-BR-003 del plan de pruebas.
     */
    @Test
    public void testSetFila_Tipico() {
        boleto.setFila("A");
        assertEquals("A", boleto.getFila());
    }

    /**
     * Caso de prueba TC-BR-004 del plan de pruebas.
     */
    @Test
    public void testSetFila_Error_FilaNula() {
        boleto.setFila(null);
        assertNull(boleto.getFila());
    }

    /**
     * Caso de prueba TC-BR-005 del plan de pruebas.
     */
    @Test
    public void testSetEstado_Tipico() {
        boleto.setEstado(EstadoBoleto.VENDIDO);
        assertEquals(EstadoBoleto.VENDIDO, boleto.getEstado());
    }

    /**
     * Caso de prueba TC-BR-006 del plan de pruebas.
     */
    @Test
    public void testSetEstado_Error_EstadoNulo() {
        boleto.setEstado(null);
        assertNull(boleto.getEstado());
    }
}
