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

    @Test
    public void testRegistrarYClasificar_Típico() {
        String descripcion = "No puedo iniciar sesión";
        Incidente incidente = gestorIncidentes.registrarYClasificar(descripcion);
        assertNotNull(incidente);
        assertEquals(descripcion, incidente.getDescripcion());
        assertTrue(outContent.toString().contains("Incidente registrado y clasificado."));
    }

    @Test
    public void testRegistrarYClasificar_Error_DescripcionNula() {
        Incidente incidente = gestorIncidentes.registrarYClasificar(null);
        assertNotNull(incidente);
        assertNull(incidente.getDescripcion());
    }

    @Test
    public void testRegistrarIncidente_Simple() {
        String descripcion = "Problema simple de acceso";
        gestorIncidentes.registrarIncidente(descripcion);
        String output = outContent.toString();
        assertTrue(output.contains("Incidente resuelto en primer nivel: " + descripcion));
        assertFalse(output.contains("Resolución final aplicada"));
    }

    @Test
    public void testRegistrarIncidente_Error_DescripcionNula() {
        assertDoesNotThrow(() -> {
            gestorIncidentes.registrarIncidente(null);
        });
        String output = outContent.toString();
        assertTrue(output.contains("Resolución final aplicada: null"));
    }

    @Test
    public void testRegistrarIncidente_Complejo() {
        String descripcion = "Fraude complejo en la plataforma";
        gestorIncidentes.registrarIncidente(descripcion);
        String output = outContent.toString();
        assertTrue(output.contains("No puede resolver. Escalando al siguiente nivel..."));
        assertTrue(output.contains("Resolución final aplicada: " + descripcion));
    }
}

