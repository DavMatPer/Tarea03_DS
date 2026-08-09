package com.ticketbeat.manejador_incidente;

import com.ticketbeat.modelo.Incidente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class DepartamentoAdministracionTest {

    private DepartamentoAdministracion departamento;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        departamento = new DepartamentoAdministracion();
        System.setOut(new PrintStream(outContent));
    }

    /**
     * Test case TC-DA-001 from plan_pruebas.md
     */
    @Test
    public void testManejarIncidente_Típico() {
        Incidente incidente = new Incidente();
        incidente.setDescripcion("Incidente complejo para admin");
        
        departamento.manejarIncidente(incidente);
        
        String output = outContent.toString();
        assertTrue(output.contains("Recibió incidente escalado."));
        assertTrue(output.contains("Resolución final aplicada: Incidente complejo para admin"));
        assertTrue(output.contains("Incidente cerrado exitosamente."));
    }

    /**
     * Test case TC-DA-002 from plan_pruebas.md
     */
    @Test
    public void testManejarIncidente_Error_IncidenteNulo() {
        assertThrows(NullPointerException.class, () -> {
            departamento.manejarIncidente(null);
        });
    }
    
    /**
     * Test case TC-DA-003 from plan_pruebas.md
     */
    @Test
    public void testResolucionFinal_Error_IncidenteNulo() {
         assertThrows(NullPointerException.class, () -> {
            departamento.resolucionFinal(null);
        });
    }
}
