package com.ticketbeat.modelo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas para la clase Incidente (POJO).
 */
public class IncidenteTest {

    /**
     * Caso de prueba TC-I-001 del plan de pruebas.
     *
     * CORREGIDO: el plan original probaba setEstado()/getEstado(), pero Incidente
     * no expone esos accesores (el campo "estado" existe internamente pero no es
     * público). Se ajustó el caso a los únicos accesores reales: setDescripcion/getDescripcion.
     */
    @Test
    public void testSetDescripcion_Tipico() {
        Incidente incidente = new Incidente();
        incidente.setDescripcion("Consulta cerrada");
        assertEquals("Consulta cerrada", incidente.getDescripcion());
    }
}
