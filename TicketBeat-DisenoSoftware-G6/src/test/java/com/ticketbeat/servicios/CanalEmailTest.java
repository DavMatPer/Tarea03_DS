package com.ticketbeat.servicios;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

/**
 * Pruebas para CanalEmail.
 */
public class CanalEmailTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outContent, true, StandardCharsets.UTF_8));
    }

    /**
     * Caso de prueba TC-CE-001 del plan de pruebas.
     */
    @Test
    public void testEnviar_Tipico() {
        CanalEmail canal = new CanalEmail();
        assertTrue(canal.enviar("Su compra fue confirmada"));
    }

    /**
     * Caso de prueba TC-CE-002 del plan de pruebas.
     */
    @Test
    public void testEnviar_Limite_MensajeNulo() {
        CanalEmail canal = new CanalEmail();
        assertTrue(canal.enviar(null));
        assertTrue(outContent.toString(StandardCharsets.UTF_8).contains("[EMAIL] Enviando correo: null"));
    }
}
