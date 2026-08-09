package com.ticketbeat.servicios;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class CanalServicioTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outContent));
    }

    //--- Tests for CanalEmail ---

    /**
     * Test case TC-CE-001 from plan_pruebas.md
     */
    @Test
    public void testCanalEmail_EnviarTípico() {
        CanalEmail canal = new CanalEmail();
        assertTrue(canal.enviar("Mensaje de prueba"));
        assertTrue(outContent.toString().contains("[EMAIL] Enviando correo: Mensaje de prueba"));
    }

    /**
     * Test case TC-CE-002 from plan_pruebas.md
     */
    @Test
    public void testCanalEmail_EnviarNulo() {
        CanalEmail canal = new CanalEmail();
        // The implementation does not validate null and will print "null".
        assertTrue(canal.enviar(null));
        assertTrue(outContent.toString().contains("[EMAIL] Enviando correo: null"));
    }

    //--- Tests for CanalSMS ---

    /**
     * Test case TC-CS-001 from plan_pruebas.md
     */
    @Test
    public void testCanalSMS_EnviarTípico() {
        CanalSMS canal = new CanalSMS();
        assertTrue(canal.enviar("Mensaje de texto de prueba"));
        assertTrue(outContent.toString().contains("[SMS] Enviando mensaje de texto: Mensaje de texto de prueba"));
    }

    /**
     * Test case TC-CS-002 from plan_pruebas.md
     */
    @Test
    public void testCanalSMS_EnviarVacio() {
        CanalSMS canal = new CanalSMS();
        // The implementation does not validate empty strings.
        assertTrue(canal.enviar(""));
        assertTrue(outContent.toString().contains("[SMS] Enviando mensaje de texto: "));
    }
}
