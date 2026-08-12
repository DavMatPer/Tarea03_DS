package com.ticketbeat.boletos.creaciones;

import com.ticketbeat.modelo.EstadoBoleto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class BoletoGeneralTest {

    private BoletoGeneral boleto;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        boleto = new BoletoGeneral();
        System.setOut(new PrintStream(outContent, true, StandardCharsets.UTF_8));
    }

    @Test
    public void testMostrarDetalles_Tipico() {
        boleto.mostrarDetalles();
        String output = outContent.toString(StandardCharsets.UTF_8);
        assertTrue(output.contains("=== BOLETO GENERAL ==="));
        assertTrue(output.contains("Sección: General A"));
        assertTrue(output.contains("Precio: $100.0"));
        assertTrue(output.contains("Estado: DISPONIBLE"));
    }

    @Test
    public void testSetSeccion_Tipico() {
        boleto.setSeccion("General B");
        assertEquals("General B", boleto.getSeccion());
    }

    @Test
    public void testSetSeccion_Error_SeccionNula() {
        boleto.setSeccion(null);
        assertNull(boleto.getSeccion());
    }

    @Test
    public void testSetEstado_Tipico() {
        boleto.setEstado(EstadoBoleto.VENDIDO);
        assertEquals(EstadoBoleto.VENDIDO, boleto.getEstado());
    }

    @Test
    public void testSetEstado_Error_EstadoNulo() {
        boleto.setEstado(null);
        assertNull(boleto.getEstado());
    }

    @Test
    public void testSetComprador_Tipico() {
        assertNull(boleto.getComprador(), "Un boleto recién creado no tiene comprador vinculado.");

        com.ticketbeat.modelo.Comprador comprador = new com.ticketbeat.modelo.Comprador();
        comprador.setNombre("Juan Perez");
        boleto.setComprador(comprador);

        assertSame(comprador, boleto.getComprador());
    }
}
