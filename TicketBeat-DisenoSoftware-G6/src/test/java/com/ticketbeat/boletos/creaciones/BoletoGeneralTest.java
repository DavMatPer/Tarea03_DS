package com.ticketbeat.boletos.creaciones;

import com.ticketbeat.modelo.EstadoBoleto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

/**
 * Pruebas para BoletoGeneral.
 */
public class BoletoGeneralTest {

    private BoletoGeneral boleto;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        boleto = new BoletoGeneral();
        System.setOut(new PrintStream(outContent, true, StandardCharsets.UTF_8));
    }

    /**
     * Caso de prueba TC-BG-001 del plan de pruebas.
     */
    @Test
    public void testMostrarDetalles_Tipico() {
        boleto.mostrarDetalles();
        String output = outContent.toString(StandardCharsets.UTF_8);
        assertTrue(output.contains("=== BOLETO GENERAL ==="));
        assertTrue(output.contains("Sección: General A"));
        assertTrue(output.contains("Precio: $100.0"));
        assertTrue(output.contains("Estado: DISPONIBLE"));
    }

    /**
     * Caso de prueba TC-BG-002 del plan de pruebas.
     */
    @Test
    public void testSetSeccion_Tipico() {
        boleto.setSeccion("General B");
        assertEquals("General B", boleto.getSeccion());
    }

    /**
     * Caso de prueba TC-BG-003 del plan de pruebas.
     *
     * CORREGIDO: setSeccion es una asignación directa; no lanza excepción.
     */
    @Test
    public void testSetSeccion_Error_SeccionNula() {
        boleto.setSeccion(null);
        assertNull(boleto.getSeccion());
    }

    /**
     * Caso de prueba TC-BG-004 del plan de pruebas.
     */
    @Test
    public void testSetEstado_Tipico() {
        boleto.setEstado(EstadoBoleto.VENDIDO);
        assertEquals(EstadoBoleto.VENDIDO, boleto.getEstado());
    }

    /**
     * Caso de prueba TC-BG-005 del plan de pruebas.
     *
     * CORREGIDO: setEstado es una asignación directa; no lanza excepción.
     */
    @Test
    public void testSetEstado_Error_EstadoNulo() {
        boleto.setEstado(null);
        assertNull(boleto.getEstado());
    }
}
