package com.ticketbeat.boletos.creaciones;

import com.ticketbeat.modelo.EstadoBoleto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class BoletoGeneralTest {

    private BoletoGeneral boleto;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        boleto = new BoletoGeneral();
        System.setOut(new PrintStream(outContent));
    }

    /**
     * Test case TC-BG-001 from plan_pruebas.md
     */
    @Test
    public void testMostrarDetalles_Típico() {
        boleto.mostrarDetalles();
        String output = outContent.toString();
        assertTrue(output.contains("=== BOLETO GENERAL ==="));
        assertTrue(output.contains("Sección: General A"));
        assertTrue(output.contains("Precio: $100.0"));
        assertTrue(output.contains("Estado: DISPONIBLE"));
    }

    /**
     * Test case TC-BG-002 from plan_pruebas.md
     */
    @Test
    public void testSetSeccion_Típico() {
        String nuevaSeccion = "General B";
        boleto.setSeccion(nuevaSeccion);
        assertEquals(nuevaSeccion, boleto.getSeccion());
    }

    /**
     * Test case TC-BG-003 from plan_pruebas.md
     */
    @Test
    public void testSetSeccion_Error_Nulo() {
        // The current implementation does not validate against null.
        // This test confirms that behavior. If validation is added, this test should be updated.
        boleto.setSeccion(null);
        assertNull(boleto.getSeccion());
    }

    /**
     * Test case TC-BG-004 from plan_pruebas.md
     */
    @Test
    public void testSetEstado_Típico() {
        boleto.setEstado(EstadoBoleto.VENDIDO);
        assertEquals(EstadoBoleto.VENDIDO, boleto.getEstado());
    }

    /**
     * Test case TC-BG-005 from plan_pruebas.md
     */
    @Test
    public void testSetEstado_Error_Nulo() {
        // The current implementation does not validate against null.
        // This test confirms that behavior.
        boleto.setEstado(null);
        assertNull(boleto.getEstado());
    }
}
