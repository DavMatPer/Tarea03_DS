package com.ticketbeat.manejador_incidente;

import com.ticketbeat.modelo.Incidente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class AgenteSoporteTest {

    private AgenteSoporte agenteSoporte;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        agenteSoporte = new AgenteSoporte();
        System.setOut(new PrintStream(outContent));
    }

    /**
     * Test case TC-AS-001 from plan_pruebas.md
     */
    @Test
    public void testPuedeResolver_Típico_Simple() {
        Incidente incidente = new Incidente();
        incidente.setDescripcion("Problema simple de acceso");
        assertTrue(agenteSoporte.puedeResolver(incidente));
    }

    /**
     * Test case TC-AS-002 from plan_pruebas.md
     */
    @Test
    public void testPuedeResolver_Típico_Complejo() {
        Incidente incidente = new Incidente();
        incidente.setDescripcion("problema COMPLEJO en la plataforma");
        assertFalse(agenteSoporte.puedeResolver(incidente));
    }
    
    @Test
    public void testPuedeResolver_Límite_DescripcionNula() {
        Incidente incidente = new Incidente();
        incidente.setDescripcion(null);
        assertFalse(agenteSoporte.puedeResolver(incidente));
    }

    /**
     * Test case TC-AS-003 from plan_pruebas.md
     */
    @Test
    public void testManejarIncidente_Escala() {
        Incidente incidente = new Incidente();
        incidente.setDescripcion("Incidente complejo que necesita escalar");

        // Create a simple mock for the next handler
        ManejadorIncidente siguiente = new ManejadorIncidente() {
            @Override
            public void manejarIncidente(Incidente incidente) {
                System.out.println("Siguiente manejador fue invocado.");
            }
        };
        agenteSoporte.setSiguienteManejador(siguiente);
        
        agenteSoporte.manejarIncidente(incidente);
        
        String output = outContent.toString();
        assertTrue(output.contains("No puede resolver. Escalando al siguiente nivel..."));
        assertTrue(output.contains("Siguiente manejador fue invocado."));
    }
    
    @Test
    public void testManejarIncidente_Resuelve() {
        Incidente incidente = new Incidente();
        incidente.setDescripcion("Incidente simple");
        
        agenteSoporte.manejarIncidente(incidente);
        
        String output = outContent.toString();
        assertTrue(output.contains("Incidente resuelto en primer nivel"));
        assertFalse(output.contains("Escalando al siguiente nivel..."));
    }

    /**
     * Test case TC-AS-004 from plan_pruebas.md
     */
    @Test
    public void testManejarIncidente_Límite_SinSiguienteManejador() {
        Incidente incidente = new Incidente();
        incidente.setDescripcion("Incidente complejo");
        
        // Ensure no next handler is set
        agenteSoporte.setSiguienteManejador(null);
        
        agenteSoporte.manejarIncidente(incidente);
        
        String output = outContent.toString();
        assertTrue(output.contains("No puede resolver. Escalando al siguiente nivel..."));
        assertTrue(output.contains("No hay más manejadores en la cadena."));
    }
}
