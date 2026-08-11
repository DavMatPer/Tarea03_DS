package com.ticketbeat.modelo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas para la clase Comprador (POJO).
 */
public class CompradorTest {

    /**
     * Caso de prueba TC-C-001 del plan de pruebas.
     */
    @Test
    public void testSetEdad_Error_EdadNegativa() {
        Comprador comprador = new Comprador();
        comprador.setEdad(-5);
        assertEquals(-5, comprador.getEdad(), "La edad se asigna sin ninguna validación de rango.");
    }
}
