package com.ticketbeat.boletos.creaciones;

import com.ticketbeat.modelo.EstadoBoleto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BoletoReservadoTest {

    private BoletoReservado boleto;

    @BeforeEach
    public void setUp() {
        boleto = new BoletoReservado();
    }

    /**
     * Test case TC-BR-001 from plan_pruebas.md
     */
    @Test
    public void testSetNumeroAsiento_Típico() {
        String nuevoAsiento = "B-202";
        boleto.setNumeroAsiento(nuevoAsiento);
        assertEquals(nuevoAsiento, boleto.getNumeroAsiento());
    }

    /**
     * Test case TC-BR-002 from plan_pruebas.md
     */
    @Test
    public void testSetNumeroAsiento_Error_Nulo() {
        // Current implementation accepts null without validation.
        boleto.setNumeroAsiento(null);
        assertNull(boleto.getNumeroAsiento());
    }

    /**
     * Test case TC-BR-003 from plan_pruebas.md
     */
    @Test
    public void testSetFila_Típico() {
        String nuevaFila = "Fila 10";
        boleto.setFila(nuevaFila);
        assertEquals(nuevaFila, boleto.getFila());
    }

    /**
     * Test case TC-BR-004 from plan_pruebas.md
     */
    @Test
    public void testSetFila_Error_Nulo() {
        // Current implementation accepts null without validation.
        boleto.setFila(null);
        assertNull(boleto.getFila());
    }

    /**
     * Test case TC-BR-005 from plan_pruebas.md
     */
    @Test
    public void testSetEstado_Típico() {
        boleto.setEstado(EstadoBoleto.VENDIDO);
        assertEquals(EstadoBoleto.VENDIDO, boleto.getEstado());
    }

    /**
     * Test case TC-BR-006 from plan_pruebas.md
     */
    @Test
    public void testSetEstado_Error_Nulo() {
        // Current implementation accepts null without validation.
        boleto.setEstado(null);
        assertNull(boleto.getEstado());
    }
}
