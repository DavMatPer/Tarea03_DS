package com.ticketbeat.gestores;

import com.ticketbeat.modelo.Incidente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class GestorIncidentesTest {

    private GestorIncidentes gestorIncidentes;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        gestorIncidentes = new GestorIncidentes();
        System.setOut(new PrintStream(outContent));
    }

    /**
     * Test case TC-GI-001 from plan_pruebas.md
     */
    @Test
    public void testRegistrarYClasificar_Típico() {
        String descripcion = "No puedo iniciar sesión";
        Incidente incidente = gestorIncidentes.registrarYClasificar(descripcion);
        assertNotNull(incidente);
        assertEquals(descripcion, incidente.getDescripcion());
        assertTrue(outContent.toString().contains("Incidente registrado y clasificado."));
    }

    /**
     * Test case TC-GI-002 from plan_pruebas.md
     */
    @Test
    public void testRegistrarYClasificar_Error_DescripcionNula() {
        // Based on the implementation of Incidente.setDescripcion, this will not throw a NPE
        // but will set the description to null. If validation is added, this test should change.
        Incidente incidente = gestorIncidentes.registrarYClasificar(null);
        assertNotNull(incidente);
        assertNull(incidente.getDescripcion());
    }

    /**
     * Test case TC-GI-003 from plan_pruebas.md
     */
    @Test
    public void testRegistrarIncidente_Simple() {
        String descripcion = "Problema simple de acceso";
        gestorIncidentes.registrarIncidente(descripcion);
        String output = outContent.toString();
        assertTrue(output.contains("Resolviendo incidente de Nivel 1:"));
        assertFalse(output.contains("Resolución final aplicada por Administración"));
    }
    
    /**
     * Test case TC-GI-004 from plan_pruebas.md
     */
    @Test
    public void testRegistrarIncidente_Error_DescripcionNula() {
        // The underlying registrarYClasificar handles this by creating an incident with a null description.
        // The chain of responsibility will then likely fail with a NullPointerException inside a handler.
        assertThrows(NullPointerException.class, () -> {
            gestorIncidentes.registrarIncidente(null);
        });
    }

    /**
     * Test case TC-GI-005 from plan_pruebas.md
     */
    @Test
    public void testRegistrarIncidente_Complejo() {
        String descripcion = "Fraude complejo en la plataforma";
        gestorIncidentes.registrarIncidente(descripcion);
        String output = outContent.toString();
        assertTrue(output.contains("El problema es demasiado complejo para el Nivel 1. Escalando..."));
        assertTrue(output.contains("Resolución final aplicada por Administración para el incidente"));
    }

    /**
     * Test cases TC-GI-006 and TC-GI-007 from plan_pruebas.md
     * These tests point out that some parameters in the legacy 'reportarIncidente' method are unused.
     * This is more of a code smell (dead parameter) than a testable bug.
     * We can confirm no error is thrown when they are null.
     */
    @Test
    public void testReportarIncidente_DeadParameters() {
        // The method 'reportarIncidente' is legacy and has unused parameters.
        // We will call it with nulls for those to ensure it doesn't crash.
        assertDoesNotThrow(() -> {
            gestorIncidentes.reportarIncidente("Test", null, null, new com.ticketbeat.modelo.Comprador(), new GestorNotificaciones());
        });
    }
}
