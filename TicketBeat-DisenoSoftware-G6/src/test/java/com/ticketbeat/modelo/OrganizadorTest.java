package com.ticketbeat.modelo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas para la clase Organizador.
 */
public class OrganizadorTest {

    /**
     * Caso de prueba TC-O-001 del plan de pruebas.
     *
     * NUEVO: Organizador, subclase concreta de Usuario, no tenía ningún caso de
     * prueba en el plan original.
     */
    @Test
    public void testSetNombreEmpresa_Tipico() {
        Organizador organizador = new Organizador();
        assertNull(organizador.getNombre(), "Los atributos heredados de Usuario inician en null.");

        organizador.setNombreEmpresa("Live Music Corp");
        assertEquals("Live Music Corp", organizador.getNombreEmpresa());
    }
}
