package com.ticketbeat.modelo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas para la clase Incidente (POJO).
 */
public class IncidenteTest {

    /**
     * Caso de prueba TC-I-001 del plan de pruebas.
     */
    @Test
    public void testSetDescripcion_Tipico() {
        Incidente incidente = new Incidente();
        incidente.setDescripcion("Consulta cerrada");
        assertEquals("Consulta cerrada", incidente.getDescripcion());
    }
}
