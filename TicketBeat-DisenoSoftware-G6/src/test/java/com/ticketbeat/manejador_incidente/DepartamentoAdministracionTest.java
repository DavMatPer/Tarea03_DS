package com.ticketbeat.manejador_incidente;

import com.ticketbeat.modelo.Incidente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

/**
 * Pruebas para DepartamentoAdministracion.
 */
public class DepartamentoAdministracionTest {

    private DepartamentoAdministracion departamento;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        departamento = new DepartamentoAdministracion();
        System.setOut(new PrintStream(outContent, true, StandardCharsets.UTF_8));
    }

    /**
     * Caso de prueba TC-DA-001 del plan de pruebas.
     */
    @Test
    public void testManejarIncidente_Tipico() {
        Incidente incidente = new Incidente();
        incidente.setDescripcion("Problema de acceso a boletos digitales");

        departamento.manejarIncidente(incidente);

        String output = outContent.toString(StandardCharsets.UTF_8);
        assertTrue(output.contains("Recibió incidente escalado."));
        assertTrue(output.contains("Resolución final aplicada: Problema de acceso a boletos digitales"));
        assertTrue(output.contains("Incidente cerrado exitosamente."));
    }

    /**
     * Caso de prueba TC-DA-002 del plan de pruebas.
     */
    @Test
    public void testManejarIncidente_Error_IncidenteNulo() {
        assertThrows(NullPointerException.class, () -> departamento.manejarIncidente(null));
    }

    /**
     * Caso de prueba TC-DA-003 del plan de pruebas.
     */
    @Test
    public void testResolucionFinal_Error_IncidenteNulo() {
        assertThrows(NullPointerException.class, () -> departamento.resolucionFinal(null));
    }
}
