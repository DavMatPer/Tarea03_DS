package com.ticketbeat.servicios;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

/**
 * Pruebas para CanalSMS.
 */
public class CanalSMSTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outContent, true, StandardCharsets.UTF_8));
    }

    /**
     * Caso de prueba TC-CS-001 del plan de pruebas.
     */
    @Test
    public void testEnviar_Tipico() {
        CanalSMS canal = new CanalSMS();
        assertTrue(canal.enviar("Su compra fue confirmada"));
    }

    /**
     * Caso de prueba TC-CS-002 del plan de pruebas.
     */
    @Test
    public void testEnviar_Limite_MensajeVacio() {
        CanalSMS canal = new CanalSMS();
        assertTrue(canal.enviar(""));
        assertTrue(outContent.toString(StandardCharsets.UTF_8).contains("[SMS] Enviando mensaje de texto: "));
    }
}
