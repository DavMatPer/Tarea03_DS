package com.ticketbeat.modelo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class OrganizadorTest {

    @Test
    public void testSetNombreEmpresa_Tipico() {
        Organizador organizador = new Organizador();
        assertNull(organizador.getNombre(), "Los atributos heredados de Usuario inician en null.");

        organizador.setNombreEmpresa("Live Music Corp");
        assertEquals("Live Music Corp", organizador.getNombreEmpresa());
    }
}
