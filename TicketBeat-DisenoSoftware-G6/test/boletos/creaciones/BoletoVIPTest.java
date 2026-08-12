package com.ticketbeat.boletos.creaciones;

import com.ticketbeat.modelo.EstadoBoleto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class BoletoVIPTest {

    private BoletoVIP boleto;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        boleto = new BoletoVIP();
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void testMostrarDetalles_Típico() {
        boleto.mostrarDetalles();
        String output = outContent.toString();
        assertTrue(output.contains("=== BOLETO VIP ==="));
        assertTrue(output.contains("Beneficios: [Acceso backstage, Bebidas incluidas, Meet & Greet]"));
    }

    @Test
    public void testSetBeneficios_Error_Nulo() {
        boleto.setBeneficios(null);
        assertDoesNotThrow(() -> {
            boleto.mostrarDetalles();
        });
        assertTrue(outContent.toString().contains("Beneficios: null"));
    }

    @Test
    public void testSetEstado_Típico() {
        boleto.setEstado(EstadoBoleto.VENDIDO);
        assertEquals(EstadoBoleto.VENDIDO, boleto.getEstado());
    }

    @Test
    public void testSetEstado_Error_Nulo() {
        // Current implementation accepts null without validation.
        boleto.setEstado(null);
        assertNull(boleto.getEstado());
    }
}
