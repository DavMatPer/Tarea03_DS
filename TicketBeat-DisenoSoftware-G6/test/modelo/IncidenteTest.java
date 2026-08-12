package com.ticketbeat.modelo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class IncidenteTest {

    @Test
    public void testSetDescripcion_Tipico() {
        Incidente incidente = new Incidente();
        incidente.setDescripcion("Consulta cerrada");
        assertEquals("Consulta cerrada", incidente.getDescripcion());
    }

    @Test
    public void testEstado_Tipico() {
        Incidente incidente = new Incidente();
        assertEquals(EstadoIncidente.ABIERTO, incidente.getEstado());

        incidente.setEstado(EstadoIncidente.RESUELTO);
        assertEquals(EstadoIncidente.RESUELTO, incidente.getEstado());
    }
}

