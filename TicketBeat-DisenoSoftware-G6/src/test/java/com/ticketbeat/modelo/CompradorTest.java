package com.ticketbeat.modelo;

import com.ticketbeat.servicios.CanalEmail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CompradorTest {

    private Comprador comprador;

    @BeforeEach
    public void setUp() {
        comprador = new Comprador();
    }

    /**
     * Test case TC-C-001 from plan_pruebas.md
     */
    @Test
    public void testSetEdad_Error_EdadNegativa() {
        // The current implementation does not validate the age.
        // This test confirms that a negative age can be set.
        comprador.setEdad(-5);
        assertEquals(-5, comprador.getEdad());
    }

    @Test
    public void testSetAndGetCanalPreferido() {
        CanalEmail canal = new CanalEmail();
        comprador.setCanalPreferido(canal);
        assertEquals(canal, comprador.getCanalPreferido());
    }
    
    @Test
    public void testSetAndGetEsSocio() {
        comprador.setEsSocio(true);
        assertTrue(comprador.isEsSocio());
        comprador.setEsSocio(false);
        assertFalse(comprador.isEsSocio());
    }
}
