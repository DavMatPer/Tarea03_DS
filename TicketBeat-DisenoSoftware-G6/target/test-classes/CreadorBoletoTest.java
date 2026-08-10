package com.ticketbeat.boletos.creadores;

import com.ticketbeat.boletos.creaciones.BoletoGeneral;
import com.ticketbeat.boletos.creaciones.BoletoReservado;
import com.ticketbeat.boletos.creaciones.BoletoVIP;
import com.ticketbeat.interfaces.IBoleto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class CreadorBoletoTest {
    
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outContent));
    }

    /**
     * Test case TC-CBG-001 from plan_pruebas.md
     */
    @Test
    public void testCreadorBoletoGeneral_CreaBoletoCorrecto() {
        CreadorBoleto creador = new CreadorBoletoGeneral();
        IBoleto boleto = creador.crearBoleto();
        assertTrue(boleto instanceof BoletoGeneral, "El creador debe instanciar un BoletoGeneral.");
    }

    /**
     * Test case TC-CBR-001 from plan_pruebas.md
     */
    @Test
    public void testCreadorBoletoReservado_CreaBoletoCorrecto() {
        CreadorBoleto creador = new CreadorBoletoReservado();
        IBoleto boleto = creador.crearBoleto();
        assertTrue(boleto instanceof BoletoReservado, "El creador debe instanciar un BoletoReservado.");
    }

    /**
     * Test case TC-CBV-001 from plan_pruebas.md
     */
    @Test
    public void testCreadorBoletoVIP_CreaBoletoCorrecto() {
        CreadorBoleto creador = new CreadorBoletoVIP();
        IBoleto boleto = creador.crearBoleto();
        assertTrue(boleto instanceof BoletoVIP, "El creador debe instanciar un BoletoVIP.");
    }
    
    /**
     * Test case TC-CB-001 from plan_pruebas.md
     *
     * CORREGIDO: BoletoGeneral.mostrarDetalles() imprime "=== BOLETO GENERAL ==="
     * y "Sección: ...", nunca "Tipo de Boleto: General". Se ajustó la
     * aserción para que verifique el texto que realmente produce el método.
     */
    @Test
    public void testProcesarEmision_TemplateMethod() {
        // Use CreadorBoletoGeneral as the concrete implementation for the test
        CreadorBoleto creador = new CreadorBoletoGeneral();
        creador.procesarEmision();
        
        // The output should contain details from BoletoGeneral's mostrarDetalles() method
        String output = outContent.toString();
        assertTrue(output.contains("=== BOLETO GENERAL ==="));
        assertTrue(output.contains("Precio: $100.0"));
    }
}